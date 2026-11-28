package com.example.fitcheck.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.fitcheck.data.entity.UserEntity

@Entity(
    tableName = "weight_table",
    indices = [Index("userId")],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class WeightRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,         // Khóa chính tự tăng
    val userId: Long,          // Bắt buộc truyền userId khi khởi tạo
    val weight: Float,         // Số cân nặng (ví dụ: 56.0)
    val date: String,          // Ngày ghi nhận (ví dụ: "05/11/2026")
    val session: String        // Buổi trong ngày (ví dụ: "07:00 • Buổi sáng")
)
