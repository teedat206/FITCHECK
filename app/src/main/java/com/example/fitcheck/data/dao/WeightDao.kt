package com.example.fitcheck.data.dao

import androidx.room.*
import com.example.fitcheck.data.WeightRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: WeightRecord)

    @Update
    suspend fun update(record: WeightRecord)

    @Delete
    suspend fun delete(record: WeightRecord)

    @Query("SELECT * FROM weight_table ORDER BY id DESC")
    fun getAllWeights(): Flow<List<WeightRecord>>

    @Query("SELECT * FROM weight_table ORDER BY id DESC")
    fun getAll(): Flow<List<WeightRecord>>

    @Query("SELECT * FROM weight_table WHERE userId = :userId ORDER BY id DESC")
    fun getAllByUser(userId: Long): Flow<List<WeightRecord>>

    @Query("SELECT * FROM weight_table WHERE userId = :userId ORDER BY id ASC")
    suspend fun getAllOnce(userId: Long): List<WeightRecord>

    @Query("""
        SELECT * FROM weight_table 
        WHERE userId = :userId 
          AND substr(date,7,4) || substr(date,4,2) || substr(date,1,2) BETWEEN :from AND :to 
        ORDER BY id DESC
    """)
    suspend fun getBetween(userId: Long, from: String, to: String): List<WeightRecord>

    @Query("""
        SELECT * FROM weight_table 
        WHERE userId = :userId 
          AND substr(date,7,4) || substr(date,4,2) || substr(date,1,2) BETWEEN :from AND :to 
        ORDER BY id DESC
    """)
    fun getBetweenFlow(userId: Long, from: String, to: String): Flow<List<WeightRecord>>
}
