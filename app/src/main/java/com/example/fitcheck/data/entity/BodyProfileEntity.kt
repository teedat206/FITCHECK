package com.example.fitcheck.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
@Entity(
    tableName = "body_profiles",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"], childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class BodyProfileEntity(
    @PrimaryKey val userId: Long,
    val gender: String,
    val age: Int,
    val heightCm: Float,
    val currentWeightKg: Float,
    val targetWeightKg: Float,
    val activityLevel: String,
    val goal: String,            // GIAM_CAN / GIU_CAN / TANG_CAN
    val bmi: Float,
    val dailyCalories: Int,
    val updatedAt: Long = System.currentTimeMillis()
)