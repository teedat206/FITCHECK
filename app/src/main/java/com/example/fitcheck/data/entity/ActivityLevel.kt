package com.example.fitcheck.data.entity

enum class ActivityLevel(
    val key: String,
    val label: String,
    val description: String,
    val factor: Float
) {
    SEDENTARY("IT_VAN_DONG", "Ít vận động", "Cả ngày hầu như chỉ ngồi / nghỉ ngơi · × 1,2", 1.2f),
    LIGHT("VAN_DONG_NHE", "Vận động nhẹ", "Tập nhẹ 1–3 buổi/tuần · × 1,375", 1.375f),
    MODERATE("VAN_DONG_VUA", "Vận động vừa", "Tập vừa 3–5 buổi/tuần · × 1,55", 1.55f),
    HIGH("VAN_DONG_NHIEU", "Vận động nhiều", "Tập nặng 6–7 buổi/tuần · × 1,725", 1.725f),
    EXTRA_HIGH("RAT_NHIEU", "Rất nhiều", "Tập nặng & lao động thể lực · × 1,9", 1.9f);

    companion object {
        fun fromKey(key: String): ActivityLevel? {
            val trimmed = key.trim()
            return entries.firstOrNull { 
                it.key.equals(trimmed, ignoreCase = true) || 
                it.label.equals(trimmed, ignoreCase = true) ||
                (trimmed == "Ít" && it == SEDENTARY) ||
                (trimmed == "Vừa" && it == MODERATE) ||
                (trimmed == "Nhiều" && it == HIGH)
            }
        }
    }
}
