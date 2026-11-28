package com.example.fitcheck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.entity.ReminderEntity
import com.example.fitcheck.data.repository.ReminderRepository
import kotlinx.coroutines.launch

class ReminderViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ReminderRepository(AppDatabase.getInstance(app).reminderDao())

    fun observe(userId: Long): LiveData<List<ReminderEntity>> = repo.observe(userId)

    /** type: "WEIGHT" / "MEAL" / "WORKOUT"; daysOfWeek ví dụ "T2,T5" */
    fun save(
        userId: Long, type: String, isEnabled: Boolean,
        hour: Int, minute: Int, daysOfWeek: String
    ) {
        viewModelScope.launch {
            repo.save(
                ReminderEntity(
                    userId = userId, type = type, isEnabled = isEnabled,
                    hour = hour, minute = minute, daysOfWeek = daysOfWeek
                )
            )
        }
    }
}