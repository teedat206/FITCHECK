package com.example.fitcheck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.WeightRecord
import com.example.fitcheck.data.repository.ProfileRepository
import com.example.fitcheck.ui.AppPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeightViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val dao = db.weightDao()
    private val profileRepo = ProfileRepository(db.bodyProfileDao(), db.userDao())
    val userId = AppPreferences.getCurrentUserId(application)

    val allWeights: LiveData<List<WeightRecord>> = dao.getAllByUser(userId).asLiveData()

    private val _insertStatus = MutableLiveData<Result<String>?>()
    val insertStatus: LiveData<Result<String>?> = _insertStatus

    fun insert(weight: Float, date: String, session: String) = viewModelScope.launch(Dispatchers.IO) {
        if (userId == -1L) {
            _insertStatus.postValue(Result.failure(Exception("Tài khoản không tồn tại, vui lòng đăng nhập lại")))
            return@launch
        }
        try {
            val record = WeightRecord(userId = userId, weight = weight, date = date, session = session)
            dao.insert(record)
            profileRepo.updateWeightAndRecalculate(userId, weight)
            AppPreferences.setUserWeight(getApplication(), weight)
            _insertStatus.postValue(Result.success("Đã thêm cân nặng thành công!"))
        } catch (e: Exception) {
            _insertStatus.postValue(Result.failure(Exception("Lỗi lưu dữ liệu: ${e.localizedMessage}")))
        }
    }

    fun update(record: WeightRecord) = viewModelScope.launch(Dispatchers.IO) {
        try {
            dao.update(record.copy(userId = userId))
            profileRepo.updateWeightAndRecalculate(userId, record.weight)
            AppPreferences.setUserWeight(getApplication(), record.weight)
        } catch (e: Exception) {
            // Handled
        }
    }

    fun delete(record: WeightRecord) = viewModelScope.launch(Dispatchers.IO) {
        try {
            dao.delete(record)
            val latest = dao.getAllOnce(userId).lastOrNull()
            if (latest != null) {
                profileRepo.updateWeightAndRecalculate(userId, latest.weight)
                AppPreferences.setUserWeight(getApplication(), latest.weight)
            }
        } catch (e: Exception) {
            // Handled
        }
    }

    fun clearStatus() { _insertStatus.value = null }
}
