package com.example.fitcheck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.entity.MealEntity
import com.example.fitcheck.data.repository.MealRepository
import kotlinx.coroutines.launch

data class MealFilter(
    val keyword: String = "",
    val goal: String = "",        // "Giảm cân" / "Giữ cân" / "Tăng cân", rỗng = tất cả
    val maxPrep: Int? = null,
    val minKcal: Int = 0,
    val maxKcal: Int = 5000
)

class MealViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = MealRepository(AppDatabase.getInstance(app).mealDao())

    private val filter = MutableLiveData(MealFilter())

    /** Tự query lại mỗi khi bộ lọc thay đổi */
    val meals: LiveData<List<MealEntity>> = filter.switchMap { f ->
        repo.search(f.keyword, f.goal, f.maxPrep, f.minKcal, f.maxKcal)
    }

    val bookmarked: LiveData<List<MealEntity>> = repo.getBookmarked()

    fun setFilter(f: MealFilter) { filter.value = f }
    fun setKeyword(keyword: String) { filter.value = (filter.value ?: MealFilter()).copy(keyword = keyword) }

    fun byGoal(goal: String): LiveData<List<MealEntity>> = repo.getByGoal(goal)

    fun toggleBookmark(meal: MealEntity) {
        viewModelScope.launch { repo.setBookmark(meal.id, !meal.isBookmarked) }
    }

    companion object {
        /** Đổi goal của hồ sơ (GIAM_CAN...) sang chuỗi goal của món ăn */
        fun goalToMealGoal(profileGoal: String): String = when (profileGoal) {
            "GIAM_CAN" -> "Giảm cân"
            "TANG_CAN" -> "Tăng cân"
            else -> "Giữ cân"
        }
    }
}