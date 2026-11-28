package com.example.fitcheck.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitcheck.data.entity.WorkoutEntity

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts ORDER BY name")
    fun getAll(): LiveData<List<WorkoutEntity>>

    @Query("""
        SELECT * FROM workouts
        WHERE (:keyword = '' OR name LIKE '%' || :keyword || '%')
          AND (:intensity = '' OR intensity = :intensity)
          AND (:maxDuration IS NULL OR durationMinutes <= :maxDuration)
          AND (:hasEquipment IS NULL OR hasEquipment = :hasEquipment)
        ORDER BY name
    """)
    fun search(
        keyword: String, intensity: String, maxDuration: Int?, hasEquipment: Boolean?
    ): LiveData<List<WorkoutEntity>>

    @Query("SELECT * FROM workouts WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): WorkoutEntity?

    @Query("UPDATE workouts SET isCompleted = :value WHERE id = :id")
    suspend fun setCompleted(id: Long, value: Boolean)

    @Query("UPDATE workouts SET isBookmarked = :value WHERE id = :id")
    suspend fun setBookmark(id: Long, value: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(list: List<WorkoutEntity>)

    @Query("SELECT COUNT(*) FROM workouts")
    suspend fun count(): Int
}
