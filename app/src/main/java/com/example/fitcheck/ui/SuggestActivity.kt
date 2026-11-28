package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcheck.R
import com.example.fitcheck.data.entity.MealEntity
import com.example.fitcheck.viewmodel.MealViewModel

class SuggestActivity : AppCompatActivity() {

    private val mealViewModel: MealViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggest)
        
        BottomNavHelper.setupBottomNav(this)

        findViewById<View>(R.id.btnSearch)?.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        
        findViewById<View>(R.id.chipExercise)?.setOnClickListener {
            startActivity(Intent(this, SearchWorkoutActivity::class.java))
        }

        val btnBookmark = findViewById<ImageView>(R.id.btnBookmark)
        var isBookmarked = false
        btnBookmark?.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                btnBookmark.setColorFilter(Color.parseColor("#F59E0B"))
                Toast.makeText(this, "Đã lưu vào danh sách", Toast.LENGTH_SHORT).show()
            } else {
                btnBookmark.setColorFilter(Color.parseColor("#FFFFFF"))
                Toast.makeText(this, "Đã bỏ lưu", Toast.LENGTH_SHORT).show()
            }
        }

        val cardLose = findViewById<androidx.cardview.widget.CardView>(R.id.cardLose)
        val tabLose = findViewById<TextView>(R.id.tabLose)
        
        val cardKeep = findViewById<androidx.cardview.widget.CardView>(R.id.cardKeep)
        val tabKeep = findViewById<TextView>(R.id.tabKeep)
        
        val cardGain = findViewById<androidx.cardview.widget.CardView>(R.id.cardGain)
        val tabGain = findViewById<TextView>(R.id.tabGain)

        val tvMealName = findViewById<TextView>(R.id.tvMealName)
        val tvMealInfo = findViewById<TextView>(R.id.tvMealInfo)
        val tvMealType = findViewById<TextView>(R.id.tvMealType)
        val tvSuggestSub = findViewById<TextView>(R.id.tvSuggestSub)

        val rowMorning = findViewById<View>(R.id.rowMorning)
        val rowEvening = findViewById<View>(R.id.rowEvening)
        val rowSnack = findViewById<View>(R.id.rowSnack)

        fun showMealDialog(meal: MealEntity) {
            AlertDialog.Builder(this)
                .setTitle(meal.name)
                .setMessage("Loại bữa: ${meal.mealType}\nNăng lượng: ${meal.calories} kcal\nThời gian: ${meal.prepTimeMinutes} phút\nMô tả: ${meal.description}\nThành phần: ${meal.ingredients}")
                .setPositiveButton("Đóng", null)
                .show()
        }

        fun updateRowMeal(row: View?, meal: MealEntity) {
            if (row == null) return
            val layout = (row as? ViewGroup)?.getChildAt(1) as? ViewGroup
            val tvTitle = layout?.getChildAt(0) as? TextView
            val tvSub = layout?.getChildAt(1) as? TextView

            tvTitle?.text = "${meal.mealType} · ${meal.name}"
            tvSub?.text = "${meal.calories} kcal · ${meal.prepTimeMinutes} phút"
            row.setOnClickListener { showMealDialog(meal) }
        }

        fun updateMealUI(meals: List<MealEntity>) {
            if (meals.isEmpty()) return

            val mainMeal = meals.firstOrNull() ?: return
            tvMealName?.text = mainMeal.name
            tvMealInfo?.text = "${mainMeal.calories} kcal · Chuẩn bị ${mainMeal.prepTimeMinutes} phút"
            tvMealType?.text = "BỮA ${mainMeal.mealType.uppercase()}"
            findViewById<View>(R.id.cardMainMeal)?.setOnClickListener { showMealDialog(mainMeal) }

            updateRowMeal(rowMorning, meals.getOrNull(1) ?: mainMeal)
            updateRowMeal(rowEvening, meals.getOrNull(2) ?: mainMeal)
            updateRowMeal(rowSnack, meals.getOrNull(3) ?: mainMeal)
        }

        fun observeForGoal(goal: String) {
            val baseKcal = when (goal) {
                "Giảm cân" -> "~1.400 kcal/ngày (Thâm hụt calo)"
                "Tăng cân" -> "~2.200 kcal/ngày (Thặng dư calo)"
                else -> "~1.760 kcal/ngày (Duy trì năng lượng)"
            }
            tvSuggestSub?.text = "$baseKcal · Theo chế độ $goal của bạn"

            mealViewModel.byGoal(goal).observe(this) { meals ->
                updateMealUI(meals ?: emptyList())
            }
        }

        fun updateTabs(activeCard: androidx.cardview.widget.CardView?, goal: String) {
            val cards = listOf(cardLose, cardKeep, cardGain)
            val textViews = listOf(tabLose, tabKeep, tabGain)
            
            for (i in cards.indices) {
                val card = cards[i]
                val tv = textViews[i]
                
                if (card == activeCard) {
                    card?.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                    tv?.setTextColor(Color.parseColor("#1B6B45"))
                    tv?.setTypeface(null, android.graphics.Typeface.BOLD)
                } else {
                    card?.setCardBackgroundColor(Color.TRANSPARENT)
                    tv?.setTextColor(Color.parseColor("#6B7280"))
                    tv?.setTypeface(null, android.graphics.Typeface.NORMAL)
                }
            }
            observeForGoal(goal)
        }

        cardLose?.setOnClickListener { updateTabs(cardLose, "Giảm cân") }
        cardKeep?.setOnClickListener { updateTabs(cardKeep, "Giữ cân") }
        cardGain?.setOnClickListener { updateTabs(cardGain, "Tăng cân") }

        // Default to "Giữ cân" or user's active goal
        updateTabs(cardKeep, "Giữ cân")
    }
}
