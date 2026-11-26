package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitcheck.R

class SuggestActivity : AppCompatActivity() {
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

        val showDetails = View.OnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Chi tiết món ăn")
            builder.setMessage("Món ăn rất tốt cho sức khoẻ, chứa đầy đủ chất xơ và protein.")
            builder.setPositiveButton("Đóng", null)
            builder.show()
        }

        findViewById<View>(R.id.cardMainMeal)?.setOnClickListener(showDetails)
        findViewById<View>(R.id.rowMorning)?.setOnClickListener(showDetails)
        findViewById<View>(R.id.rowEvening)?.setOnClickListener(showDetails)
        findViewById<View>(R.id.rowSnack)?.setOnClickListener(showDetails)

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

        findViewById<View>(R.id.tvChangeMenu)?.setOnClickListener {
            Toast.makeText(this, "Đã đổi thực đơn gợi ý", Toast.LENGTH_SHORT).show()
        }

        // Logic switch tabs for goals (Giảm cân / Giữ cân / Tăng cân)
        val cardLose = findViewById<androidx.cardview.widget.CardView>(R.id.cardLose)
        val tabLose = findViewById<TextView>(R.id.tabLose)
        
        val cardKeep = findViewById<androidx.cardview.widget.CardView>(R.id.cardKeep)
        val tabKeep = findViewById<TextView>(R.id.tabKeep)
        
        val cardGain = findViewById<androidx.cardview.widget.CardView>(R.id.cardGain)
        val tabGain = findViewById<TextView>(R.id.tabGain)

        fun updateTabs(activeCard: androidx.cardview.widget.CardView?) {
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
        }

        cardLose?.setOnClickListener { updateTabs(cardLose) }
        cardKeep?.setOnClickListener { updateTabs(cardKeep) }
        cardGain?.setOnClickListener { updateTabs(cardGain) }
    }
}