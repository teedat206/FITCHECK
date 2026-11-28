package com.example.fitcheck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.entity.WorkoutEntity
import com.example.fitcheck.data.repository.WorkoutRepository
import kotlinx.coroutines.launch

data class WorkoutFilter(
    val keyword: String = "",
    val intensity: String = "",       // "Nhẹ" / "Vừa" / "Cao", rỗng = tất cả
    val maxDuration: Int? = null,
    val hasEquipment: Boolean? = null // null = không lọc
)

class WorkoutViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = WorkoutRepository(AppDatabase.getInstance(app).workoutDao())

    private val filter = MutableLiveData(WorkoutFilter())

    val workouts: LiveData<List<WorkoutEntity>> = filter.switchMap { f ->
        repo.search(f.keyword, f.intensity, f.maxDuration, f.hasEquipment)
    }

    val allWorkouts: LiveData<List<WorkoutEntity>> = repo.getAll()

    fun setFilter(f: WorkoutFilter) { filter.value = f }
    fun setKeyword(keyword: String) {
        filter.value = (filter.value ?: WorkoutFilter()).copy(keyword = keyword)
    }

    fun toggleCompleted(w: WorkoutEntity) {
        viewModelScope.launch { repo.setCompleted(w.id, !w.isCompleted) }
    }

    fun toggleBookmark(w: WorkoutEntity) {
        viewModelScope.launch { repo.setBookmark(w.id, !w.isBookmarked) }
    }
}