package com.example.fitcheck.ui

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

object AppPreferences {
    private const val PREFS_NAME = "fitcheck_prefs"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_PHONE = "user_phone"
    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_UNITS = "units"
    private const val KEY_LANGUAGE = "language"
    
    // Auth and Onboarding mock data
    private const val KEY_USERS_LIST = "users_list_json"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_HAS_COMPLETED_PROFILE = "has_completed_profile"

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- Current Session Data ---
    fun isLoggedIn(context: Context): Boolean = getPrefs(context).getBoolean(KEY_IS_LOGGED_IN, false)
    fun setLoggedIn(context: Context, loggedIn: Boolean) = getPrefs(context).edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply()
    
    fun hasCompletedProfile(context: Context): Boolean = getPrefs(context).getBoolean(KEY_HAS_COMPLETED_PROFILE, false)
    fun setHasCompletedProfile(context: Context, completed: Boolean) = getPrefs(context).edit().putBoolean(KEY_HAS_COMPLETED_PROFILE, completed).apply()

    private const val KEY_USER_HEIGHT = "user_height"
    private const val KEY_USER_WEIGHT = "user_weight"
    private const val KEY_USER_AGE = "user_age"
    private const val KEY_USER_GENDER = "user_gender"

    fun getUserName(context: Context): String = getPrefs(context).getString(KEY_USER_NAME, "Dat To Chan") ?: "Dat To Chan"
    fun setUserName(context: Context, name: String) = getPrefs(context).edit().putString(KEY_USER_NAME, name).apply()

    fun getUserEmail(context: Context): String = getPrefs(context).getString(KEY_USER_EMAIL, "dattochan@gmail.com") ?: "dattochan@gmail.com"
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

    // --- Users Data Mock ---
    // TODO: Replace with Room DB
    fun getUsersList(context: Context): JSONArray {
        val json = getPrefs(context).getString(KEY_USERS_LIST, "[]") ?: "[]"
        return JSONArray(json)
    }

    fun addUser(context: Context, userJson: JSONObject) {
        val users = getUsersList(context)
        users.put(userJson)
        getPrefs(context).edit().putString(KEY_USERS_LIST, users.toString()).apply()
    }

    fun findUserByEmail(context: Context, email: String): JSONObject? {
        val users = getUsersList(context)
        for (i in 0 until users.length()) {
            val user = users.getJSONObject(i)
            if (user.optString("email") == email) {
                return user
            }
        }
        return null
    }

    // --- Settings ---
    fun isDarkMode(context: Context): Boolean = getPrefs(context).getBoolean(KEY_DARK_MODE, false)
    fun setDarkMode(context: Context, isDark: Boolean) = getPrefs(context).edit().putBoolean(KEY_DARK_MODE, isDark).apply()

    fun getUnits(context: Context): String = getPrefs(context).getString(KEY_UNITS, "kg") ?: "kg"
    fun setUnits(context: Context, units: String) = getPrefs(context).edit().putString(KEY_UNITS, units).apply()

    fun getLanguage(context: Context): String = getPrefs(context).getString(KEY_LANGUAGE, "vi") ?: "vi"
    fun setLanguage(context: Context, lang: String) = getPrefs(context).edit().putString(KEY_LANGUAGE, lang).apply()

    fun clearSession(context: Context) {
        setLoggedIn(context, false)
        setHasCompletedProfile(context, false)
        setUserName(context, "")
        setUserEmail(context, "")
        setUserPhone(context, "")
    }
}
