package com.example.fitcheck.data

import com.example.fitcheck.data.entity.MealEntity
import com.example.fitcheck.data.entity.WorkoutEntity

// Dữ liệu minh họa, calo chỉ là ước lượng đối chiếu theo Bảng thành phần thực phẩm VN / USDA FoodData Central
object SeedData {

    const val GOAL_LOSE = "Giảm cân"
    const val GOAL_KEEP = "Giữ cân"
    const val GOAL_GAIN = "Tăng cân"

    const val INTENSITY_LIGHT = "Nhẹ"
    const val INTENSITY_MODERATE = "Vừa"
    const val INTENSITY_HIGH = "Cao"

    fun meals(): List<MealEntity> {
        val list = mutableListOf<MealEntity>()
        list.addAll(SeedMealsLose.getMeals())
        list.addAll(SeedMealsKeep.getMeals())
        list.addAll(SeedMealsGain.getMeals())
        return list
    }

    fun workouts(): List<WorkoutEntity> {
        val list = mutableListOf<WorkoutEntity>()
        list.addAll(SeedWorkoutsLight.getWorkouts())
        list.addAll(SeedWorkoutsModerate.getWorkouts())
        list.addAll(SeedWorkoutsHigh.getWorkouts())
        return list
    }
}
