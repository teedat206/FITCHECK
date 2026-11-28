package com.example.fitcheck.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitcheck.data.entity.MealEntity

@Dao
interface MealDao {
    @Query("SELECT * FROM meals WHERE goal = :goal ORDER BY mealType, name")
    fun getByGoal(goal: String): LiveData<List<MealEntity>>

    @Query("""
        SELECT * FROM meals
        WHERE (:keyword = '' OR name LIKE '%' || :keyword || '%')
          AND (:goal = '' OR goal = :goal)
          AND (:mealType = '' OR mealType = :mealType)
          AND (:maxPrep IS NULL OR prepTimeMinutes <= :maxPrep)
          AND calories BETWEEN :minKcal AND :maxKcal
        ORDER BY name
    """)
    fun search(
        keyword: String, goal: String, mealType: String, maxPrep: Int?, minKcal: Int, maxKcal: Int
    ): LiveData<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE isBookmarked = 1")
    fun getBookmarked(): LiveData<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): MealEntity?

    @Query("UPDATE meals SET isBookmarked = :value WHERE id = :id")
    suspend fun setBookmark(id: Long, value: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<MealEntity>)

    @Query("SELECT COUNT(*) FROM meals")
    suspend fun count(): Int
}
