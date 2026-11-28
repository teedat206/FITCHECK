package com.example.fitcheck.ui

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {
    private const val PREFS_NAME = "fitcheck_prefs"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PHONE = "user_phone"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_UNITS = "units"
    private const val KEY_LANGUAGE = "language"
    
    private const val KEY_HAS_COMPLETED_PROFILE = "has_completed_profile"
    private const val KEY_CURRENT_USER_ID = "current_user_id"

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- Current Session Data ---
    fun getCurrentUserId(context: Context): Long = getPrefs(context).getLong(KEY_CURRENT_USER_ID, -1L)
    fun setCurrentUserId(context: Context, userId: Long) = getPrefs(context).edit().putLong(KEY_CURRENT_USER_ID, userId).apply()

    fun isLoggedIn(context: Context): Boolean = getCurrentUserId(context) != -1L
    fun setLoggedIn(context: Context, loggedIn: Boolean) {
        if (!loggedIn) {
            setCurrentUserId(context, -1L)
        }
    }
    
    fun hasCompletedProfile(context: Context): Boolean = getPrefs(context).getBoolean(KEY_HAS_COMPLETED_PROFILE, false)
    fun setHasCompletedProfile(context: Context, completed: Boolean) = getPrefs(context).edit().putBoolean(KEY_HAS_COMPLETED_PROFILE, completed).apply()

    private const val KEY_USER_HEIGHT = "user_height"
    private const val KEY_USER_WEIGHT = "user_weight"
    private const val KEY_USER_AGE = "user_age"
    private const val KEY_USER_GENDER = "user_gender"

    fun getUserName(context: Context): String = getPrefs(context).getString(KEY_USER_NAME, "Người dùng") ?: "Người dùng"
    fun setUserName(context: Context, name: String) = getPrefs(context).edit().putString(KEY_USER_NAME, name).apply()

    fun getUserEmail(context: Context): String = getPrefs(context).getString(KEY_USER_EMAIL, "") ?: ""
    fun setUserEmail(context: Context, email: String) = getPrefs(context).edit().putString(KEY_USER_EMAIL, email).apply()

    fun getUserPhone(context: Context): String = getPrefs(context).getString(KEY_USER_PHONE, "") ?: ""
    fun setUserPhone(context: Context, phone: String) = getPrefs(context).edit().putString(KEY_USER_PHONE, phone).apply()

    fun getUserHeight(context: Context): Float = getPrefs(context).getFloat(KEY_USER_HEIGHT, 160f)
    fun setUserHeight(context: Context, height: Float) = getPrefs(context).edit().putFloat(KEY_USER_HEIGHT, height).apply()

    fun getUserWeight(context: Context): Float = getPrefs(context).getFloat(KEY_USER_WEIGHT, 60.0f)
    fun setUserWeight(context: Context, weight: Float) = getPrefs(context).edit().putFloat(KEY_USER_WEIGHT, weight).apply()

    fun getUserAge(context: Context): Int = getPrefs(context).getInt(KEY_USER_AGE, 24)
    fun setUserAge(context: Context, age: Int) = getPrefs(context).edit().putInt(KEY_USER_AGE, age).apply()

    fun getUserGender(context: Context): String = getPrefs(context).getString(KEY_USER_GENDER, "Nam") ?: "Nam"
    fun setUserGender(context: Context, gender: String) = getPrefs(context).edit().putString(KEY_USER_GENDER, gender).apply()

    // --- Settings ---
    fun isDarkMode(context: Context): Boolean = getPrefs(context).getBoolean(KEY_DARK_MODE, false)
    fun setDarkMode(context: Context, isDark: Boolean) = getPrefs(context).edit().putBoolean(KEY_DARK_MODE, isDark).apply()

    fun getUnits(context: Context): String = getPrefs(context).getString(KEY_UNITS, "kg") ?: "kg"
    fun setUnits(context: Context, units: String) = getPrefs(context).edit().putString(KEY_UNITS, units).apply()

    fun getLanguage(context: Context): String = getPrefs(context).getString(KEY_LANGUAGE, "vi") ?: "vi"
    fun setLanguage(context: Context, lang: String) = getPrefs(context).edit().putString(KEY_LANGUAGE, lang).apply()

    fun clearSession(context: Context) {
        getPrefs(context).edit()
            .remove(KEY_CURRENT_USER_ID)
            .remove(KEY_HAS_COMPLETED_PROFILE)
            .remove(KEY_USER_NAME)
            .remove(KEY_USER_EMAIL)
            .remove(KEY_USER_PHONE)
            .apply()
    }
}
