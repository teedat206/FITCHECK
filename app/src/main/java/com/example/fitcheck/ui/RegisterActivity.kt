package com.example.fitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.UserRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnBack)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        findViewById<View>(R.id.tvLoginPrompt)?.setOnClickListener {
            finish()
        }

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmailReg)
        val etPassword = findViewById<EditText>(R.id.etPasswordReg)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPass)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

        findViewById<View>(R.id.btnRegister)?.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString().trim()
            val confirm = etConfirm.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 6) {
                Toast.makeText(this, "Mật khẩu phải từ 6 ký tự", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Vui lòng đồng ý với Điều khoản", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getDatabase(this@RegisterActivity)
                val existing = db.userDao().getUserByEmail(email)

                if (existing != null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Tài khoản (email) đã tồn tại", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                // Save new user to Room
                val newUser = UserRecord(
                    fullName = name,
                    email = email,
                    password = pass,
                    hasCompletedProfile = false
                )
                db.userDao().insert(newUser)

                withContext(Dispatchers.Main) {
                    // Set session in SharedPreferences
                    AppPreferences.setLoggedIn(this@RegisterActivity, true)
                    AppPreferences.setUserName(this@RegisterActivity, name)
                    AppPreferences.setUserEmail(this@RegisterActivity, email)
                    AppPreferences.setHasCompletedProfile(this@RegisterActivity, false)

                    Toast.makeText(this@RegisterActivity, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@RegisterActivity, ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}