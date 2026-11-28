package com.example.fitcheck.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.example.fitcheck.R
import com.example.fitcheck.data.AppDatabase
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileWriter

class SettingsActivity : AppCompatActivity() {

    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var tvUserInitials: TextView
    private lateinit var tvUnitValue: TextView
    private lateinit var tvLanguageValue: TextView
    private lateinit var tvDarkModeStatus: TextView
    private lateinit var switchDarkMode: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Restore dark mode state before inflating layout if needed, though usually handled at app level
        if (AppPreferences.isDarkMode(this)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_settings)

        // Setup bottom nav if it exists in settings
        BottomNavHelper.setupBottomNav(this)

        initViews()
        loadPreferences()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        loadPreferences() // Reload in case returning from PersonalInfoActivity
    }

    private fun initViews() {
        tvUserName = findViewById(R.id.tvUserName)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        tvUserInitials = findViewById(R.id.tvUserInitials)
        tvUnitValue = findViewById(R.id.tvUnitValue)
        tvLanguageValue = findViewById(R.id.tvLanguageValue)
        tvDarkModeStatus = findViewById(R.id.tvDarkModeStatus)
        switchDarkMode = findViewById(R.id.switchDarkMode)
    }

    private fun loadPreferences() {
        val name = AppPreferences.getUserName(this)
        val email = AppPreferences.getUserEmail(this)
        tvUserName.text = name
        tvUserEmail.text = email

        val words = name.trim().split("\\s+".toRegex())
        var initials = ""
        if (words.isNotEmpty()) {
            if (words.size == 1) {
                initials = words[0].take(2).uppercase()
            } else {
                initials = (words[words.size - 2].take(1) + words[words.size - 1].take(1)).uppercase()
            }
        }
        tvUserInitials.text = initials

        tvUnitValue.text = if (AppPreferences.getUnits(this) == "kg") "Hệ mét · kg, cm" else "Hệ Anh · lbs, ft"
        tvLanguageValue.text = if (AppPreferences.getLanguage(this) == "vi") "Tiếng Việt" else "English"
        
        val isDark = AppPreferences.isDarkMode(this)
        switchDarkMode.isChecked = isDark
        tvDarkModeStatus.text = if (isDark) "Đang bật" else "Đang tắt"
    }

    private fun setupListeners() {
        findViewById<android.view.View>(R.id.btnBack)?.setOnClickListener {
            finish()
        }

        findViewById<CardView>(R.id.cardUserProfile).setOnClickListener {
            startActivity(Intent(this, PersonalInfoActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.rowReminder).setOnClickListener {
            startActivity(Intent(this, RemindersActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.rowUnits).setOnClickListener {
            val units = arrayOf("Hệ mét · kg, cm", "Hệ Anh · lbs, ft")
            val currentUnit = AppPreferences.getUnits(this)
            val checkedItem = if (currentUnit == "kg") 0 else 1

            AlertDialog.Builder(this)
                .setTitle("Đơn vị đo lường")
                .setSingleChoiceItems(units, checkedItem) { dialog, which ->
                    val selected = if (which == 0) "kg" else "lbs"
                    AppPreferences.setUnits(this, selected)
                    loadPreferences()
                    dialog.dismiss()
                }
                .setNegativeButton("Hủy", null)
                .show()
        }

        findViewById<LinearLayout>(R.id.rowLanguage).setOnClickListener {
            val langs = arrayOf("Tiếng Việt", "English")
            val currentLang = AppPreferences.getLanguage(this)
            val checkedItem = if (currentLang == "vi") 0 else 1

            AlertDialog.Builder(this)
                .setTitle("Ngôn ngữ")
                .setSingleChoiceItems(langs, checkedItem) { dialog, which ->
                    val selected = if (which == 0) "vi" else "en"
                    AppPreferences.setLanguage(this, selected)
                    loadPreferences()
                    dialog.dismiss()
                }
                .setNegativeButton("Hủy", null)
                .show()
        }

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != AppPreferences.isDarkMode(this)) {
                AppPreferences.setDarkMode(this, isChecked)
                tvDarkModeStatus.text = if (isChecked) "Đang bật" else "Đang tắt"
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        
        // Ensure clicking the row also toggles the switch
        findViewById<LinearLayout>(R.id.rowDarkMode).setOnClickListener {
            switchDarkMode.isChecked = !switchDarkMode.isChecked
        }

        findViewById<LinearLayout>(R.id.rowPrivacy).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Quyền riêng tư")
                .setMessage("FitCheck lưu trữ dữ liệu hoàn toàn cục bộ trên thiết bị của bạn. Chúng tôi không thu thập thông tin cá nhân lên máy chủ.")
                .setPositiveButton("Đã hiểu", null)
                .show()
        }

        findViewById<LinearLayout>(R.id.rowExportData).setOnClickListener {
            exportDataToCsv()
        }

        findViewById<android.view.View>(R.id.rowHelp).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Hỗ trợ")
                .setMessage("FitCheck Phiên bản 1.0.0\nỨng dụng theo dõi sức khỏe cá nhân.")
                .setPositiveButton("Đóng", null)
                .show()
        }

        findViewById<android.view.View>(R.id.btnLogout).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi FitCheck?")
                .setPositiveButton("Đăng xuất") { _, _ ->
                    AppPreferences.clearSession(this)
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Hủy", null)
                .show()
        }
    }

    private fun exportDataToCsv() {
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getDatabase(this@SettingsActivity).weightDao()
            // Collect the list manually since getAllWeights returns a Flow
            // Wait, since we are exporting, we might need a suspend function in Dao that returns a List.
            // But since I shouldn't break the existing Dao (it returns Flow), I can either collect it or add a simple query.
            // Let's just add a simple query to WeightDao if it doesn't exist, or collect the flow and take the first emission.
            var records = emptyList<com.example.fitcheck.data.WeightRecord>()
            dao.getAllWeights().collect { list ->
                records = list

                if (records.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SettingsActivity, "Chưa có dữ liệu để xuất", Toast.LENGTH_SHORT).show()
                    }
                    return@collect
                }

                try {
                    val file = File(cacheDir, "FitCheck_WeightHistory.csv")
                    val writer = FileWriter(file)
                    writer.append("ID,Weight,Date,Session\n")
                    for (record in records) {
                        writer.append("${record.id},${record.weight},${record.date},${record.session}\n")
                    }
                    writer.flush()
                    writer.close()

                    // Use FileProvider to share
                    // Need to declare FileProvider in Manifest for this to work
                    val uri = FileProvider.getUriForFile(this@SettingsActivity, "${packageName}.fileprovider", file)
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/csv"
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Lịch sử cân nặng FitCheck")
                    intent.putExtra(Intent.EXTRA_STREAM, uri)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                    withContext(Dispatchers.Main) {
                        startActivity(Intent.createChooser(intent, "Chia sẻ dữ liệu qua"))
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SettingsActivity, "Lỗi khi xuất file", Toast.LENGTH_SHORT).show()
                    }
                }

                throw kotlinx.coroutines.CancellationException("Stop collecting")
            }
        }
    }
}
