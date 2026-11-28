package com.example.fitcheck

import com.example.fitcheck.data.SeedData
import org.junit.Assert.*
import org.junit.Test

class SeedDataTest {

    @Test
    fun testMealCountAndDistribution() {
        val meals = SeedData.meals()
        assertEquals("Tổng số món ăn phải là 138 món", 138, meals.size)

        val loseMeals = meals.filter { it.goal == SeedData.GOAL_LOSE }
        val keepMeals = meals.filter { it.goal == SeedData.GOAL_KEEP }
        val gainMeals = meals.filter { it.goal == SeedData.GOAL_GAIN }

        assertEquals("Mỗi goal phải có đúng 46 món", 46, loseMeals.size)
        assertEquals("Mỗi goal phải có đúng 46 món", 46, keepMeals.size)
        assertEquals("Mỗi goal phải có đúng 46 món", 46, gainMeals.size)

        for (goalMeals in listOf(loseMeals, keepMeals, gainMeals)) {
            assertEquals("Sáng phải có đúng 12 món", 12, goalMeals.count { it.mealType == "Sáng" })
            assertEquals("Trưa phải có đúng 12 món", 12, goalMeals.count { it.mealType == "Trưa" })
            assertEquals("Tối phải có đúng 12 món", 12, goalMeals.count { it.mealType == "Tối" })
            assertEquals("Phụ phải có đúng 10 món", 10, goalMeals.count { it.mealType == "Phụ" })
        }
    }

    @Test
    fun testWorkoutCountAndDistribution() {
        val workouts = SeedData.workouts()
        assertEquals("Tổng số bài tập phải là 60 bài", 60, workouts.size)

        val lightWorkouts = workouts.filter { it.intensity == SeedData.INTENSITY_LIGHT }
        val modWorkouts = workouts.filter { it.intensity == SeedData.INTENSITY_MODERATE }
        val highWorkouts = workouts.filter { it.intensity == SeedData.INTENSITY_HIGH }

        assertEquals("Mức Nhẹ phải có đúng 20 bài", 20, lightWorkouts.size)
        assertEquals("Mức Vừa phải có đúng 20 bài", 20, modWorkouts.size)
        assertEquals("Mức Cao phải có đúng 20 bài", 20, highWorkouts.size)

        for (list in listOf(lightWorkouts, modWorkouts, highWorkouts)) {
            assertTrue("Có dụng cụ phải >= 8 bài", list.count { it.hasEquipment } >= 8)
            assertTrue("Không dụng cụ phải >= 8 bài", list.count { !it.hasEquipment } >= 8)
        }
    }

    @Test
    fun testMealNamesAreUniqueCaseInsensitive() {
        val meals = SeedData.meals()
        val names = meals.map { it.name.lowercase().trim() }
        val uniqueNames = names.toSet()

        assertEquals("Tất cả các món ăn phải có tên duy nhất không phân biệt hoa thường", names.size, uniqueNames.size)
    }

    @Test
    fun testWorkoutNamesAreUniqueCaseInsensitive() {
        val workouts = SeedData.workouts()
        val names = workouts.map { it.name.lowercase().trim() }
        val uniqueNames = names.toSet()

        assertEquals("Tất cả các bài tập phải có tên duy nhất không phân biệt hoa thường", names.size, uniqueNames.size)
    }

