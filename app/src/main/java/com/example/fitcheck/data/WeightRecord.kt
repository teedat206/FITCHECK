package com.example.fitcheck.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_table")
data class WeightRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,         // Khóa chính tự tăng
    val weight: Float,         // Số cân nặng (ví dụ: 56.0)
    val date: String,          // Ngày ghi nhận (ví dụ: "05/11/2026")
    val session: String        // Buổi trong ngày (ví dụ: "07:00 • Buổi sáng")
)