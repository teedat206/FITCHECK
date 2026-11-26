package com.example.fitcheck.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import com.example.fitcheck.R

object BottomNavHelper {
    fun setupBottomNav(activity: Activity) {
        val navHome = activity.findViewById<View>(R.id.navHome)
        val navWeight = activity.findViewById<View>(R.id.navWeight)
        val navSuggest = activity.findViewById<View>(R.id.navSuggest) ?: activity.findViewById<View>(R.id.navRecommendations)
        val navProfile = activity.findViewById<View>(R.id.navProfile)

        navHome?.setOnClickListener {
            if (activity !is HomeActivity) {
                val intent = Intent(activity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                activity.startActivity(intent)
                activity.overridePendingTransition(0, 0)
            }
        }

        navWeight?.setOnClickListener {
            if (activity !is WeightHistoryActivity) {
                val intent = Intent(activity, WeightHistoryActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                activity.startActivity(intent)
                activity.overridePendingTransition(0, 0)
            }
        }

        navSuggest?.setOnClickListener {
            if (activity !is SuggestActivity) {
                val intent = Intent(activity, SuggestActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                activity.startActivity(intent)
                activity.overridePendingTransition(0, 0)
            }
        }

        navProfile?.setOnClickListener {
            if (activity !is SettingsActivity) {
                val intent = Intent(activity, SettingsActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                activity.startActivity(intent)
                activity.overridePendingTransition(0, 0)
            }
        }
    }
}