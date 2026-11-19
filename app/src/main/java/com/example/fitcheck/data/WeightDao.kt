package com.example.fitcheck.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeightDao {

    @Insert
    suspend fun insert(record: WeightRecord)

    @Update
    suspend fun update(record: WeightRecord)

    @Delete
    suspend fun delete(record: WeightRecord)

    // Lấy toàn bộ danh sách, sắp xếp bản ghi mới nhất lên đầu
    @Query("SELECT * FROM weight_table ORDER BY id DESC")
    fun getAllWeights(): Flow<List<WeightRecord>>
}