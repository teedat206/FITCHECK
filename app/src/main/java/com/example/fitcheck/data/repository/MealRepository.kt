package com.example.fitcheck.data.repository

import androidx.lifecycle.LiveData
import com.example.fitcheck.data.dao.MealDao
import com.example.fitcheck.data.entity.MealEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealRepository(private val dao: MealDao) {

    fun getByGoal(goal: String): LiveData<List<MealEntity>> = dao.getByGoal(goal)

    fun search(
        keyword: String = "", goal: String = "", maxPrep: Int? = null,
        minKcal: Int = 0, maxKcal: Int = 5000
    ): LiveData<List<MealEntity>> = dao.search(keyword, goal, "", maxPrep, minKcal, maxKcal)

    fun searchWithMealType(
        keyword: String = "", goal: String = "", mealType: String = "", maxPrep: Int? = null,
        minKcal: Int = 0, maxKcal: Int = 5000
    ): LiveData<List<MealEntity>> = dao.search(keyword, goal, mealType, maxPrep, minKcal, maxKcal)

    fun getBookmarked(): LiveData<List<MealEntity>> = dao.getBookmarked()

    suspend fun setBookmark(id: Long, value: Boolean) =
        withContext(Dispatchers.IO) { dao.setBookmark(id, value) }
}
