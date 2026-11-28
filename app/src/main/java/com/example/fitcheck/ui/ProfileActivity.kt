package com.example.fitcheck.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.WeightRecord
import com.example.fitcheck.data.entity.ActivityLevel
import com.example.fitcheck.data.repository.ProfileResult
import com.example.fitcheck.viewmodel.ProfileViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private var isMale = false
    private var selectedActivityLevel: ActivityLevel = ActivityLevel.LIGHT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        findViewById<View>(R.id.ivSettings)?.setOnClickListener {
            finish()
        }

        val tvName = findViewById<TextView>(R.id.tvName)
        val cardNam = findViewById<MaterialCardView>(R.id.cardNam)
        val tvNam = findViewById<TextView>(R.id.tvNam)
        val cardNu = findViewById<MaterialCardView>(R.id.cardNu)
        val tvNu = findViewById<TextView>(R.id.tvNu)

        val edtHeight = findViewById<EditText>(R.id.edtHeight)
        val edtWeight = findViewById<EditText>(R.id.edtWeight)
        val edtAge = findViewById<EditText>(R.id.edtAge)

        val layoutActivity = findViewById<View>(R.id.layoutActivity)
        val tvActivityTitle = findViewById<TextView>(R.id.tvActivityTitle)
        val tvActivitySub = findViewById<TextView>(R.id.tvActivitySub)

        fun updateGenderUI() {
            if (isMale) {
                cardNam.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                cardNam.cardElevation = 4f
                cardNam.strokeWidth = 2
                cardNam.strokeColor = Color.parseColor("#E5E7EB")
                tvNam.setTextColor(Color.parseColor("#111827"))
                tvNam.setTypeface(null, android.graphics.Typeface.BOLD)

                cardNu.setCardBackgroundColor(Color.parseColor("#F3F4F6"))
                cardNu.cardElevation = 0f
                cardNu.strokeWidth = 0
                tvNu.setTextColor(Color.parseColor("#6B7280"))
                tvNu.setTypeface(null, android.graphics.Typeface.NORMAL)
            } else {
                cardNu.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                cardNu.cardElevation = 4f
                cardNu.strokeWidth = 2
                cardNu.strokeColor = Color.parseColor("#E5E7EB")
                tvNu.setTextColor(Color.parseColor("#111827"))
                tvNu.setTypeface(null, android.graphics.Typeface.BOLD)

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

        updateGenderUI()

        // Load Activity Level UI
        tvActivityTitle?.text = selectedActivityLevel.label
        tvActivitySub?.text = selectedActivityLevel.description

        layoutActivity?.setOnClickListener {
            val items = ActivityLevel.entries.map { "${it.label}\n${it.description}" }.toTypedArray()
            val selectedIndex = ActivityLevel.entries.indexOf(selectedActivityLevel).let { if (it < 0) 1 else it }

            MaterialAlertDialogBuilder(this)
                .setTitle("Chọn mức độ hoạt động")
                .setSingleChoiceItems(items, selectedIndex) { dialog, which ->
                    selectedActivityLevel = ActivityLevel.entries[which]
                    tvActivityTitle?.text = selectedActivityLevel.label
                    tvActivitySub?.text = selectedActivityLevel.description
                    dialog.dismiss()
                }
                .setNegativeButton("Hủy", null)
                .show()
        }

        val userId = AppPreferences.getCurrentUserId(this)

        // Observe User & BodyProfile from Room DB
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(this@ProfileActivity)
            val email = AppPreferences.getUserEmail(this@ProfileActivity)
            val user = db.userDao().findByEmail(email)

            withContext(Dispatchers.Main) {
                if (user != null) {
                    tvName?.text = user.fullName
                } else {
                    tvName?.text = AppPreferences.getUserName(this@ProfileActivity)
                }
            }
        }

        profileViewModel.observeProfile(userId).observe(this) { profile ->
            if (profile != null) {
                edtHeight.setText(profile.heightCm.toInt().toString())
                edtWeight.setText(profile.currentWeightKg.toString())
                edtAge.setText(profile.age.toString())
                isMale = (profile.gender == "Nam")
                updateGenderUI()

                ActivityLevel.fromKey(profile.activityLevel)?.let {
                    selectedActivityLevel = it
                    tvActivityTitle?.text = it.label
                    tvActivitySub?.text = it.description
                }
            }
        }

        findViewById<View>(R.id.btnSaveBmi)?.setOnClickListener {
            val email = AppPreferences.getUserEmail(this)

            val hText = edtHeight?.text.toString().replace(",", ".").trim()
            val wText = edtWeight?.text.toString().replace(",", ".").trim()
            val aText = edtAge?.text.toString().trim()

            val h = hText.toFloatOrNull()
            val w = wText.toFloatOrNull()
            val a = aText.toIntOrNull()

            if (h == null || h < 50f || h > 250f) {
                Toast.makeText(this, "Chiều cao hợp lệ từ 50cm đến 250cm", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (w == null || w < 20f || w > 300f) {
                Toast.makeText(this, "Cân nặng hợp lệ từ 20kg đến 300kg", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (a == null || a < 18 || a > 120) {
                Toast.makeText(this, "Tuổi hợp lệ từ 18 tuổi trở lên (đến 120 tuổi)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val genderStr = if (isMale) "Nam" else "Nữ"

            AppPreferences.setUserHeight(this, h)
            AppPreferences.setUserWeight(this, w)
            AppPreferences.setUserAge(this, a)
            AppPreferences.setUserGender(this, genderStr)
            AppPreferences.setHasCompletedProfile(this, true)

            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getInstance(this@ProfileActivity)
                val user = db.userDao().getUserByEmail(email)
                val currentUserId = user?.id ?: AppPreferences.getCurrentUserId(this@ProfileActivity)

                if (user != null) {
                    db.userDao().update(user.copy(hasCompletedProfile = true))
                }

                val dbRepo = com.example.fitcheck.data.repository.ProfileRepository(db.bodyProfileDao(), db.userDao())
                val saveResult = dbRepo.save(
                    userId = currentUserId,
                    gender = genderStr,
                    age = a,
                    heightCm = h,
                    weightKg = w,
                    targetKg = w,
                    activityKey = selectedActivityLevel.key,
                    goal = "GIU_CAN"
                )

                withContext(Dispatchers.Main) {
                    when (saveResult) {
                        is ProfileResult.Success -> {
                            // Insert initial weight record if needed
                            lifecycleScope.launch(Dispatchers.IO) {
                                val cal = java.util.Calendar.getInstance()
                                val sdfDate = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                                val sdfTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
                                val initialRecord = WeightRecord(
                                    userId = currentUserId,
                                    weight = w,
                                    date = sdfDate.format(cal.time),
                                    session = "${sdfTime.format(cal.time)} • Khởi tạo"
                                )
                                db.weightDao().insert(initialRecord)
                            }

                            Toast.makeText(this@ProfileActivity, "Đã lưu hồ sơ cơ thể thành công!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ProfileActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is ProfileResult.Error -> {
                            Toast.makeText(this@ProfileActivity, saveResult.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}
