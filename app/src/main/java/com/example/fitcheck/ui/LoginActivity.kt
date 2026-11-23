package com.example.fitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        findViewById<View>(R.id.btnLogin)?.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@LoginActivity)
                val user = db.userDao().getUserByEmail(email)

                withContext(Dispatchers.Main) {
                    if (user == null) {
                        Toast.makeText(this@LoginActivity, "Tài khoản chưa tồn tại, vui lòng đăng ký", Toast.LENGTH_SHORT).show()
                        return@withContext
                    }

                    if (user.password != pass) {
                        Toast.makeText(this@LoginActivity, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show()
                        return@withContext
                    }

                    AppPreferences.setLoggedIn(this@LoginActivity, true)
                    AppPreferences.setUserName(this@LoginActivity, user.fullName)
                    AppPreferences.setUserEmail(this@LoginActivity, user.email)
                    AppPreferences.setHasCompletedProfile(this@LoginActivity, user.hasCompletedProfile)

                    val intent = if (user.hasCompletedProfile) {
                        Intent(this@LoginActivity, HomeActivity::class.java)
                    } else {
                        Intent(this@LoginActivity, ProfileActivity::class.java)
                    }
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
        
        findViewById<View>(R.id.tvRegisterPrompt)?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}