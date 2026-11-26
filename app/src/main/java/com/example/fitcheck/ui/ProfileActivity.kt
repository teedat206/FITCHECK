package com.example.fitcheck.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private var isMale = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<View>(R.id.ivSettings)?.setOnClickListener {
            finish()
        }

        val cardNam = findViewById<MaterialCardView>(R.id.cardNam)
        val tvNam = findViewById<TextView>(R.id.tvNam)
        val cardNu = findViewById<MaterialCardView>(R.id.cardNu)
        val tvNu = findViewById<TextView>(R.id.tvNu)

        fun updateGenderUI() {
            if (isMale) {
                // Nam active
                cardNam.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                cardNam.cardElevation = 4f
                cardNam.strokeWidth = 2
                cardNam.strokeColor = Color.parseColor("#E5E7EB")
                tvNam.setTextColor(Color.parseColor("#111827"))
                tvNam.setTypeface(null, android.graphics.Typeface.BOLD)

                // Nữ inactive
                cardNu.setCardBackgroundColor(Color.parseColor("#F3F4F6"))
                cardNu.cardElevation = 0f
                cardNu.strokeWidth = 0
                tvNu.setTextColor(Color.parseColor("#6B7280"))
                tvNu.setTypeface(null, android.graphics.Typeface.NORMAL)
            } else {
                // Nữ active
                cardNu.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                cardNu.cardElevation = 4f
                cardNu.strokeWidth = 2
                cardNu.strokeColor = Color.parseColor("#E5E7EB")
                tvNu.setTextColor(Color.parseColor("#111827"))
                tvNu.setTypeface(null, android.graphics.Typeface.BOLD)

                // Nam inactive
                cardNam.setCardBackgroundColor(Color.parseColor("#F3F4F6"))
                cardNam.cardElevation = 0f
                cardNam.strokeWidth = 0
                tvNam.setTextColor(Color.parseColor("#6B7280"))
                tvNam.setTypeface(null, android.graphics.Typeface.NORMAL)
            }
        }

        cardNam.setOnClickListener {
            isMale = true
            updateGenderUI()
        }

        cardNu.setOnClickListener {
            isMale = false
            updateGenderUI()
        }

        // Initialize state
        updateGenderUI()

        val edtHeight = findViewById<android.widget.EditText>(R.id.edtHeight)
        val edtWeight = findViewById<android.widget.EditText>(R.id.edtWeight)
        val edtAge = findViewById<android.widget.EditText>(R.id.edtAge)

        findViewById<View>(R.id.btnSaveBmi)?.setOnClickListener {
            val email = AppPreferences.getUserEmail(this)

            val h = edtHeight?.text.toString().toFloatOrNull() ?: 160f
            val w = edtWeight?.text.toString().toFloatOrNull() ?: 60f
            val a = edtAge?.text.toString().toIntOrNull() ?: 24

            AppPreferences.setUserHeight(this, h)
            AppPreferences.setUserWeight(this, w)
            AppPreferences.setUserAge(this, a)
            AppPreferences.setUserGender(this, if (isMale) "Nam" else "Nữ")
            
            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@ProfileActivity)
                val user = db.userDao().getUserByEmail(email)
                if (user != null) {
                    user.hasCompletedProfile = true
                    db.userDao().update(user)
                }

                // Add an initial weight record if none exists
                val weights = db.weightDao()
                val cal = java.util.Calendar.getInstance()
                val sdfDate = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                val sdfTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                val initialRecord = com.example.fitcheck.data.WeightRecord(
                    weight = w,
                    date = sdfDate.format(cal.time),
                    session = "${sdfTime.format(cal.time)} • Khởi tạo"
                )
                weights.insert(initialRecord)
                
                withContext(Dispatchers.Main) {
                    AppPreferences.setHasCompletedProfile(this@ProfileActivity, true)
                    
                    val intent = Intent(this@ProfileActivity, ResultActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}