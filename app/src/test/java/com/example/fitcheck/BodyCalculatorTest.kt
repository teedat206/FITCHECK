package com.example.fitcheck

import com.example.fitcheck.data.BodyCalculator
import com.example.fitcheck.data.entity.ActivityLevel
import org.junit.Assert.*
import org.junit.Test

class BodyCalculatorTest {

    @Test
    fun testFemale24Years160cm56kgLightActivity() {
        val bmi = BodyCalculator.calculateBmi(56f, 160f)
        assertEquals(21.9f, bmi, 0.1f)
        assertEquals("Bình thường", BodyCalculator.getBmiCategory(bmi))

        val bmr = BodyCalculator.calculateBmr(56f, 160f, 24, "Nữ")
        assertEquals(1279, bmr)

        val tdee = BodyCalculator.calculateTdee(bmr, ActivityLevel.LIGHT)
        assertEquals(1759, tdee)
    }

    @Test
    fun testBmiBoundaryCategories() {
        assertEquals("Thiếu cân", BodyCalculator.getBmiCategory(18.4f))
        assertEquals("Bình thường", BodyCalculator.getBmiCategory(18.5f))
        assertEquals("Bình thường", BodyCalculator.getBmiCategory(24.9f))
        assertEquals("Thừa cân", BodyCalculator.getBmiCategory(25.0f))
        assertEquals("Thừa cân", BodyCalculator.getBmiCategory(29.9f))
        assertEquals("Béo phì", BodyCalculator.getBmiCategory(30.0f))
    }
}
