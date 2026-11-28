package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcheck.R
import com.example.fitcheck.data.entity.MealEntity
import com.example.fitcheck.viewmodel.MealFilter
import com.example.fitcheck.viewmodel.MealViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.RangeSlider

class SearchActivity : AppCompatActivity() {

    private val mealViewModel: MealViewModel by viewModels()

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

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

        val chipGroups = listOfNotNull(chipGroupGoal, chipGroupTime)

        fun applyFilters() {
            val query = etSearchQuery?.text.toString().trim()

            val selectedGoalId = chipGroupGoal?.checkedChipId ?: View.NO_ID
            val selectedGoal = if (selectedGoalId != View.NO_ID) {
                findViewById<Chip>(selectedGoalId)?.text?.toString() ?: ""
            } else ""

            val selectedTimeId = chipGroupTime?.checkedChipId ?: View.NO_ID
            val selectedTimeText = if (selectedTimeId != View.NO_ID) {
                findViewById<Chip>(selectedTimeId)?.text?.toString()
            } else null

            val maxPrep = when {
                selectedTimeText?.contains("15") == true -> 15
                selectedTimeText?.contains("30") == true -> 30
                else -> null
            }

            val minKcal = sliderKcal?.values?.get(0)?.toInt() ?: 0
            val maxKcal = sliderKcal?.values?.get(1)?.toInt() ?: 5000

            mealViewModel.setFilter(
                MealFilter(
                    keyword = query,
                    goal = selectedGoal,
                    maxPrep = maxPrep,
                    minKcal = minKcal,
                    maxKcal = maxKcal
                )
            )
        }

        sliderKcal?.values = listOf(0f, 1500f)

        sliderKcal?.addOnChangeListener { slider, _, _ ->
            val values = slider.values
            tvMinKcal?.text = "${values[0].toInt()} kcal"
            tvMaxKcal?.text = "${values[1].toInt()} kcal"
            applyFilters()
        }

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
                applyFilters()
            }
        }

        fun renderMeals(meals: List<MealEntity>) {
            layoutResultsContainer?.removeAllViews()

            if (meals.isEmpty()) {
                tvEmptyResult?.visibility = View.VISIBLE
                tvEmptyResult?.text = "Không tìm thấy món ăn phù hợp"
                tvResultCount?.text = "0 kết quả phù hợp"
            } else {
                tvEmptyResult?.visibility = View.GONE
                tvResultCount?.text = "${meals.size} kết quả phù hợp"

                meals.forEach { meal ->
                    val card = MaterialCardView(this).apply {
                        radius = resources.displayMetrics.density * 16f
                        setCardBackgroundColor(Color.WHITE)
                        strokeColor = Color.parseColor("#E5E7EB")
                        strokeWidth = (resources.displayMetrics.density * 1f).toInt()
                        elevation = 0f

                        val lp = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            topMargin = (resources.displayMetrics.density * 10f).toInt()
                        }
                        this.layoutParams = lp

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
                            text = "${meal.calories} kcal · ${meal.prepTimeMinutes} phút · ${meal.goal}"
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
                                .setMessage("Năng lượng: ${meal.calories} kcal\nThời gian chuẩn bị: ${meal.prepTimeMinutes} phút\nMục tiêu: ${meal.goal}\nMô tả: ${meal.description}\nThành phần: ${meal.ingredients}")
                                .setPositiveButton(if (meal.isBookmarked) "Bỏ lưu món" else "Lưu món ăn") { _, _ ->
                                    mealViewModel.toggleBookmark(meal)
                                    Toast.makeText(this@SearchActivity, "Đã cập nhật trạng thái lưu món", Toast.LENGTH_SHORT).show()
                                }
                                .setNegativeButton("Đóng", null)
                                .show()
                        }
                    }
                    layoutResultsContainer?.addView(card)
                }
            }
        }

        mealViewModel.meals.observe(this) { list ->
            renderMeals(list ?: emptyList())
        }

        etSearchQuery?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnClearQuery?.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                searchRunnable?.let { searchHandler.removeCallbacks(it) }
                searchRunnable = Runnable { applyFilters() }
                searchHandler.postDelayed(searchRunnable!!, 300)
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
            sliderKcal?.values = listOf(0f, 1500f)
            etSearchQuery?.text?.clear()
            applyFilters()
        }

        findViewById<MaterialButton>(R.id.btnApplyFilter)?.setOnClickListener {
            applyFilters()
        }

        applyFilters()
    }

    override fun onDestroy() {
        super.onDestroy()
        searchRunnable?.let { searchHandler.removeCallbacks(it) }
    }
}
