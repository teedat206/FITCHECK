package com.example.fitcheck.data.repository

import androidx.lifecycle.LiveData
import com.example.fitcheck.data.dao.WorkoutDao
import com.example.fitcheck.data.entity.WorkoutEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkoutRepository(private val dao: WorkoutDao) {

    fun getAll(): LiveData<List<WorkoutEntity>> = dao.getAll()

    fun search(
        keyword: String = "", intensity: String = "",
        maxDuration: Int? = null, hasEquipment: Boolean? = null
    ): LiveData<List<WorkoutEntity>> = dao.search(keyword, intensity, maxDuration, hasEquipment)

    suspend fun setCompleted(id: Long, value: Boolean) =
        withContext(Dispatchers.IO) { dao.setCompleted(id, value) }

    suspend fun setBookmark(id: Long, value: Boolean) =
        withContext(Dispatchers.IO) { dao.setBookmark(id, value) }
}