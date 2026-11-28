package com.example.fitcheck.data.repository

import com.example.fitcheck.data.BodyCalculator
import com.example.fitcheck.data.dao.BodyProfileDao
import com.example.fitcheck.data.dao.UserDao
import com.example.fitcheck.data.entity.ActivityLevel
import com.example.fitcheck.data.entity.BodyProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class ProfileResult {
    data class Success(val profile: BodyProfileEntity) : ProfileResult()
    data class Error(val message: String) : ProfileResult()
}

class ProfileRepository(
    private val profileDao: BodyProfileDao,
    private val userDao: UserDao
) {
    companion object {
        const val GENDER_MALE = "Nam"
        const val GENDER_FEMALE = "Nữ"

        const val GOAL_LOSE = "GIAM_CAN"
        const val GOAL_MAINTAIN = "GIU_CAN"
        const val GOAL_GAIN = "TANG_CAN"

        fun mapProfileGoalToMealGoal(profileGoal: String): String = when (profileGoal) {
            GOAL_LOSE -> "Giảm cân"
            GOAL_GAIN -> "Tăng cân"
            else -> "Giữ cân"
        }
    }

    fun observe(userId: Long) = profileDao.observe(userId)

    suspend fun getOnce(userId: Long): BodyProfileEntity? = withContext(Dispatchers.IO) {
        profileDao.getOnce(userId)
    }

    suspend fun updateWeightAndRecalculate(userId: Long, newWeightKg: Float): Boolean = withContext(Dispatchers.IO) {
        val currentProfile = profileDao.getOnce(userId) ?: return@withContext false
        val metrics = BodyCalculator.computeProfileMetrics(currentProfile.copy(currentWeightKg = newWeightKg))
        val updatedProfile = currentProfile.copy(
            currentWeightKg = newWeightKg,
            bmi = metrics.bmi,
            dailyCalories = metrics.targetCalories,
            updatedAt = System.currentTimeMillis()
        )
        profileDao.upsert(updatedProfile)
        true
    }

    suspend fun save(
        userId: Long, gender: String, age: Int, heightCm: Float,
        weightKg: Float, targetKg: Float, activityKey: String, goal: String
    ): ProfileResult = withContext(Dispatchers.IO) {
        val activityLevel = ActivityLevel.fromKey(activityKey)
            ?: return@withContext ProfileResult.Error("Mức độ hoạt động không hợp lệ: $activityKey")

        if (age !in 18..120) {
            return@withContext ProfileResult.Error("Tuổi hợp lệ từ 18 đến 120 tuổi")
        }
        if (heightCm !in 50f..250f) {
            return@withContext ProfileResult.Error("Chiều cao hợp lệ từ 50cm đến 250cm")
        }
        if (weightKg !in 20f..300f) {
            return@withContext ProfileResult.Error("Cân nặng hợp lệ từ 20kg đến 300kg")
        }
        if (targetKg !in 20f..300f) {
            return@withContext ProfileResult.Error("Cân nặng mục tiêu hợp lệ từ 20kg đến 300kg")
        }

        val metrics = BodyCalculator.computeProfileMetrics(
            BodyProfileEntity(
                userId = userId,
                gender = gender,
                age = age,
                heightCm = heightCm,
                currentWeightKg = weightKg,
                targetWeightKg = targetKg,
                activityLevel = activityLevel.key,
                goal = goal,
                bmi = 0f,
                dailyCalories = 0
            )
        )

        val profile = BodyProfileEntity(
            userId = userId,
            gender = gender,
            age = age,
            heightCm = heightCm,
            currentWeightKg = weightKg,
            targetWeightKg = targetKg,
            activityLevel = activityLevel.key,
            goal = goal,
            bmi = metrics.bmi,
            dailyCalories = metrics.targetCalories
        )

        try {
            profileDao.upsert(profile)
            userDao.setProfileCompleted(userId, true)
            ProfileResult.Success(profile)
        } catch (e: Exception) {
            ProfileResult.Error("Không thể lưu hồ sơ cơ thể: ${e.localizedMessage}")
        }
    }
}
