package com.example.fitcheck.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcheck.R
import com.example.fitcheck.data.WeightRecord
import com.example.fitcheck.viewmodel.WeightViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeightChartActivity : AppCompatActivity() {

    private val viewModel: WeightViewModel by viewModels()
    private var selectedDaysLimit = 30 // Default 1 month

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_chart)

        BottomNavHelper.setupBottomNav(this)

        val cardChart = findViewById<MaterialCardView>(R.id.cardChart)
        val layoutChartEmptyState = findViewById<View>(R.id.layoutChartEmptyState)

        findViewById<View>(R.id.tabHistory)?.setOnClickListener {
            val intent = Intent(this, WeightHistoryActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        findViewById<MaterialButton>(R.id.btnRecordFirstWeight)?.setOnClickListener {
            val intent = Intent(this, WeightHistoryActivity::class.java)
            intent.putExtra("OPEN_ADD_DIALOG", true)
            startActivity(intent)
        }

        val tvCurrentWeightValue = findViewById<TextView>(R.id.tvCurrentWeightValue)
        val tvLegendRecordCount = findViewById<TextView>(R.id.tvLegendRecordCount)
        val tvDateRange = findViewById<TextView>(R.id.tvDateRange)
        val tvWeightDiff = findViewById<TextView>(R.id.tvWeightDiff)
        val lineChart = findViewById<WeightLineChartView>(R.id.lineChart)

        val card1Month = findViewById<MaterialCardView>(R.id.card1Month)
        val tv1Month = findViewById<TextView>(R.id.tv1Month)
        val card3Months = findViewById<MaterialCardView>(R.id.card3Months)
        val tv3Months = findViewById<TextView>(R.id.tv3Months)
        val card6Months = findViewById<MaterialCardView>(R.id.card6Months)
        val tv6Months = findViewById<TextView>(R.id.tv6Months)

        fun updateTimeRangeChips(activeCard: MaterialCardView?) {
            val cards = listOf(card1Month, card3Months, card6Months)
            val textViews = listOf(tv1Month, tv3Months, tv6Months)

            for (i in cards.indices) {
                val card = cards[i]
                val tv = textViews[i]
                if (card == activeCard) {
                    card?.setCardBackgroundColor(Color.parseColor("#E8F3EE"))
                    card?.strokeWidth = 0
                    tv?.setTextColor(Color.parseColor("#1B6B45"))
                    tv?.setTypeface(null, android.graphics.Typeface.BOLD)
                } else {
                    card?.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                    card?.strokeWidth = 2
                    card?.strokeColor = Color.parseColor("#E5E7EB")
                    tv?.setTextColor(Color.parseColor("#6B7280"))
                    tv?.setTypeface(null, android.graphics.Typeface.NORMAL)
                }
            }
        }

        fun displayData(records: List<WeightRecord>) {
            val first = records.first()
            val last = records.last()

            tvCurrentWeightValue?.text = "${last.weight} kg"
            tvLegendRecordCount?.text = "Cân nặng (kg) · ${records.size} lần ghi"

            val dateText = if (first.date == last.date) {
                first.date
            } else {
                "${first.date.substringBeforeLast("/")} – ${last.date}"
            }
            tvDateRange?.text = dateText

            val delta = last.weight - first.weight
            val roundedDelta = (delta * 10).roundToInt() / 10f

            val diffText = when {
                roundedDelta > 0 -> "↑ ${Math.abs(roundedDelta)} kg"
                roundedDelta < 0 -> "↓ ${Math.abs(roundedDelta)} kg"
                else -> "0.0 kg (Không đổi)"
            }
            tvWeightDiff?.text = diffText

            lineChart?.setRecords(records)
        }

        fun updateChartData(fullList: List<WeightRecord>) {
            val sortedList = fullList.sortedBy { it.id } // Order chronologically

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val now = System.currentTimeMillis()
            val limitMillis = selectedDaysLimit * 24L * 60L * 60L * 1000L

            val filtered = sortedList.filter { record ->
                try {
                    val date = sdf.parse(record.date)
                    date != null && (now - date.time <= limitMillis || date.time >= now)
                } catch (e: Exception) { true }
            }

            val targetList = if (filtered.size >= 2) filtered else sortedList

            if (targetList.size < 2) {
                cardChart?.visibility = View.GONE
                layoutChartEmptyState?.visibility = View.VISIBLE
            } else {
                cardChart?.visibility = View.VISIBLE
                layoutChartEmptyState?.visibility = View.GONE
                displayData(targetList)
            }
        }

        viewModel.allWeights.observe(this) { list ->
            if (list.size < 2) {
                cardChart?.visibility = View.GONE
                layoutChartEmptyState?.visibility = View.VISIBLE
            } else {
                updateChartData(list)

                card1Month?.setOnClickListener {
                    selectedDaysLimit = 30
                    updateTimeRangeChips(card1Month)
                    updateChartData(list)
                }

                card3Months?.setOnClickListener {
                    selectedDaysLimit = 90
                    updateTimeRangeChips(card3Months)
                    updateChartData(list)
                }

                card6Months?.setOnClickListener {
                    selectedDaysLimit = 180
                    updateTimeRangeChips(card6Months)
                    updateChartData(list)
                }
            }
        }

        val calendarClick = View.OnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year)
                val allList = viewModel.allWeights.value ?: emptyList()
                val filtered = allList.filter { it.date == selectedDate }
                if (filtered.isEmpty()) {
                    Toast.makeText(this, "Không có bản ghi ngày $selectedDate", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Tìm thấy ${filtered.size} bản ghi ngày $selectedDate", Toast.LENGTH_SHORT).show()
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }
        findViewById<View>(R.id.ivCalendar)?.setOnClickListener(calendarClick)
    }
}
