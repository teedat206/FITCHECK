package com.example.fitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val userId = AppPreferences.getCurrentUserId(this)
            
            if (userId == -1L) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    val db = AppDatabase.getInstance(this@MainActivity)
                    val email = AppPreferences.getUserEmail(this@MainActivity)
                    val user = if (email.isNotEmpty()) db.userDao().findByEmail(email) else null

                    withContext(Dispatchers.Main) {
                        if (user == null) {
                            AppPreferences.clearSession(this@MainActivity)
                            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        } else {
                            val hasCompleted = user.hasCompletedProfile
                            AppPreferences.setHasCompletedProfile(this@MainActivity, hasCompleted)
                            
                            val intent = if (!hasCompleted) {
                                Intent(this@MainActivity, ProfileActivity::class.java)
                            } else {
                                Intent(this@MainActivity, HomeActivity::class.java)
                            }
                            startActivity(intent)
                        }
                        finish()
                    }
                }
            }
        }, 1500)
    }
}
