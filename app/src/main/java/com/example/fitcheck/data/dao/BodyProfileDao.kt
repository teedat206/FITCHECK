package com.example.fitcheck.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fitcheck.data.entity.BodyProfileEntity
import com.example.fitcheck.data.entity.UserEntity

// BodyProfileDao.kt
@Dao
interface BodyProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(profile: BodyProfileEntity)

    @Query("SELECT * FROM body_profiles WHERE userId = :userId")
    fun observe(userId: Long): LiveData<BodyProfileEntity?>

    @Query("SELECT * FROM body_profiles WHERE userId = :userId")
    suspend fun getOnce(userId: Long): BodyProfileEntity?
}