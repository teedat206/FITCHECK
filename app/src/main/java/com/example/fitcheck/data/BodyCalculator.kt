package com.example.fitcheck.data

import com.example.fitcheck.data.entity.ActivityLevel
import com.example.fitcheck.data.entity.BodyProfileEntity
import kotlin.math.roundToInt

object BodyCalculator {

    fun calculateBmi(weightKg: Float, heightCm: Float): Float {
        if (heightCm <= 0f) return 0f
        val heightM = heightCm / 100f
        val rawBmi = weightKg / (heightM * heightM)
        return (rawBmi * 10f).roundToInt() / 10f
    }

    fun getBmiCategory(bmi: Float): String {
        return when {
            bmi < 18.5f -> "Thiếu cân"
            bmi <= 24.9f -> "Bình thường"
            bmi <= 29.9f -> "Thừa cân"
            else -> "Béo phì"
        }
    }

    fun calculateBmr(weightKg: Float, heightCm: Float, age: Int, gender: String): Int {
        val genderBonus = if (gender.trim().equals("Nam", ignoreCase = true)) 5 else -161
        val rawBmr = 10f * weightKg + 6.25f * heightCm - 5f * age + genderBonus
        return rawBmr.roundToInt()
    }

    fun calculateTdee(bmr: Int, activityLevel: ActivityLevel): Int {
        return (bmr * activityLevel.factor).roundToInt()
    }

    fun calculateTargetCalories(tdee: Int, goal: String): Int {
        val adjustment = when (goal) {
            "GIAM_CAN" -> -500
            "TANG_CAN" -> 500
            else -> 0
        }
        return tdee + adjustment
    }

    fun computeProfileMetrics(profile: BodyProfileEntity): ProfileMetrics {
        val bmi = calculateBmi(profile.currentWeightKg, profile.heightCm)
        val category = getBmiCategory(bmi)
        val bmr = calculateBmr(profile.currentWeightKg, profile.heightCm, profile.age, profile.gender)
        val activity = ActivityLevel.fromKey(profile.activityLevel) ?: ActivityLevel.LIGHT
        val tdee = calculateTdee(bmr, activity)
        val targetCalories = calculateTargetCalories(tdee, profile.goal)

        return ProfileMetrics(
            bmi = bmi,
            bmiCategory = category,
            bmr = bmr,
            tdee = tdee,
            targetCalories = targetCalories
        )
    }
}

data class ProfileMetrics(
    val bmi: Float,
    val bmiCategory: String,
    val bmr: Int,
    val tdee: Int,
    val targetCalories: Int
)
