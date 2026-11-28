package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var tvAvatarInitials: TextView
    private lateinit var btnSaveChanges: MaterialButton

    private var originalName = ""
    private var originalEmail = ""
    private var originalPhone = ""

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Toast.makeText(this, "Đã chọn ảnh mới", Toast.LENGTH_SHORT).show()
            enableSaveButton()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        initViews()
        loadData()
        setupListeners()
    }

    private fun initViews() {
        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        tvAvatarInitials = findViewById(R.id.tvAvatarInitials)
        btnSaveChanges = findViewById(R.id.btnSaveChanges)

        disableSaveButton()
    }

    private fun loadData() {
        val userId = AppPreferences.getCurrentUserId(this)
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(this@PersonalInfoActivity)
            val email = AppPreferences.getUserEmail(this@PersonalInfoActivity)
            val user = db.userDao().findByEmail(email)

            withContext(Dispatchers.Main) {
                originalName = user?.fullName ?: AppPreferences.getUserName(this@PersonalInfoActivity)
                originalEmail = user?.email ?: email
                originalPhone = user?.phone ?: AppPreferences.getUserPhone(this@PersonalInfoActivity)

                etFullName.setText(originalName)
                etEmail.setText(originalEmail)
                etPhone.setText(originalPhone)

                updateAvatarInitials(originalName)
            }
        }
    }

    private fun updateAvatarInitials(name: String) {
        val words = name.trim().split("\\s+".toRegex())
        var initials = ""
        if (words.isNotEmpty()) {
            if (words.size == 1) {
                initials = words[0].take(2).uppercase()
            } else {
                initials = (words[words.size - 2].take(1) + words[words.size - 1].take(1)).uppercase()
            }
        }
        tvAvatarInitials.text = initials
    }

    private fun setupListeners() {
        findViewById<android.view.View>(R.id.btnBack)?.setOnClickListener {
            handleBackPress()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkChanges()
            }
        }

        etFullName.addTextChangedListener(textWatcher)
        etEmail.addTextChangedListener(textWatcher)
        etPhone.addTextChangedListener(textWatcher)

        findViewById<CardView>(R.id.cardAvatar).setOnClickListener {
            pickMedia.launch(androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        findViewById<TextView>(R.id.tvChangeAvatar).setOnClickListener {
            pickMedia.launch(androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnSaveChanges.setOnClickListener {
            val newName = etFullName.text.toString().trim()
            val newEmail = etEmail.text.toString().trim()
            val newPhone = etPhone.text.toString().trim()

            if (newName.isEmpty()) {
                Toast.makeText(this, "Họ tên không được để trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = AppPreferences.getCurrentUserId(this)
            lifecycleScope.launch(Dispatchers.IO) {
                val db = AppDatabase.getInstance(this@PersonalInfoActivity)
                val user = db.userDao().findByEmail(originalEmail)
                if (user != null) {
                    db.userDao().updateInfo(user.id, newName, newPhone, null)
                }

                withContext(Dispatchers.Main) {
                    AppPreferences.setUserName(this@PersonalInfoActivity, newName)
                    AppPreferences.setUserEmail(this@PersonalInfoActivity, newEmail)
                    AppPreferences.setUserPhone(this@PersonalInfoActivity, newPhone)

                    originalName = newName
                    originalEmail = newEmail
                    originalPhone = newPhone

                    updateAvatarInitials(newName)

                    Toast.makeText(this@PersonalInfoActivity, "Đã lưu thay đổi vào CSDL", Toast.LENGTH_SHORT).show()
                    disableSaveButton()
                    setResult(RESULT_OK)
                }
            }
        }

        findViewById<CardView>(R.id.cardBodyProfile).setOnClickListener {
            val items = arrayOf("Giảm cân", "Giữ cân", "Tăng cân")
            AlertDialog.Builder(this)
                .setTitle("Mục tiêu cơ thể")
                .setSingleChoiceItems(items, 0) { dialog, which ->
                    Toast.makeText(this, "Đã chọn: ${items[which]}", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun checkChanges() {
        val currentName = etFullName.text.toString().trim()
        val currentEmail = etEmail.text.toString().trim()
        val currentPhone = etPhone.text.toString().trim()

        if (currentName != originalName || currentEmail != originalEmail || currentPhone != originalPhone) {
            enableSaveButton()
        } else {
            disableSaveButton()
        }
    }

    private fun enableSaveButton() {
        btnSaveChanges.isEnabled = true
        btnSaveChanges.setBackgroundColor(android.graphics.Color.parseColor("#1B6B45"))
        btnSaveChanges.setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
    }

    private fun disableSaveButton() {
        btnSaveChanges.isEnabled = false
        btnSaveChanges.setBackgroundColor(android.graphics.Color.parseColor("#E1E7E3"))
        btnSaveChanges.setTextColor(android.graphics.Color.parseColor("#6B7280"))
    }

    private fun handleBackPress() {
        if (btnSaveChanges.isEnabled) {
            AlertDialog.Builder(this)
                .setTitle("Hủy thay đổi?")
                .setMessage("Bạn có thay đổi chưa lưu. Bạn có chắc muốn thoát?")
                .setPositiveButton("Thoát") { _, _ ->
                    finish()
                }
                .setNegativeButton("Ở lại", null)
                .show()
        } else {
            finish()
        }
    }
}
