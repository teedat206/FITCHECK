package com.example.fitcheck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.entity.BodyProfileEntity
import com.example.fitcheck.data.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {
    private val db = AppDatabase.getInstance(app)
    private val repo = ProfileRepository(db.bodyProfileDao(), db.userDao())

    fun observeProfile(userId: Long): LiveData<BodyProfileEntity?> = repo.observe(userId)

    /** onDone chạy trên Main thread sau khi lưu xong (dùng để chuyển sang ResultActivity) */
    fun save(
        userId: Long, gender: String, age: Int, heightCm: Float,
        weightKg: Float, targetKg: Float, activity: String, goal: String,
        onDone: () -> Unit = {}
    ) {
        viewModelScope.launch {
            repo.save(userId, gender, age, heightCm, weightKg, targetKg, activity, goal)
            onDone()
        }
    }
}