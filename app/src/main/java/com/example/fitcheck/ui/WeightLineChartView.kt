package com.example.fitcheck.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.fitcheck.data.WeightRecord
import kotlin.math.max

class WeightLineChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1B6B45")
        strokeWidth = 6f
        style = Paint.Style.STROKE
    }

    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1B6B45")
        style = Paint.Style.FILL
    }

    private val dotOuterPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#E8F3EE")
        style = Paint.Style.FILL
    }

    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#E5E7EB")
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#6B7280")
        textSize = 32f
        textAlign = Paint.Align.CENTER
    }

    private var records = listOf<WeightRecord>()

    fun setRecords(newRecords: List<WeightRecord>) {
        records = newRecords.sortedBy { it.id } // Chronological order by ID
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (records.size < 2) return

        val width = width.toFloat()
        val height = height.toFloat()
        val paddingLeft = 80f
        val paddingRight = 80f
        val paddingTop = 60f
        val paddingBottom = 80f

        val minWeight = records.minOf { it.weight } - 1f
        val maxWeight = records.maxOf { it.weight } + 1f
        val weightRange = max(maxWeight - minWeight, 1f)

        // Draw grid lines
        val gridLines = 4
        for (i in 0 until gridLines) {
            val y = paddingTop + i * (height - paddingTop - paddingBottom) / (gridLines - 1)
            canvas.drawLine(paddingLeft, y, width - paddingRight, y, gridPaint)
        }

        val path = Path()

        records.forEachIndexed { index, record ->
            val x = paddingLeft + index * (width - paddingLeft - paddingRight) / (records.size - 1)
            val y = height - paddingBottom - (record.weight - minWeight) / weightRange * (height - paddingTop - paddingBottom)

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        // Draw line
        canvas.drawPath(path, linePaint)

        // Draw points and values
        records.forEachIndexed { index, record ->
            val x = paddingLeft + index * (width - paddingLeft - paddingRight) / (records.size - 1)
            val y = height - paddingBottom - (record.weight - minWeight) / weightRange * (height - paddingTop - paddingBottom)

            canvas.drawCircle(x, y, 16f, dotOuterPaint)
            canvas.drawCircle(x, y, 8f, dotPaint)

            // Value text above dot
            canvas.drawText("${record.weight}", x, y - 24f, textPaint)

            // Short date below
            val dateLabel = record.date.substringBeforeLast("/")
            canvas.drawText(dateLabel, x, height - 20f, textPaint)
        }
    }
}
