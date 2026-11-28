package com.example.fitcheck.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workouts",
    indices = [Index(value = ["name"], unique = true)]
)
data class WorkoutEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val intensity: String,       // "Nhẹ" / "Vừa" / "Cao"
    val durationMinutes: Int,
    val hasEquipment: Boolean,
    val caloriesBurned: Int,
    val description: String,
    val steps: String,
    val isCompleted: Boolean = false,
    val isBookmarked: Boolean = false
)
