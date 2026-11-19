package com.example.fitcheck.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitcheck.R
import com.example.fitcheck.data.WeightRecord
import com.example.fitcheck.viewmodel.WeightViewModel
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeightHistoryActivity : AppCompatActivity() {

    private val viewModel: WeightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_history)

        val rvWeightList = findViewById<RecyclerView>(R.id.rvWeightList)
        val btnAddWeight = findViewById<MaterialButton>(R.id.btnAddWeight)
        val tvLatestWeight = findViewById<TextView>(R.id.tvLatestWeight)
        val tvRecordCount = findViewById<TextView>(R.id.tvRecordCount)

        // 1. Khởi tạo Adapter kèm sự kiện Sửa và Xóa
        val adapter = WeightAdapter(
            onEditClick = { record ->
                showWeightDialog(record)
            },
            onDeleteClick = { record ->
                viewModel.delete(record)
                Toast.makeText(this, "Đã xóa bản ghi", Toast.LENGTH_SHORT).show()
            }
        )

        rvWeightList.layoutManager = LinearLayoutManager(this)
        rvWeightList.adapter = adapter

        // 2. Lắng nghe dữ liệu realtime từ Room Database qua ViewModel
        viewModel.allWeights.observe(this) { list ->
            adapter.submitList(list)
            
            // Cập nhật Cân nặng gần nhất
            val latest = list.firstOrNull()
            if (latest != null) {
                tvLatestWeight.text = latest.weight.toString()
            } else {
                tvLatestWeight.text = "--.-"
            }
            
            // Cập nhật số lượng bản ghi
            tvRecordCount.text = "${list.size} bản ghi"
        }

        // 3. Bấm nút Thêm cân nặng
        btnAddWeight.setOnClickListener {
            showWeightDialog(null)
        }
    }

    private fun showWeightDialog(recordToEdit: WeightRecord?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_weight_input, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()
        
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvTitle = dialogView.findViewById<TextView>(R.id.tvDialogTitle)
        val etWeight = dialogView.findViewById<EditText>(R.id.etWeightInput)
        val rbLbs = dialogView.findViewById<RadioButton>(R.id.rbLbs)
        val btnCancel = dialogView.findViewById<MaterialButton>(R.id.btnCancel)
        val btnSave = dialogView.findViewById<MaterialButton>(R.id.btnSave)

        if (recordToEdit != null) {
            tvTitle.text = "Sửa cân nặng"
            etWeight.setText(recordToEdit.weight.toString())
        } else {
            tvTitle.text = "Thêm cân nặng"
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            val inputStr = etWeight.text.toString().trim()
            if (inputStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập cân nặng", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var weightValue = inputStr.toFloatOrNull()
            if (weightValue == null) {
                Toast.makeText(this, "Cân nặng không hợp lệ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Quy đổi nếu chọn lbs
            if (rbLbs.isChecked) {
                weightValue = weightValue / 2.20462f
            }

            // Làm tròn 1 chữ số thập phân
            weightValue = (weightValue * 10).roundToInt() / 10f

            if (recordToEdit == null) {
                // Thêm mới
                val cal = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                
                val dateStr = dateFormat.format(cal.time)
                val timeStr = timeFormat.format(cal.time)
                
                val hour = cal.get(Calendar.HOUR_OF_DAY)
                val sessionLabel = when {
                    hour < 12 -> "Buổi sáng"
                    hour < 18 -> "Buổi chiều"
                    else -> "Buổi tối"
                }
                val sessionStr = "$timeStr • $sessionLabel"

                val newRecord = WeightRecord(
                    weight = weightValue,
                    date = dateStr,
                    session = sessionStr
                )
                viewModel.insert(newRecord)
                Toast.makeText(this, "Đã thêm cân nặng thành công!", Toast.LENGTH_SHORT).show()
            } else {
                // Cập nhật
                val updatedRecord = recordToEdit.copy(weight = weightValue)
                viewModel.update(updatedRecord)
                Toast.makeText(this, "Đã cập nhật thành công!", Toast.LENGTH_SHORT).show()
            }
            
            dialog.dismiss()
        }

        dialog.show()
    }
}
