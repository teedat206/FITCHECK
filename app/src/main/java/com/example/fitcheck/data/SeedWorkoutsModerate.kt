package com.example.fitcheck.data

import com.example.fitcheck.data.entity.WorkoutEntity

object SeedWorkoutsModerate {
    fun getWorkouts(): List<WorkoutEntity> {
        val intensity = SeedData.INTENSITY_MODERATE
        return listOf(
            // ---- Không dụng cụ (10 bài) ----
            WorkoutEntity(name = "Chạy bộ tại chỗ", intensity = intensity, durationMinutes = 25, hasEquipment = false, caloriesBurned = 220, description = "Tăng nhịp tim và đốt calo toàn thân hiệu quả.", steps = "1. Khởi động 3 phút\n2. Nâng cao đùi vừa phải 20 phút\n3. Thả lỏng 2 phút"),
            WorkoutEntity(name = "Squat & Đùi thon gọn", intensity = intensity, durationMinutes = 20, hasEquipment = false, caloriesBurned = 190, description = "Tập trung nhóm cơ chân và vòng 3.", steps = "1. Khởi động khớp gối 3 phút\n2. Hạ mông vuông góc 14 phút\n3. Thả lỏng đùi 3 phút"),
            WorkoutEntity(name = "Bơi lội tự do", intensity = intensity, durationMinutes = 30, hasEquipment = false, caloriesBurned = 260, description = "Sức bền toàn thân dưới nước.", steps = "1. Khởi động trên bờ 5 phút\n2. Bơi nhịp nhàng 20 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Aerobic sôi động tại nhà", intensity = intensity, durationMinutes = 25, hasEquipment = false, caloriesBurned = 210, description = "Nhảy theo nhịp nhạc tăng dẻo dai.", steps = "1. Khởi động nhẹ 5 phút\n2. Nhảy aerobic 15 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Chạy nâng cao đùi & gót chạm mông", intensity = intensity, durationMinutes = 20, hasEquipment = false, caloriesBurned = 180, description = "Tăng sự linh hoạt đôi chân.", steps = "1. Khởi động 3 phút\n2. Chạy luân phiên 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Jumping Jacks nhịp đều", intensity = intensity, durationMinutes = 15, hasEquipment = false, caloriesBurned = 160, description = "Bật nhảy toàn thân gia tăng trao đổi chất.", steps = "1. Khởi động 3 phút\n2. Bật nhảy 3 hiệp 9 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Gập bụng Crunch săn chắc", intensity = intensity, durationMinutes = 20, hasEquipment = false, caloriesBurned = 170, description = "Tập trung vào nhóm cơ bụng trên.", steps = "1. Khởi động lưng 3 phút\n2. Gập bụng 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Lunge bước chân gập gối", intensity = intensity, durationMinutes = 20, hasEquipment = false, caloriesBurned = 195, description = "Tăng sức mạnh đùi và cân bằng bắp chân.", steps = "1. Khởi động khớp hông 3 phút\n2. Bước lunge luân phiên 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Chống đẩy nghiêng gối (Knee Push-ups)", intensity = intensity, durationMinutes = 15, hasEquipment = false, caloriesBurned = 155, description = "Tập ngực và tay sau nhẹ nhàng.", steps = "1. Khởi động vai 3 phút\n2. Chống đẩy quỳ gối 9 phút\n3. Thả lỏng vai 3 phút"),
            WorkoutEntity(name = "Đá chân sau & Mông đùi", intensity = intensity, durationMinutes = 20, hasEquipment = false, caloriesBurned = 175, description = "Củng cố cơ mông săn chắc.", steps = "1. Khởi động hông 3 phút\n2. Quỳ đá chân sau 14 phút\n3. Thả lỏng 3 phút"),

            // ---- Có dụng cụ (10 bài) ----
            WorkoutEntity(name = "Đạp xe thể thao", intensity = intensity, durationMinutes = 30, hasEquipment = true, caloriesBurned = 250, description = "Săn chắc cơ đùi và tăng sức bền tim mạch.", steps = "1. Khởi động 5 phút\n2. Đạp xe nhịp nhàng 20 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Tập tạ tay săn chắc", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 180, description = "Phát triển cơ bắp tay và vai gọn gàng.", steps = "1. Khởi động tay 3 phút\n2. Nâng tạ 2-4kg 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Tập tạ ấm (Kettlebell) toàn thân", intensity = intensity, durationMinutes = 25, hasEquipment = true, caloriesBurned = 230, description = "Sức mạnh bùng nổ với tạ ấm.", steps = "1. Khởi động 5 phút\n2. Vung tạ ấm 15 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Nhảy dây nhịp độ vừa", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 200, description = "Gia tăng nhịp tim và sức bền đôi chân.", steps = "1. Khởi động cổ chân 3 phút\n2. Nhảy dây nhịp đều 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Chèo thuyền máy (Rowing Machine)", intensity = intensity, durationMinutes = 25, hasEquipment = true, caloriesBurned = 240, description = "Tăng cường sức mạnh lưng và tay.", steps = "1. Khởi động 5 phút\n2. Chèo thuyền nhịp nhàng 15 phút\n3. Thả lỏng 5 phút"),
            WorkoutEntity(name = "Tập cơ tay với dây thun", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 165, description = "Săn chắc tay sau và cẳng tay.", steps = "1. Khởi động 3 phút\n2. Kéo dây thun 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Gập bụng trên ghế dốc", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 185, description = "Tác động sâu nhóm cơ bụng.", steps = "1. Khởi động lưng 3 phút\n2. Gập bụng trên ghế 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Ép ngực với tạ đơn", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 190, description = "Săn chắc cơ ngực trên thảm tập.", steps = "1. Khởi động vai 3 phút\n2. Nằm ép tạ đơn 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Đẩy tạ vai đứng", intensity = intensity, durationMinutes = 20, hasEquipment = true, caloriesBurned = 195, description = "Phát triển nhóm cơ vai toàn diện.", steps = "1. Khởi động khớp vai 3 phút\n2. Đẩy tạ qua đầu 14 phút\n3. Thả lỏng 3 phút"),
            WorkoutEntity(name = "Tập đùi với bóng rèn luyện", intensity = intensity, durationMinutes = 25, hasEquipment = true, caloriesBurned = 210, description = "Củng cố cơ đùi trong và mông.", steps = "1. Khởi động 5 phút\n2. Kẹp bóng squat 15 phút\n3. Thả lỏng 5 phút")
        )
    }
}
