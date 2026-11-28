package com.example.fitcheck.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "meals",
    indices = [Index(value = ["name"], unique = true)]
)
data class MealEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val goal: String,            // "Giảm cân" / "Giữ cân" / "Tăng cân"
    val mealType: String,        // "Sáng" / "Trưa" / "Tối" / "Phụ"
    val calories: Int,
    val prepTimeMinutes: Int,
    val description: String,
    val ingredients: String,
    val imageResName: String? = null,
    val isBookmarked: Boolean = false
)
