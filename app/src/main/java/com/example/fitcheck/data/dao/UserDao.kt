package com.example.fitcheck.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitcheck.data.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity): Long

    @Update
    suspend fun update(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun findByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email AND passwordHash = :hash LIMIT 1")
    suspend fun login(email: String, hash: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id")
    fun observeById(id: Long): LiveData<UserEntity?>

    @Query("UPDATE users SET fullName = :name, phone = :phone, avatarUri = :avatar WHERE id = :id")
    suspend fun updateInfo(id: Long, name: String, phone: String?, avatar: String?)

    @Query("UPDATE users SET hasCompletedProfile = :done WHERE id = :id")
    suspend fun setProfileCompleted(id: Long, done: Boolean)
}
