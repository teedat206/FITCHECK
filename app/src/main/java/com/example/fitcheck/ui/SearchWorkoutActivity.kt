package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcheck.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

data class WorkoutItem(
    val name: String,
    val durationMinutes: Int,
    val intensity: String, // "Vừa", "Nhẹ", "Cao"
    val equipment: String  // "Không dụng cụ", "Có dụng cụ"
)

class SearchWorkoutActivity : AppCompatActivity() {

    private val allWorkouts = listOf(
        WorkoutItem("Giãn cơ tại bàn học", 5, "Nhẹ", "Không dụng cụ"),
        WorkoutItem("Giãn cơ toàn thân", 10, "Nhẹ", "Không dụng cụ"),
        WorkoutItem("Chạy bộ tại chỗ", 15, "Vừa", "Không dụng cụ"),
        WorkoutItem("Nhảy dây gia tăng thể lực", 20, "Cao", "Có dụng cụ"),
        WorkoutItem("Tập tạ tay săn chắc vai", 25, "Vừa", "Có dụng cụ"),
        WorkoutItem("Cardio đốt mỡ nhanh", 30, "Cao", "Không dụng cụ"),
        WorkoutItem("Plank & Cơ bụng 6 múi", 15, "Vừa", "Không dụng cụ"),
        WorkoutItem("Squat & Đùi thon gọn", 20, "Vừa", "Không dụng cụ"),
        WorkoutItem("Yoga thư giãn buổi tối", 30, "Nhẹ", "Có dụng cụ")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_workout)

        findViewById<View>(R.id.btnBack)?.setOnClickListener { finish() }

        findViewById<TextView>(R.id.tvTabMeals)?.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val etSearchQuery = findViewById<EditText>(R.id.etSearchQuery)
        val btnClearQuery = findViewById<ImageButton>(R.id.btnClearQuery)
        val chipGroupIntensity = findViewById<ChipGroup>(R.id.chipGroupIntensity)
        val chipGroupDuration = findViewById<ChipGroup>(R.id.chipGroupDuration)
        val chipGroupEquipment = findViewById<ChipGroup>(R.id.chipGroupEquipment)
        val layoutResultsContainer = findViewById<LinearLayout>(R.id.layoutResultsContainer)
        val tvResultCount = findViewById<TextView>(R.id.tvResultCount)
        val tvEmptyResult = findViewById<TextView>(R.id.tvEmptyResult)

        val chipGroups = listOfNotNull(chipGroupIntensity, chipGroupDuration, chipGroupEquipment)

        fun syncChipGroupStyle(chipGroup: ChipGroup) {
            for (i in 0 until chipGroup.childCount) {
                val chip = chipGroup.getChildAt(i) as? Chip ?: continue
                chip.isCheckedIconVisible = false
                if (chip.isChecked) {
                    chip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#E3F1E8"))
                    chip.setTextColor(Color.parseColor("#1B6B45"))
                    chip.chipStrokeWidth = 0f
                } else {
                    chip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
                    chip.setTextColor(Color.parseColor("#6B7280"))
                    chip.chipStrokeColor = ColorStateList.valueOf(Color.parseColor("#E5E7EB"))
                    chip.chipStrokeWidth = resources.displayMetrics.density * 1f
                }
            }
        }

        chipGroups.forEach { group ->
            syncChipGroupStyle(group)
            group.setOnCheckedStateChangeListener { _, _ ->
                syncChipGroupStyle(group)
            }
        }

        fun String.unaccent(): String {
            val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
            return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(temp).replaceAll("")
                .lowercase(Locale.getDefault())
        }

