package com.example.fitcheck.data

import com.example.fitcheck.data.entity.WorkoutEntity

object SeedWorkoutsLight {
    fun getWorkouts(): List<WorkoutEntity> {
        val intensity = SeedData.INTENSITY_LIGHT
        return listOf(
            // ---- Không dụng cụ (10 bài) ----
            WorkoutEntity(name = "Đi bộ nhanh buổi sáng", intensity = intensity, durationMinutes = 30, hasEquipment = false, caloriesBurned = 150, description = "Cardio nhẹ nhàng, phù hợp người mới bắt đầu.", steps = "1. Khởi động 3 phút\n2. Đi bộ nhanh 25 phút\n3. Thả lỏng 2 phút"),
            WorkoutEntity(name = "Giãn cơ cổ vai gáy", intensity = intensity, durationMinutes = 15, hasEquipment = false, caloriesBurned = 60, description = "Thả lỏng cơ bắp sau khi ngồi làm việc lâu.", steps = "1. Xoay cổ nhẹ nhàng 3 phút\n2. Kéo giãn tay vai 7 phút\n3. Thở sâu 5 phút"),
            WorkoutEntity(name = "Đi dạo công viên", intensity = intensity, durationMinutes = 40, hasEquipment = false, caloriesBurned = 145, description = "Vận động nhẹ nhàng thư thái tinh thần.", steps = "1. Khởi động khớp 5 phút\n2. Bước đều nhịp 30 phút\n3. Hít thở sâu thả lỏng 5 phút"),
            WorkoutEntity(name = "Làm nóng & xoay khớp nhẹ", intensity = intensity, durationMinutes = 10, hasEquipment = false, caloriesBurned = 40, description = "Bài tập khởi động nhẹ nhàng đầu ngày.", steps = "1. Xoay cổ tay cổ chân 3 phút\n2. Vươn vai hít thở 4 phút\n3. Thả lỏng toàn thân 3 phút"),
            WorkoutEntity(name = "Tập cơ sàn chậu & hông", intensity = intensity, durationMinutes = 15, hasEquipment = false, caloriesBurned = 70, description = "Tăng sự linh hoạt cơ hông và thắt lưng.", steps = "1. Xoay hông nhẹ nhàng 3 phút\n2. Nghiêng người hai bên 7 phút\n3. Thả lỏng thắt lưng 5 phút"),
            WorkoutEntity(name = "Nâng gót chân & kiễng chân", intensity = intensity, durationMinutes = 12, hasEquipment = false, caloriesBurned = 50, description = "Tăng cường sức bền bắp chân.", steps = "1. Đứng thẳng người 2 phút\n2. Kiễng gót 20 lần x 3 hiệp 7 phút\n3. Thả lỏng bắp chân 3 phút"),
            WorkoutEntity(name = "Kéo giãn đùi sau & lưng", intensity = intensity, durationMinutes = 15, hasEquipment = false, caloriesBurned = 65, description = "Giảm căng cơ đùi sau và thắt lưng.", steps = "1. Đứng cúi người nhẹ 3 phút\n2. Kéo giãn chân trái phải 7 phút\n3. Thả lỏng đùi 5 phút"),
            WorkoutEntity(name = "Cúi gập người thư giãn", intensity = intensity, durationMinutes = 10, hasEquipment = false, caloriesBurned = 45, description = "Giảm áp lực cột sống sau giờ làm.", steps = "1. Đứng thẳng hít sâu 2 phút\n2. Thả lỏng tay cúi gập 5 phút\n3. Đứng dậy hít thở 3 phút"),
            WorkoutEntity(name = "Xoay tay & ngực mở rộng", intensity = intensity, durationMinutes = 12, hasEquipment = false, caloriesBurned = 55, description = "Mở rộng lồng ngực và linh hoạt vai.", steps = "1. Xoay khớp vai 3 phút\n2. Mở rộng tay ngang 6 phút\n3. Thả lỏng cánh tay 3 phút"),
            WorkoutEntity(name = "Mông & đùi nhẹ nhàng", intensity = intensity, durationMinutes = 15, hasEquipment = false, caloriesBurned = 75, description = "Kích hoạt cơ mông nhẹ nhàng.", steps = "1. Đá chân sau nhẹ 3 phút\n2. Đứng nâng đùi 7 phút\n3. Thả lỏng cơ chân 5 phút"),

            // ---- Có dụng cụ (10 bài) ----
            WorkoutEntity(name = "Yoga thư giãn cơ bản", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 90, description = "Giãn cơ, tăng linh hoạt và giảm căng thẳng.", steps = "1. Chào mặt trời 5 phút\n2. Tư thế chó úp mặt 10 phút\n3. Tư thế em bé 5 phút"),
            WorkoutEntity(name = "Giãn cơ với thảm tập", intensity = intensity, durationMinutes = 15, hasEquipment = true, caloriesBurned = 65, description = "Thả lỏng toàn thân trên thảm.", steps = "1. Nằm ngửa hít thở 3 phút\n2. Kéo gối sát ngực 7 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Kéo dây kháng lực vai", intensity = intensity, durationMinutes = 15, hasEquipment = true, caloriesBurned = 80, description = "Mở rộng vai với dây kháng lực nhẹ.", steps = "1. Cầm dây rộng bằng vai 3 phút\n2. Kéo sang hai bên 7 phút\n3. Thả lỏng vai 5 phút"),
            WorkoutEntity(name = "Cuộn bọt đùi & lưng (Foam Roller)", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 75, description = "Massage thả lỏng cơ đùi và lưng bằng con lăn.", steps = "1. Lăn cơ đùi trước 5 phút\n2. Lăn cơ lưng 10 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Tập tạ nhẹ thả lỏng tay", intensity = intensity, durationMinutes = 15, hasEquipment = true, caloriesBurned = 85, description = "Vận động tay nhẹ nhàng với tạ 1kg.", steps = "1. Khởi động cổ tay 3 phút\n2. Nâng tạ nhẹ 7 phút\n3. Thả lỏng cánh tay 5 phút"),
            WorkoutEntity(name = "Xoay vòng lắc hông (Hula Hoop)", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 100, description = "Vận động hông nhẹ nhàng với vòng.", steps = "1. Xoay hông nhẹ 3 phút\n2. Lắc vòng nhịp nhàng 12 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Đạp xe trong nhà nhịp nhẹ", intensity = intensity, durationMinutes = 25, hasEquipment = true, caloriesBurned = 120, description = "Thả lỏng khớp gối với xe đạp tập.", steps = "1. Đạp chậm 5 phút\n2. Đạp nhịp nhàng 15 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Ép dẻo với thảm Yoga", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 85, description = "Tăng độ dẻo dai cột sống.", steps = "1. Tư thế con mèo 5 phút\n2. Tư thế xà cạp 10 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Tập gậy gỗ linh hoạt vai", intensity = intensity, durationMinutes = 15, hasEquipment = true, caloriesBurned = 60, description = "Giảm mỏi vai với gậy tập.", steps = "1. Đưa gậy qua đầu 3 phút\n2. Xoay gậy hai bên 7 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Bóng tập Yoga dẻo dai", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 95, description = "Tập cân bằng trên bóng Yoga.", steps = "1. Ngồi cân bằng 5 phút\n2. Ngửa lưng trên bóng 10 phút\n3. Thả lỏng 5 phút")
        )
    }
}
