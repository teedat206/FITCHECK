package com.example.fitcheck.data.repository

import com.example.fitcheck.data.WeightRecord
import com.example.fitcheck.data.dao.WeightDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

sealed class WeightResult<out T> {
    data class Success<out T>(val data: T) : WeightResult<T>()
    data class Error(val message: String) : WeightResult<Nothing>()
}

class WeightRepository(private val weightDao: WeightDao, private val userId: Long) {

    companion object {
        const val MIN_WEIGHT = 20f
        const val MAX_WEIGHT = 300f
        private val displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        private val dbFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    }

    fun parseDisplayToDbDate(displayDate: String): String? {
        return try {
            val date = LocalDate.parse(displayDate, displayFormatter)
            date.format(dbFormatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    fun parseDbToDisplayDate(dbDate: String): String? {
        return try {
            val date = LocalDate.parse(dbDate, dbFormatter)
            date.format(displayFormatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    fun formatDateToDb(date: LocalDate): String = date.format(dbFormatter)
    fun formatDateToDisplay(date: LocalDate): String = date.format(displayFormatter)

    val allWeights: Flow<List<WeightRecord>> = weightDao.getAllByUser(userId)

    suspend fun insert(weight: Float, date: String, session: String): WeightResult<WeightRecord> =
        withContext(Dispatchers.IO) {
            if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
                return@withContext WeightResult.Error("Cân nặng phải nằm trong khoảng từ ${MIN_WEIGHT.toInt()}kg đến ${MAX_WEIGHT.toInt()}kg")
            }
            if (parseDisplayToDbDate(date) == null) {
                return@withContext WeightResult.Error("Định dạng ngày không hợp lệ (yêu cầu dd/MM/yyyy)")
            }
            if (session.isBlank()) {
                return@withContext WeightResult.Error("Buổi ghi nhận không được để trống")
            }

            try {
                val record = WeightRecord(userId = userId, weight = weight, date = date.trim(), session = session.trim())
                weightDao.insert(record)
                WeightResult.Success(record)
            } catch (e: Exception) {
                WeightResult.Error("Không thể lưu bản ghi cân nặng: ${e.localizedMessage}")
            }
        }

    suspend fun update(record: WeightRecord): WeightResult<WeightRecord> =
        withContext(Dispatchers.IO) {
            if (record.weight < MIN_WEIGHT || record.weight > MAX_WEIGHT) {
                return@withContext WeightResult.Error("Cân nặng phải nằm trong khoảng từ ${MIN_WEIGHT.toInt()}kg đến ${MAX_WEIGHT.toInt()}kg")
            }
            if (parseDisplayToDbDate(record.date) == null) {
                return@withContext WeightResult.Error("Định dạng ngày không hợp lệ (yêu cầu dd/MM/yyyy)")
            }
            try {
                weightDao.update(record.copy(userId = userId))
                WeightResult.Success(record)
            } catch (e: Exception) {
                WeightResult.Error("Không thể cập nhật bản ghi: ${e.localizedMessage}")
            }
        }

    suspend fun delete(record: WeightRecord) = withContext(Dispatchers.IO) {
        weightDao.delete(record)
    }

    suspend fun getBetween(fromDate: LocalDate, toDate: LocalDate): List<WeightRecord> =
        withContext(Dispatchers.IO) {
            val fromStr = formatDateToDb(fromDate)
            val toStr = formatDateToDb(toDate)
            weightDao.getBetween(userId, fromStr, toStr)
        }

    suspend fun exportToCsv(fromDate: LocalDate? = null, toDate: LocalDate? = null): String =
        withContext(Dispatchers.IO) {
            val records = if (fromDate != null && toDate != null) {
                getBetween(fromDate, toDate)
            } else {
                weightDao.getAllOnce(userId)
            }

            val sb = StringBuilder()
            sb.append("date,session,weight\n")

            for (r in records) {
                val escapedDate = escapeCsv(r.date)
                val escapedSession = escapeCsv(r.session)
                sb.append("$escapedDate,$escapedSession,${r.weight}\n")
            }
            sb.toString()
        }

    private fun escapeCsv(field: String): String {
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            val escaped = field.replace("\"", "\"\"")
            return "\"$escaped\""
        }
        return field
    }
}
