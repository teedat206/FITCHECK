package com.example.fitcheck.data.repository

import androidx.lifecycle.LiveData
import com.example.fitcheck.data.dao.ReminderDao
import com.example.fitcheck.data.entity.ReminderEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class ReminderResult {
    data class Success(val reminder: ReminderEntity) : ReminderResult()
    data class Error(val message: String) : ReminderResult()
}

class ReminderRepository(private val dao: ReminderDao) {

    companion object {
        const val TYPE_WEIGHT = "WEIGHT"
        const val TYPE_MEAL = "MEAL"
        const val TYPE_WORKOUT = "WORKOUT"
    }

    fun observe(userId: Long): LiveData<List<ReminderEntity>> = dao.observe(userId)

    suspend fun save(reminder: ReminderEntity) =
        withContext(Dispatchers.IO) { dao.upsert(reminder) }

    suspend fun saveWithParams(
        userId: Long, type: String, isEnabled: Boolean,
        hour: Int, minute: Int, daysOfWeek: String
    ): ReminderResult = withContext(Dispatchers.IO) {
        if (hour !in 0..23) {
            return@withContext ReminderResult.Error("Giờ nhắc nhở không hợp lệ (0-23)")
        }
        if (minute !in 0..59) {
            return@withContext ReminderResult.Error("Phút nhắc nhở không hợp lệ (0-59)")
        }

        try {
            val reminder = ReminderEntity(
                userId = userId,
                type = type,
                isEnabled = isEnabled,
                hour = hour,
                minute = minute,
                daysOfWeek = daysOfWeek
            )
            dao.upsert(reminder)
            ReminderResult.Success(reminder)
        } catch (e: Exception) {
            ReminderResult.Error("Không thể lưu cấu hình nhắc nhở: ${e.localizedMessage}")
        }
    }
}
