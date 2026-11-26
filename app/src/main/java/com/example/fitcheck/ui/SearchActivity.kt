package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
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
import com.google.android.material.slider.RangeSlider
import java.text.Normalizer
import java.util.Locale
import java.util.regex.Pattern

data class MealItem(
    val name: String,
    val kcal: Int,
    val prepMinutes: Int,
    val goal: String
)

class SearchActivity : AppCompatActivity() {

    private val allMeals = listOf(
        MealItem("Cơm gạo lứt, gà & rau củ", 550, 25, "Giữ cân"),
        MealItem("Bún gà rau xanh", 450, 15, "Giảm cân"),
        MealItem("Salad ức gà & trứng luộc", 320, 10, "Giảm cân"),
        MealItem("Yến mạch trái cây & hạt", 380, 5, "Giảm cân"),
        MealItem("Sinh tố bơ chuối đạm cao", 600, 10, "Tăng cân"),
        MealItem("Bò bít tết & khoai lang nướng", 750, 30, "Tăng cân"),
        MealItem("Cơm tấm sườn bì chả", 850, 20, "Tăng cân"),
        MealItem("Cá hồi áp chảo & măng tây", 520, 20, "Giữ cân"),
        MealItem("Súp bí đỏ & bánh mì đen", 280, 15, "Giảm cân")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        findViewById<View>(R.id.btnBack)?.setOnClickListener { finish() }

        findViewById<TextView>(R.id.tvTabWorkout)?.setOnClickListener {
            val intent = Intent(this, SearchWorkoutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val etSearchQuery = findViewById<EditText>(R.id.etSearchQuery)
        val btnClearQuery = findViewById<ImageButton>(R.id.btnClearQuery)
        val sliderKcal = findViewById<RangeSlider>(R.id.sliderKcal)
        val tvMinKcal = findViewById<TextView>(R.id.tvMinKcal)
        val tvMaxKcal = findViewById<TextView>(R.id.tvMaxKcal)
        val chipGroupGoal = findViewById<ChipGroup>(R.id.chipGroupGoal)
        val chipGroupTime = findViewById<ChipGroup>(R.id.chipGroupTime)
        val layoutResultsContainer = findViewById<LinearLayout>(R.id.layoutResultsContainer)
        val tvResultCount = findViewById<TextView>(R.id.tvResultCount)
        val tvEmptyResult = findViewById<TextView>(R.id.tvEmptyResult)

        sliderKcal?.values = listOf(200f, 900f)

        sliderKcal?.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            tvMinKcal?.text = "${values[0].toInt()} kcal"
            tvMaxKcal?.text = "${values[1].toInt()} kcal"
        }

        val chipGroups = listOfNotNull(chipGroupGoal, chipGroupTime)

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

            val selectedGoalId = chipGroupGoal?.checkedChipId ?: View.NO_ID
            val selectedGoal = if (selectedGoalId != View.NO_ID) {
                findViewById<Chip>(selectedGoalId)?.text?.toString()
            } else null

            val selectedTimeId = chipGroupTime?.checkedChipId ?: View.NO_ID
            val selectedTimeText = if (selectedTimeId != View.NO_ID) {
                findViewById<Chip>(selectedTimeId)?.text?.toString()
            } else null

            val minKcal = sliderKcal?.values?.get(0)?.toInt() ?: 0
            val maxKcal = sliderKcal?.values?.get(1)?.toInt() ?: 2000

            val filtered = allMeals.filter { meal ->
                val matchesQuery = query.isEmpty() || meal.name.unaccent().contains(query)
                val matchesGoal = selectedGoal == null || meal.goal.equals(selectedGoal, ignoreCase = true)
                val matchesKcal = meal.kcal in minKcal..maxKcal
                val matchesTime = when {
                    selectedTimeText?.contains("15") == true -> meal.prepMinutes <= 15
                    selectedTimeText?.contains("30") == true -> meal.prepMinutes <= 30
                    else -> true
                }
                matchesQuery && matchesGoal && matchesKcal && matchesTime
            }

            layoutResultsContainer?.removeAllViews()

            if (filtered.isEmpty()) {
                tvEmptyResult?.visibility = View.VISIBLE
                tvResultCount?.text = "0 kết quả phù hợp"
            } else {
                tvEmptyResult?.visibility = View.GONE
                tvResultCount?.text = "${filtered.size} kết quả phù hợp"

                filtered.forEach { meal ->
                    val card = MaterialCardView(this).apply {
                        radius = resources.displayMetrics.density * 16f
                        setCardBackgroundColor(Color.WHITE)
                        strokeColor = Color.parseColor("#E5E7EB")
                        strokeWidth = (resources.displayMetrics.density * 1f).toInt()
                        elevation = 0f

                        val layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            topMargin = (resources.displayMetrics.density * 10f).toInt()
                        }
                        this.layoutParams = layoutParams

                        val contentLayout = LinearLayout(this@SearchActivity).apply {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = android.view.Gravity.CENTER_VERTICAL
                            setPadding(
                                (resources.displayMetrics.density * 16f).toInt(),
                                (resources.displayMetrics.density * 14f).toInt(),
                                (resources.displayMetrics.density * 16f).toInt(),
                                (resources.displayMetrics.density * 14f).toInt()
                            )
                        }

                        val icon = android.widget.ImageView(this@SearchActivity).apply {
                            setImageResource(R.drawable.ic_goiyy)
                            setColorFilter(Color.parseColor("#1B6B45"))
                            this.layoutParams = LinearLayout.LayoutParams(
                                (resources.displayMetrics.density * 28f).toInt(),
                                (resources.displayMetrics.density * 28f).toInt()
                            )
                        }

                        val textLayout = LinearLayout(this@SearchActivity).apply {
                            orientation = LinearLayout.VERTICAL
                            this.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                                marginStart = (resources.displayMetrics.density * 14f).toInt()
                            }
                        }

                        val tvTitle = TextView(this@SearchActivity).apply {
                            text = meal.name
                            setTextColor(Color.parseColor("#111827"))
                            textSize = 16f
                            setTypeface(null, android.graphics.Typeface.BOLD)
                        }

                        val tvSub = TextView(this@SearchActivity).apply {
                            text = "${meal.kcal} kcal · ${meal.prepMinutes} phút · ${meal.goal}"
                            setTextColor(Color.parseColor("#6B7280"))
                            textSize = 13f
                        }

                        textLayout.addView(tvTitle)
                        textLayout.addView(tvSub)

                        val arrow = android.widget.ImageView(this@SearchActivity).apply {
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
                            AlertDialog.Builder(this@SearchActivity)
                                .setTitle(meal.name)
                                .setMessage("Năng lượng: ${meal.kcal} kcal\nThời gian chuẩn bị: ${meal.prepMinutes} phút\nMục tiêu: ${meal.goal}")
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
            sliderKcal?.values = listOf(200f, 900f)
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
