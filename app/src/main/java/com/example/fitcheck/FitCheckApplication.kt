package com.example.fitcheck

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.fitcheck.ui.AppPreferences

class FitCheckApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val isDark = AppPreferences.isDarkMode(this)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
