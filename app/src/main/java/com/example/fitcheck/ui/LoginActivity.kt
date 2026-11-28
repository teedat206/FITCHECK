package com.example.fitcheck.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import com.example.fitcheck.data.repository.AuthResult
import com.example.fitcheck.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()

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

        authViewModel.authResult.observe(this) { result ->
            when (result) {
                is AuthResult.Success -> {
                    AppPreferences.setCurrentUserId(this, result.userId)
                    AppPreferences.setLoggedIn(this, true)

                    lifecycleScope.launch(Dispatchers.IO) {
                        val db = AppDatabase.getInstance(this@LoginActivity)
                        val userEntity = db.userDao().findByEmail(etEmail.text.toString().trim().lowercase())
                        val completed = userEntity?.hasCompletedProfile ?: false
                        val name = userEntity?.fullName ?: "User"
                        val email = userEntity?.email ?: ""

                        withContext(Dispatchers.Main) {
                            AppPreferences.setUserName(this@LoginActivity, name)
                            AppPreferences.setUserEmail(this@LoginActivity, email)
                            AppPreferences.setHasCompletedProfile(this@LoginActivity, completed)
                            Toast.makeText(this@LoginActivity, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                            authViewModel.clearResult()

                            val intent = if (completed) {
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
                is AuthResult.Error -> {
                    Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    authViewModel.clearResult()
                }
                null -> {}
            }
        }

        findViewById<View>(R.id.btnLogin)?.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = etPassword.text.toString()
            authViewModel.login(email, pass)
        }

        findViewById<View>(R.id.tvRegisterPrompt)?.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
