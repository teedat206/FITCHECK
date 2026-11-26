package com.example.fitcheck.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val fullName: String,
    val email: String,
    val password: String,
    val phone: String = "",
    var hasCompletedProfile: Boolean = false
)
