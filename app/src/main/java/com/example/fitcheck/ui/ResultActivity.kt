package com.example.fitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.BodyCalculator
import com.example.fitcheck.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        findViewById<View>(R.id.ivBack)?.setOnClickListener { finish() }

        val tvUserInfo = findViewById<TextView>(R.id.tvUserInfo)
        val tvBmiValue = findViewById<TextView>(R.id.tvBmiValue)
        val tvBmiStatus = findViewById<TextView>(R.id.tvBmiStatus)
        val tvBmrValue = findViewById<TextView>(R.id.tvBmrValue)
        val tvTdeeValue = findViewById<TextView>(R.id.tvTdeeValue)

        val userId = AppPreferences.getCurrentUserId(this)

        if (userId == -1L) {
            Toast.makeText(this, "Chưa đăng nhập, vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        profileViewModel.observeProfile(userId).observe(this) { profile ->
            if (profile == null) {
                tvUserInfo?.text = "Chưa có hồ sơ cơ thể"
                tvBmiValue?.text = "--.-"
                tvBmiStatus?.text = "Chưa có dữ liệu"
                tvBmrValue?.text = "----"
                tvTdeeValue?.text = "----"
                Toast.makeText(this, "Chưa có hồ sơ cơ thể, vui lòng cập nhật", Toast.LENGTH_SHORT).show()
                return@observe
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getInstance(this@ResultActivity)
                val user = db.userDao().observeById(userId).value ?: run {
                    val email = AppPreferences.getUserEmail(this@ResultActivity)
                    db.userDao().findByEmail(email)
                }

                withContext(Dispatchers.Main) {
                    val fullName = user?.fullName ?: AppPreferences.getUserName(this@ResultActivity)
                    val metrics = BodyCalculator.computeProfileMetrics(profile)

                    tvUserInfo?.text = "$fullName · ${profile.gender}, ${profile.age} tuổi · ${profile.heightCm.toInt()} cm · ${String.format(Locale.US, "%.1f", profile.currentWeightKg)} kg"
                    tvBmiValue?.text = String.format(Locale.US, "%.1f", metrics.bmi)
                    tvBmiStatus?.text = metrics.bmiCategory
                    tvBmrValue?.text = String.format(Locale.US, "%,d", metrics.bmr).replace(',', '.')
                    tvTdeeValue?.text = String.format(Locale.US, "%,d", metrics.tdee).replace(',', '.')
                }
            }
        }

        findViewById<View>(R.id.btnViewMeals)?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