    @Test
    fun testMealCalorieBoundsAndValidation() {
        for (meal in SeedData.meals()) {
            assertTrue("Thời gian chuẩn bị trong khoảng 2..60 phút", meal.prepTimeMinutes in 2..60)
            assertTrue("Mô tả không được rỗng", meal.description.isNotBlank())
            assertTrue("Nguyên liệu không được rỗng", meal.ingredients.isNotBlank())
            assertTrue("Nguyên liệu phải chứa ít nhất 2 mục phân tách bằng dấu phẩy", meal.ingredients.contains(","))

            when (meal.goal) {
                SeedData.GOAL_LOSE -> {
                    if (meal.mealType == "Sáng" || meal.mealType == "Phụ") {
                        assertTrue("Giảm cân Sáng/Phụ trong khoảng 120-300 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 120..300)
                    } else {
                        assertTrue("Giảm cân Trưa/Tối trong khoảng 200-400 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 200..400)
                    }
                }
                SeedData.GOAL_KEEP -> {
                    when (meal.mealType) {
                        "Sáng" -> assertTrue("Giữ cân Sáng trong khoảng 300-420 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 300..420)
                        "Phụ" -> assertTrue("Giữ cân Phụ trong khoảng 150-250 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 150..250)
                        else -> assertTrue("Giữ cân Trưa/Tối trong khoảng 420-560 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 420..560)
                    }
                }
                SeedData.GOAL_GAIN -> {
                    when (meal.mealType) {
                        "Sáng" -> assertTrue("Tăng cân Sáng trong khoảng 500-650 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 500..650)
                        "Phụ" -> assertTrue("Tăng cân Phụ trong khoảng 350-500 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 350..500)
                        else -> assertTrue("Tăng cân Trưa/Tối trong khoảng 650-850 kcal (Món: ${meal.name}, calo: ${meal.calories})", meal.calories in 650..850)
                    }
                }
            }

            val lowerDesc = meal.description.lowercase()
            assertFalse("Không chứa khẳng định y khoa 'chữa' (Món: ${meal.name})", lowerDesc.contains("chữa"))
            assertFalse("Không chứa khẳng định y khoa 'đảm bảo' (Món: ${meal.name})", lowerDesc.contains("đảm bảo"))
            assertFalse("Không chứa khẳng định y khoa 'đốt mỡ' (Món: ${meal.name})", lowerDesc.contains("đốt mỡ"))
            assertFalse("Không chứa khẳng định y khoa 'detox' (Món: ${meal.name})", lowerDesc.contains("detox"))
            assertFalse("Không chứa từ ngữ 'thần kỳ' (Món: ${meal.name})", lowerDesc.contains("thần kỳ"))
        }
    }

    @Test
    fun testWorkoutValidation() {
        for (workout in SeedData.workouts()) {
            assertTrue("Thời lượng bài tập từ 10 đến 45 phút (Bài: ${workout.name})", workout.durationMinutes in 10..45)
            assertTrue("Mô tả bài tập không rỗng (Bài: ${workout.name})", workout.description.isNotBlank())
            assertTrue("Các bước tập bắt đầu bằng '1.' (Bài: ${workout.name})", workout.steps.startsWith("1."))
            assertTrue("Các bước tập chứa ít nhất 3 bước '3.' (Bài: ${workout.name})", workout.steps.contains("3."))

            when (workout.intensity) {
                SeedData.INTENSITY_LIGHT -> {
                    assertTrue("Calo đốt mức Nhẹ từ 30..150 kcal (Bài: ${workout.name})", workout.caloriesBurned in 30..150)
                }
                SeedData.INTENSITY_MODERATE -> {
                    assertTrue("Calo đốt mức Vừa từ 150..300 kcal (Bài: ${workout.name})", workout.caloriesBurned in 150..300)
                    val lowerSteps = workout.steps.lowercase()
                    assertTrue("Mức Vừa phải có 'khởi động' trong steps (Bài: ${workout.name})", lowerSteps.contains("khởi động"))
                    assertTrue("Mức Vừa phải có 'thả lỏng' trong steps (Bài: ${workout.name})", lowerSteps.contains("thả lỏng"))
                }
                SeedData.INTENSITY_HIGH -> {
                    assertTrue("Calo đốt mức Cao từ 220..450 kcal (Bài: ${workout.name})", workout.caloriesBurned in 220..450)
                    val lowerSteps = workout.steps.lowercase()
                    assertTrue("Mức Cao phải có 'khởi động' trong steps (Bài: ${workout.name})", lowerSteps.contains("khởi động"))
                    assertTrue("Mức Cao phải có 'thả lỏng' trong steps (Bài: ${workout.name})", lowerSteps.contains("thả lỏng"))

                    val lowerDesc = workout.description.lowercase()
                    assertTrue("Mức Cao phải có lời nhắc an toàn trong description (Bài: ${workout.name})", lowerDesc.contains("lưu ý an toàn") || lowerDesc.contains("dừng"))
                }
            }

            val lowerDesc = workout.description.lowercase()
            assertFalse("Không chứa khẳng định y khoa 'chữa' (Bài: ${workout.name})", lowerDesc.contains("chữa"))
            assertFalse("Không chứa khẳng định y khoa 'đảm bảo' (Bài: ${workout.name})", lowerDesc.contains("đảm bảo"))
            assertFalse("Không chứa từ ngữ 'thần kỳ' (Bài: ${workout.name})", lowerDesc.contains("thần kỳ"))
        }
    }
}