        fun applyFilters() {
            val query = etSearchQuery?.text.toString().trim().unaccent()

            val selectedIntensityId = chipGroupIntensity?.checkedChipId ?: View.NO_ID
            val selectedIntensity = if (selectedIntensityId != View.NO_ID) {
                findViewById<Chip>(selectedIntensityId)?.text?.toString()
            } else null

            val selectedDurationId = chipGroupDuration?.checkedChipId ?: View.NO_ID
            val selectedDurationText = if (selectedDurationId != View.NO_ID) {
                findViewById<Chip>(selectedDurationId)?.text?.toString()
            } else null

            val selectedEquipmentId = chipGroupEquipment?.checkedChipId ?: View.NO_ID
            val selectedEquipment = if (selectedEquipmentId != View.NO_ID) {
                findViewById<Chip>(selectedEquipmentId)?.text?.toString()
            } else null

            val filtered = allWorkouts.filter { workout ->
                val matchesQuery = query.isEmpty() || workout.name.unaccent().contains(query)
                val matchesIntensity = selectedIntensity == null || workout.intensity.equals(selectedIntensity, ignoreCase = true)
                val matchesEquipment = selectedEquipment == null || workout.equipment.equals(selectedEquipment, ignoreCase = true)
                val matchesDuration = when {
                    selectedDurationText?.contains("15") == true -> workout.durationMinutes <= 15
                    selectedDurationText?.contains("30") == true -> workout.durationMinutes <= 30
                    else -> true
                }
                matchesQuery && matchesIntensity && matchesEquipment && matchesDuration
            }

            layoutResultsContainer?.removeAllViews()

            if (filtered.isEmpty()) {
                tvEmptyResult?.visibility = View.VISIBLE
                tvResultCount?.text = "0 kết quả phù hợp"
            } else {
                tvEmptyResult?.visibility = View.GONE
                tvResultCount?.text = "${filtered.size} kết quả phù hợp"

                filtered.forEach { workout ->
                    val card = MaterialCardView(this).apply {
                        radius = resources.displayMetrics.density * 16f
                        setCardBackgroundColor(Color.WHITE)
                        strokeColor = Color.parseColor("#E5E5EB")
                        strokeWidth = (resources.displayMetrics.density * 1f).toInt()
                        elevation = 0f

                        val lp = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            topMargin = (resources.displayMetrics.density * 10f).toInt()
                        }
                        this.layoutParams = lp

                        val contentLayout = LinearLayout(this@SearchWorkoutActivity).apply {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = android.view.Gravity.CENTER_VERTICAL
                            setPadding(
                                (resources.displayMetrics.density * 16f).toInt(),
                                (resources.displayMetrics.density * 14f).toInt(),
                                (resources.displayMetrics.density * 16f).toInt(),
                                (resources.displayMetrics.density * 14f).toInt()
                            )
                        }

                        val icon = android.widget.ImageView(this@SearchWorkoutActivity).apply {
                            setImageResource(R.drawable.ic_stretch)
                            this.layoutParams = LinearLayout.LayoutParams(
                                (resources.displayMetrics.density * 48f).toInt(),
                                (resources.displayMetrics.density * 48f).toInt()
                            )
                        }

                        val textLayout = LinearLayout(this@SearchWorkoutActivity).apply {
                            orientation = LinearLayout.VERTICAL
                            this.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                                marginStart = (resources.displayMetrics.density * 14f).toInt()
                            }
                        }

                        val tvTitle = TextView(this@SearchWorkoutActivity).apply {
                            text = workout.name
                            setTextColor(Color.parseColor("#111827"))
                            textSize = 16f
                            setTypeface(null, android.graphics.Typeface.BOLD)
                        }

                        val tvSub = TextView(this@SearchWorkoutActivity).apply {
                            text = "${workout.durationMinutes} phút · ${workout.intensity} · ${workout.equipment}"
                            setTextColor(Color.parseColor("#6B7280"))
                            textSize = 13f
                        }

                        textLayout.addView(tvTitle)
                        textLayout.addView(tvSub)

                        val arrow = android.widget.ImageView(this@SearchWorkoutActivity).apply {
                            setImageResource(R.drawable.ic_chevron_right)
                            setColorFilter(Color.parseColor("#6B7280"))
                            this.layoutParams = LinearLayout.LayoutParams(
                                (resources.displayMetrics.density * 18f).toInt(),
                                (resources.displayMetrics.density * 18f).toInt()
                            )
                        }

                        contentLayout.addView(icon)
                        contentLayout.addView(textLayout)
                        contentLayout.addView(arrow)

                        addView(contentLayout)

                        setOnClickListener {
                            AlertDialog.Builder(this@SearchWorkoutActivity)
                                .setTitle(workout.name)
                                .setMessage("Thời lượng: ${workout.durationMinutes} phút\nCường độ: ${workout.intensity}\nDụng cụ: ${workout.equipment}")
                                .setPositiveButton("Đóng", null)
                                .show()
                        }
                    }
                    layoutResultsContainer?.addView(card)
                }
            }
        }

        etSearchQuery?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnClearQuery?.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnClearQuery?.setOnClickListener {
            etSearchQuery?.text?.clear()
            applyFilters()
        }

        findViewById<TextView>(R.id.tvResetFilter)?.setOnClickListener {
            chipGroups.forEach {
                it.clearCheck()
                syncChipGroupStyle(it)
            }
            etSearchQuery?.text?.clear()
            applyFilters()
        }

        findViewById<MaterialButton>(R.id.btnApplyFilter)?.setOnClickListener {
            applyFilters()
        }

        // Initial apply
        applyFilters()
    }
}
