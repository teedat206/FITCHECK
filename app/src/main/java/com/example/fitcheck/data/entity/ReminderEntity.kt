package com.example.fitcheck.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// ReminderEntity.kt
@Entity(
    tableName = "reminders",
    indices = [Index(value = ["userId", "type"], unique = true)]
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val type: String,            // WEIGHT / MEAL / WORKOUT
    val isEnabled: Boolean = false,
    val hour: Int = 7,
    val minute: Int = 0,
    val daysOfWeek: String = ""  // "T2,T5"
)