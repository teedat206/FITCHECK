package com.example.fitcheck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.WeightRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeightViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).weightDao()

    // Lấy danh sách dạng LiveData để Activity dễ dàng quan sát (Observe)
    val allWeights = dao.getAllWeights().asLiveData()

    fun insert(record: WeightRecord) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(record)
    }

    fun update(record: WeightRecord) = viewModelScope.launch(Dispatchers.IO) {
        dao.update(record)
    }

    fun delete(record: WeightRecord) = viewModelScope.launch(Dispatchers.IO) {
        dao.delete(record)
    }
}