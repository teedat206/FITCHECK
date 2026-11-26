package com.example.fitcheck.ui

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.example.fitcheck.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.util.Calendar
import java.util.Locale

class RemindersActivity : AppCompatActivity() {

    private var weightTime = "07:00"
    private var mealTime = "11:30"
    private var moveTime = "17:30"

    private val selectedDays = mutableSetOf(1, 2, 3, 4, 5) // Mon-Fri default (1: Mon, 7: Sun)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        findViewById<View>(R.id.btnBack)?.setOnClickListener { finish() }

        val switchWeight = findViewById<SwitchCompat>(R.id.switchWeightReminder)
        val tvSelectedTime = findViewById<TextView>(R.id.tvSelectedTime)
        val btnPickTime = findViewById<View>(R.id.btnPickTime)
        val tvNextOccurrence = findViewById<TextView>(R.id.tvNextOccurrence)

        val switchMeal = findViewById<SwitchCompat>(R.id.switchMealReminder)
        val tvMealSubtitle = findViewById<TextView>(R.id.tvMealSubtitle)

        val switchMove = findViewById<SwitchCompat>(R.id.switchMoveReminder)
        val tvMoveSubtitle = findViewById<TextView>(R.id.tvMoveSubtitle)

        val dayCards = listOf(
            findViewById<MaterialCardView>(R.id.cardDayMon) to findViewById<TextView>(R.id.tvDayMon),
            findViewById<MaterialCardView>(R.id.cardDayTue) to findViewById<TextView>(R.id.tvDayTue),
            findViewById<MaterialCardView>(R.id.cardDayWed) to findViewById<TextView>(R.id.tvDayWed),
            findViewById<MaterialCardView>(R.id.cardDayThu) to findViewById<TextView>(R.id.tvDayThu),
            findViewById<MaterialCardView>(R.id.cardDayFri) to findViewById<TextView>(R.id.tvDayFri),
            findViewById<MaterialCardView>(R.id.cardDaySat) to findViewById<TextView>(R.id.tvDaySat),
            findViewById<MaterialCardView>(R.id.cardDaySun) to findViewById<TextView>(R.id.tvDaySun)
        )

        fun updateDaysUI() {
            dayCards.forEachIndexed { index, (card, tv) ->
                val dayIndex = index + 1
                if (selectedDays.contains(dayIndex)) {
                    card?.setCardBackgroundColor(Color.parseColor("#1B6B45"))
                    tv?.setTextColor(Color.WHITE)
                } else {
                    card?.setCardBackgroundColor(Color.parseColor("#EEF2EF"))
                    tv?.setTextColor(Color.parseColor("#6B7280"))
                }
            }

            val daysNames = listOf("T2", "T3", "T4", "T5", "T6", "T7", "CN")
            val activeNames = selectedDays.sorted().map { daysNames[it - 1] }
            val nextDaysText = if (activeNames.isEmpty()) "Chưa chọn ngày" else activeNames.joinToString(", ")
            tvNextOccurrence?.text = "Lần tới: $nextDaysText lúc $weightTime"
        }

        dayCards.forEachIndexed { index, (card, _) ->
            val dayIndex = index + 1
            card?.setOnClickListener {
                if (selectedDays.contains(dayIndex)) {
                    if (selectedDays.size > 1) {
                        selectedDays.remove(dayIndex)
                    }
                } else {
                    selectedDays.add(dayIndex)
                }
                updateDaysUI()
            }
        }

        updateDaysUI()

        val pickWeightTimeListener = View.OnClickListener {
            val parts = weightTime.split(":")
            val hour = parts.getOrNull(0)?.toIntOrNull() ?: 7
            val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                weightTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                tvSelectedTime?.text = weightTime
                updateDaysUI()
            }, hour, minute, true).show()
        }

        btnPickTime?.setOnClickListener(pickWeightTimeListener)
        tvSelectedTime?.setOnClickListener(pickWeightTimeListener)

        tvMealSubtitle?.setOnClickListener {
            val parts = mealTime.split(":")
            val hour = parts.getOrNull(0)?.toIntOrNull() ?: 11
            val minute = parts.getOrNull(1)?.toIntOrNull() ?: 30

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                mealTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                tvMealSubtitle.text = "Hàng ngày lúc $mealTime"
            }, hour, minute, true).show()
        }

        tvMoveSubtitle?.setOnClickListener {
            val parts = moveTime.split(":")
            val hour = parts.getOrNull(0)?.toIntOrNull() ?: 17
            val minute = parts.getOrNull(1)?.toIntOrNull() ?: 30

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                moveTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                tvMoveSubtitle.text = "Hàng ngày lúc $moveTime"
            }, hour, minute, true).show()
        }

        findViewById<MaterialButton>(R.id.btnSaveReminders)?.setOnClickListener {
            Toast.makeText(this, "Đã lưu nhắc nhở thành công", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
