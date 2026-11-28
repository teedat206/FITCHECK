package com.example.fitcheck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.entity.UserEntity
import com.example.fitcheck.data.repository.AuthRepository
import com.example.fitcheck.data.repository.AuthResult
import kotlinx.coroutines.launch

class AuthViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = AuthRepository(AppDatabase.getInstance(app).userDao())

    private val _authResult = MutableLiveData<AuthResult?>()
    val authResult: LiveData<AuthResult?> = _authResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch { _authResult.value = repo.register(name, email, password) }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch { _authResult.value = repo.login(email, password) }
    }

    fun observeUser(userId: Long): LiveData<UserEntity?> = repo.observeUser(userId)

    fun updateInfo(userId: Long, name: String, phone: String?, avatar: String?) {
        viewModelScope.launch { repo.updateInfo(userId, name, phone, avatar) }
    }

    /** Gọi sau khi đã xử lý kết quả, tránh observer chạy lại khi xoay màn hình */
    fun clearResult() { _authResult.value = null }
}