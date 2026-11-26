package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcheck.R

class ExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // 1. Kích hoạt Bottom Navigation
        BottomNavHelper.setupBottomNav(this)

        // 2. Nút Quay lại
        findViewById<View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        // 3. Nút Tìm kiếm vận động
        findViewById<View>(R.id.btnSearch)?.setOnClickListener {
            startActivity(Intent(this, SearchWorkoutActivity::class.java))
        }

        // 4. Chip chuyển sang Bữa ăn (SuggestActivity)
        findViewById<View>(R.id.chipMeal)?.setOnClickListener {
            val intent = Intent(this, SuggestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        // 5. Hàm hiển thị dialog chi tiết bài tập
        fun showExerciseDetail(title: String, duration: String, level: String, desc: String) {
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Thời lượng: $duration\nCường độ: $level\nHướng dẫn: $desc")
                .setPositiveButton("Bắt đầu tập") { _, _ ->
                    Toast.makeText(this, "Đã bắt đầu bài tập $title!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Đóng", null)
                .show()
        }

        // 6. Gắn sự kiện cho các thẻ bài tập
        val mainExerciseClick = View.OnClickListener {
            showExerciseDetail(
                "Chạy bộ ngoài trời",
                "30 phút",
                "Vừa",
                "Chạy tốc độ vừa phải, giữ nhịp thở đều đặn và thả lỏng vai."
            )
        }
        findViewById<View>(R.id.cardMainExercise)?.setOnClickListener(mainExerciseClick)
        findViewById<View>(R.id.tvViewGuide)?.setOnClickListener(mainExerciseClick)

        findViewById<View>(R.id.rowStretch)?.setOnClickListener {
            showExerciseDetail(
                "Giãn cơ toàn thân",
                "10 phút",
                "Nhẹ",
                "Thực hiện các động tác giãn cơ cổ, tay, lưng và chân để thả lỏng cơ bắp."
            )
        }

        findViewById<View>(R.id.rowBike)?.setOnClickListener {
            showExerciseDetail(
                "Đạp xe thể thao",
                "45 phút",
                "Cao",
                "Đạp xe duy trì vận tốc 15-20km/h, tập trung lực đùi và mông."
            )
        }

        findViewById<View>(R.id.rowStrength)?.setOnClickListener {
            showExerciseDetail(
                "Tập tạ tay săn chắc",
                "20 phút",
                "Vừa",
                "Sử dụng tạ nhẹ 2-4kg, thực hiện 3 hiệp, mỗi hiệp 12 lần nâng."
            )
        }
    }
}
