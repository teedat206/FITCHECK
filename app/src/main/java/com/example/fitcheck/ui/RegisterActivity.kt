package com.example.fitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fitcheck.R
import com.example.fitcheck.data.repository.AuthResult
import com.example.fitcheck.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnBack)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<View>(R.id.btnBack)?.setOnClickListener { finish() }
        findViewById<View>(R.id.tvLoginPrompt)?.setOnClickListener { finish() }

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmailReg)
        val etPassword = findViewById<EditText>(R.id.etPasswordReg)
        val etConfirm = findViewById<EditText>(R.id.etConfirmPass)
        val cbTerms = findViewById<CheckBox>(R.id.cbTerms)

        authViewModel.authResult.observe(this) { result ->
            when (result) {
                is AuthResult.Success -> {
                    AppPreferences.setCurrentUserId(this, result.userId)
                    AppPreferences.setLoggedIn(this, true)
                    AppPreferences.setHasCompletedProfile(this, false)
                    AppPreferences.setUserName(this, etName.text.toString().trim())
                    AppPreferences.setUserEmail(this, etEmail.text.toString().trim().lowercase())

                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    authViewModel.clearResult()

                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is AuthResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    authViewModel.clearResult()
                }
                null -> {}
            }
        }

        findViewById<View>(R.id.btnRegister)?.setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            if (pass != confirm) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!cbTerms.isChecked) {
                Toast.makeText(this, "Vui lòng đồng ý với Điều khoản", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            authViewModel.register(name, email, pass)
        }
    }
}
