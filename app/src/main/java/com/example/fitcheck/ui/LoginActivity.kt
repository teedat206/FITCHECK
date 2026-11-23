package com.example.fitcheck.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitcheck.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tạm thời gắn logic đăng nhập đi thẳng vào màn hình chính (WeightHistoryActivity)
        findViewById<android.view.View>(R.id.btnLogin)?.setOnClickListener {
            val intent = android.content.Intent(this, WeightHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }
        
        // Bấm nút đăng ký (nếu có id tvRegisterPrompt) để qua màn hình Register
        findViewById<android.view.View>(R.id.tvRegisterPrompt)?.setOnClickListener {
            val intent = android.content.Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}