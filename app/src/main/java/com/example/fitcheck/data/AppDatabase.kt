package com.example.fitcheck.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.fitcheck.data.dao.*
import com.example.fitcheck.data.entity.*

@Database(
    entities = [WeightRecord::class, UserEntity::class, BodyProfileEntity::class,
        MealEntity::class, WorkoutEntity::class, ReminderEntity::class],
    version = 4,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao
    abstract fun userDao(): UserDao
    abstract fun bodyProfileDao(): BodyProfileDao
    abstract fun mealDao(): MealDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        private const val TAG = "AppDatabase"
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "fitcheck_database"
                )
                    // TODO: Trước khi phát hành ứng dụng, hãy thay thế fallbackToDestructiveMigration() bằng các Migration thật để tránh mất dữ liệu người dùng.
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    INSTANCE?.let {
                                        it.mealDao().insertAll(SeedData.meals())
                                        it.workoutDao().insertAll(SeedData.workouts())
                                    }
                                } catch (e: Exception) {
                                    Log.e(TAG, "Lỗi khi nạp dữ liệu seed: ${e.localizedMessage}", e)
                                }
                            }
                        }
                    })
                    .build().also { INSTANCE = it }
            }

        fun getDatabase(context: Context): AppDatabase = getInstance(context)
    }
}
