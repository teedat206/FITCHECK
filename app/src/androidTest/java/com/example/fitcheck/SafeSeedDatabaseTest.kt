package com.example.fitcheck

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.SeedData
import com.example.fitcheck.data.entity.MealEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SafeSeedDatabaseTest {

    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testRepeatedSeedingDoesNotDuplicateData() = runBlocking {
        // a. Call insertAll 3 times
        db.mealDao().insertAll(SeedData.meals())
        db.workoutDao().insertAll(SeedData.workouts())

        db.mealDao().insertAll(SeedData.meals())
        db.workoutDao().insertAll(SeedData.workouts())

        db.mealDao().insertAll(SeedData.meals())
        db.workoutDao().insertAll(SeedData.workouts())

        assertEquals("Số lượng món ăn phải giữ nguyên là 138 sau 3 lần seed", 138, db.mealDao().count())
        assertEquals("Số lượng bài tập phải giữ nguyên là 60 sau 3 lần seed", 60, db.workoutDao().count())
    }

    @Test
    fun testMealBookmarkStateAndIdPreservedAfterReseeding() = runBlocking {
        db.mealDao().insertAll(SeedData.meals())
        val firstMeal = db.mealDao().getById(1L)
        assertNotNull("Món ăn id 1 tồn tại", firstMeal)

        db.mealDao().setBookmark(1L, true)
        val bookmarkedBefore = db.mealDao().getById(1L)
        assertTrue("Món ăn id 1 đã được bookmark", bookmarkedBefore?.isBookmarked == true)

        // Reseed
        db.mealDao().insertAll(SeedData.meals())

        val bookmarkedAfter = db.mealDao().getById(1L)
        assertEquals("ID không đổi", 1L, bookmarkedAfter?.id)
        assertTrue("Món đó vẫn isBookmarked = true sau khi reseed", bookmarkedAfter?.isBookmarked == true)
    }

    @Test
    fun testWorkoutCompletedStatePreservedAfterReseeding() = runBlocking {
        db.workoutDao().insertAll(SeedData.workouts())
        db.workoutDao().setCompleted(1L, true)

        val completedBefore = db.workoutDao().getById(1L)
        assertTrue("Bài tập id 1 đã hoàn thành", completedBefore?.isCompleted == true)

        // Reseed
        db.workoutDao().insertAll(SeedData.workouts())

        val completedAfter = db.workoutDao().getById(1L)
        assertTrue("Bài tập vẫn isCompleted = true sau khi reseed", completedAfter?.isCompleted == true)
    }

    @Test
    fun testDuplicateMealNameIgnoredWithoutException() = runBlocking {
        val meal1 = MealEntity(name = "Phở bò đặc biệt", goal = "Giữ cân", mealType = "Sáng", calories = 500, prepTimeMinutes = 20, description = "Mô tả 1", ingredients = "Thịt bò")
        val meal2 = MealEntity(name = "Phở bò đặc biệt", goal = "Tăng cân", mealType = "Trưa", calories = 700, prepTimeMinutes = 30, description = "Mô tả 2", ingredients = "Thịt bò nạm")

        db.mealDao().insertAll(listOf(meal1))
        assertEquals(1, db.mealDao().count())

        db.mealDao().insertAll(listOf(meal2))
        assertEquals("Dòng thứ hai bị IGNORE bỏ qua, số lượng vẫn là 1", 1, db.mealDao().count())

        val stored = db.mealDao().getById(1L)
        assertEquals("Giữ nguyên thông tin của dòng đầu tiên", "Giữ cân", stored?.goal)
    }
}
