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
import com.example.fitcheck.data.entity.WorkoutEntity
import com.example.fitcheck.viewmodel.WorkoutFilter
import com.example.fitcheck.viewmodel.WorkoutViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SearchWorkoutActivity : AppCompatActivity() {

    private val workoutViewModel: WorkoutViewModel by viewModels()

    private val searchHandler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

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

        fun applyFilters() {
            val query = etSearchQuery?.text.toString().trim()

            val selectedIntensityId = chipGroupIntensity?.checkedChipId ?: View.NO_ID
            val selectedIntensity = if (selectedIntensityId != View.NO_ID) {
                findViewById<Chip>(selectedIntensityId)?.text?.toString() ?: ""
            } else ""

            val selectedDurationId = chipGroupDuration?.checkedChipId ?: View.NO_ID
            val selectedDurationText = if (selectedDurationId != View.NO_ID) {
                findViewById<Chip>(selectedDurationId)?.text?.toString()
            } else null

            val maxDuration = when {
                selectedDurationText?.contains("15") == true -> 15
                selectedDurationText?.contains("30") == true -> 30
                else -> null
            }

            val selectedEquipmentId = chipGroupEquipment?.checkedChipId ?: View.NO_ID
            val selectedEquipmentText = if (selectedEquipmentId != View.NO_ID) {
                findViewById<Chip>(selectedEquipmentId)?.text?.toString()
            } else null

            val hasEquipment = when {
                selectedEquipmentText?.contains("Có") == true -> true
                selectedEquipmentText?.contains("Không") == true -> false
                else -> null
            }

            workoutViewModel.setFilter(
                WorkoutFilter(
                    keyword = query,
                    intensity = selectedIntensity,
                    maxDuration = maxDuration,
                    hasEquipment = hasEquipment
                )
            )
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

        fun renderWorkouts(workouts: List<WorkoutEntity>) {
            layoutResultsContainer?.removeAllViews()

            if (workouts.isEmpty()) {
                tvEmptyResult?.visibility = View.VISIBLE
                tvEmptyResult?.text = "Không tìm thấy bài tập phù hợp"
                tvResultCount?.text = "0 kết quả phù hợp"
            } else {
                tvEmptyResult?.visibility = View.GONE
                tvResultCount?.text = "${workouts.size} kết quả phù hợp"

                workouts.forEach { workout ->
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

                        val equipStr = if (workout.hasEquipment) "Có dụng cụ" else "Không dụng cụ"
                        val tvSub = TextView(this@SearchWorkoutActivity).apply {
                            text = "${workout.durationMinutes} phút · ${workout.intensity} · $equipStr"
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
                                .setMessage("Thời lượng: ${workout.durationMinutes} phút\nCường độ: ${workout.intensity}\nDụng cụ: $equipStr\nCalo tiêu hao: ${workout.caloriesBurned} kcal\nMô tả: ${workout.description}\nHướng dẫn: ${workout.steps}")
                                .setPositiveButton(if (workout.isCompleted) "Bỏ hoàn thành" else "Đánh dấu hoàn thành") { _, _ ->
                                    workoutViewModel.toggleCompleted(workout)
                                    Toast.makeText(this@SearchWorkoutActivity, "Đã cập nhật trạng thái bài tập", Toast.LENGTH_SHORT).show()
                                }
                                .setNegativeButton("Đóng", null)
                                .show()
                        }
                    }
                    layoutResultsContainer?.addView(card)
                }
            }
        }

        workoutViewModel.workouts.observe(this) { list ->
            renderWorkouts(list ?: emptyList())
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
