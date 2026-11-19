package com.example.fitcheck.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitcheck.R
import com.example.fitcheck.data.WeightRecord

class WeightAdapter(
    private val onEditClick: (WeightRecord) -> Unit,
    private val onDeleteClick: (WeightRecord) -> Unit
) : RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {

    private var weightList = listOf<WeightRecord>()

    fun submitList(list: List<WeightRecord>) {
        weightList = list
        notifyDataSetChanged()
    }

    class WeightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvSession: TextView = itemView.findViewById(R.id.tvSession)
        val tvWeightValue: TextView = itemView.findViewById(R.id.tvWeightValue)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weight_record, parent, false)
        return WeightViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        val record = weightList[position]
        holder.tvDate.text = record.date
        holder.tvSession.text = record.session
        holder.tvWeightValue.text = "${record.weight} kg"

        // Bấm nút Sửa
        holder.btnEdit.setOnClickListener { onEditClick(record) }
        // Bấm nút Xóa
        holder.btnDelete.setOnClickListener { onDeleteClick(record) }
    }

    override fun getItemCount(): Int = weightList.size
}