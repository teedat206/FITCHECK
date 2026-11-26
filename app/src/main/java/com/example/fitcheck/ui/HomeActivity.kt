package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcheck.R
import com.example.fitcheck.viewmodel.WeightViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private val viewModel: WeightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        BottomNavHelper.setupBottomNav(this)

        val userName = AppPreferences.getUserName(this)
        findViewById<TextView>(R.id.tvGreeting)?.text = "Chào $userName ☀️"

        val tvDate = findViewById<TextView>(R.id.tvDate)
        tvDate?.text = SimpleDateFormat("EEEE, dd 'tháng' MM", Locale("vi", "VN")).format(Date())

        findViewById<View>(R.id.btnNotification)?.setOnClickListener {
            startActivity(Intent(this, RemindersActivity::class.java))
        }

        val openProfile = View.OnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        findViewById<View>(R.id.cardBmi)?.setOnClickListener(openProfile)
        findViewById<View>(R.id.btnRecalcBmi)?.setOnClickListener(openProfile)

        findViewById<View>(R.id.btnLogWeight)?.setOnClickListener {
            val intent = Intent(this, WeightHistoryActivity::class.java)
            intent.putExtra("OPEN_ADD_DIALOG", true)
            startActivity(intent)
        }

        findViewById<View>(R.id.btnExercise)?.setOnClickListener {
            startActivity(Intent(this, ExerciseActivity::class.java))
        }

        findViewById<View>(R.id.tvSeeSuggestions)?.setOnClickListener {
            startActivity(Intent(this, SuggestActivity::class.java))
        }

        findViewById<View>(R.id.cardMeal)?.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Bún gà rau xanh")
            builder.setMessage("Kcal: 350\nThời gian: 15 phút\nMô tả: Món ăn nhẹ thanh mát, đầy đủ dinh dưỡng.")
            builder.setPositiveButton("Đóng", null)
            builder.show()
        }

        // Bmi and stats update
        val tvBmiValue = findViewById<TextView>(R.id.tvBmiValue)
        val tvBmiStatus = findViewById<TextView>(R.id.tvBmiStatus)
        val tvWeight = findViewById<TextView>(R.id.tvWeight)
        val tvHeight = findViewById<TextView>(R.id.tvHeight)
        val tvAge = findViewById<TextView>(R.id.tvAge)

        val userHeightCm = AppPreferences.getUserHeight(this)
        val userAge = AppPreferences.getUserAge(this)

        tvHeight?.text = "${userHeightCm.toInt()} cm"
        tvAge?.text = "$userAge tuổi"

        viewModel.allWeights.observe(this) { list ->
            val latestWeight = list.firstOrNull()?.weight ?: AppPreferences.getUserWeight(this)
            tvWeight?.text = "$latestWeight kg"

            val heightM = if (userHeightCm > 0) userHeightCm / 100f else 1.6f
            val bmi = latestWeight / (heightM * heightM)
            tvBmiValue?.text = String.format(Locale.US, "%.1f", bmi)

            val status = when {
                bmi < 18.5 -> "Gầy"
                bmi < 24.9 -> "Bình thường"
                bmi < 29.9 -> "Thừa cân"
                else -> "Béo phì"
            }
            tvBmiStatus?.text = status
        }
    }
}
