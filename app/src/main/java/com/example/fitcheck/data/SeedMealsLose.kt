package com.example.fitcheck.data

import com.example.fitcheck.data.entity.MealEntity

object SeedMealsLose {
    fun getMeals(): List<MealEntity> {
        val goal = SeedData.GOAL_LOSE
        return listOf(
            // ---- 12 Sáng ----
            MealEntity(name = "Cháo yến mạch thịt bằm", goal = goal, mealType = "Sáng", calories = 300, prepTimeMinutes = 10, description = "Bữa sáng ấm bụng, giàu chất xơ hòa tan giúp no lâu.", ingredients = "Yến mạch 40g, thịt nạc dăm bằm 50g, hành lá, tiêu"),
            MealEntity(name = "Bánh mì đen trứng ốp la", goal = goal, mealType = "Sáng", calories = 290, prepTimeMinutes = 8, description = "Cung cấp năng lượng bền vững với carbohydrate phức hợp.", ingredients = "Bánh mì đen 2 lát, trứng gà 1 quả, xà lách"),
            MealEntity(name = "Khoai lang luộc & sữa đậu nành", goal = goal, mealType = "Sáng", calories = 280, prepTimeMinutes = 15, description = "Món thanh vị, giàu vitamin A và chất xơ hỗ trợ tiêu hóa.", ingredients = "Khoai lang luộc 150g, sữa đậu nành không đường 250ml"),
            MealEntity(name = "Súp gà xé nấm tuyết", goal = goal, mealType = "Sáng", calories = 240, prepTimeMinutes = 20, description = "Súp gà thanh nhẹ, dễ tiêu hóa cho buổi sáng.", ingredients = "Ức gà xé 80g, nấm tuyết 30g, trứng gà 1 quả"),
            MealEntity(name = "Bún gạo lứt mọc giò sống", goal = goal, mealType = "Sáng", calories = 290, prepTimeMinutes = 15, description = "Bún lứt thanh đạm giòn dai ngon miệng.", ingredients = "Bún lứt 60g, mọc heo nạc 50g, hành ngò"),
            MealEntity(name = "Sinh tố yến mạch chuối hạt chia", goal = goal, mealType = "Sáng", calories = 260, prepTimeMinutes = 5, description = "Thức uống giàu năng lượng tự nhiên và vi chất.", ingredients = "Yến mạch 30g, chuối 1 quả, hạt chia 10g, sữa hạnh nhân"),
            MealEntity(name = "Bánh bao nguyên cám nhân ức gà", goal = goal, mealType = "Sáng", calories = 280, prepTimeMinutes = 15, description = "Bánh bao bột cám ít calo, no lâu.", ingredients = "Vỏ bột nguyên cám, ức gà bằm 60g, mộc nhĩ"),
            MealEntity(name = "Súp bí đỏ đậu hũ mềm", goal = goal, mealType = "Sáng", calories = 210, prepTimeMinutes = 15, description = "Súp bí đỏ sánh mịn giàu beta-carotene.", ingredients = "Bí đỏ 150g, đậu hũ non 80g, ngò rí"),
            MealEntity(name = "Bánh cuốn chay nấm mộc nhĩ", goal = goal, mealType = "Sáng", calories = 270, prepTimeMinutes = 15, description = "Bánh cuốn mềm thanh đạm ít dầu mỡ.", ingredients = "Bánh cuốn 120g, nấm mèo, củ sắn, nước mắm chay"),
            MealEntity(name = "Cháo hạt kê thịt nạc", goal = goal, mealType = "Sáng", calories = 250, prepTimeMinutes = 25, description = "Cháo hạt kê bổ dưỡng thanh lọc dạ dày.", ingredients = "Hạt kê 40g, thịt nạc dăm 40g, hành hoa"),
            MealEntity(name = "Bắp luộc & trứng gà luộc", goal = goal, mealType = "Sáng", calories = 230, prepTimeMinutes = 15, description = "Bữa sáng dân dã giàu chất xơ và đạm.", ingredients = "Bắp nếp luộc 1 trái, trứng gà 1 quả"),
            MealEntity(name = "Ngũ cốc hạt & sữa chua không đường", goal = goal, mealType = "Sáng", calories = 260, prepTimeMinutes = 3, description = "Nhanh gọn, giàu vi khuẩn có lợi cho đường ruột.", ingredients = "Yến mạch nướng 30g, sữa chua không đường 100g"),

            // ---- 12 Trưa ----
            MealEntity(name = "Ức gà luộc cuốn rau sống", goal = goal, mealType = "Trưa", calories = 350, prepTimeMinutes = 20, description = "Tối ưu hóa lượng đạm nạc và giảm thiểu chất béo.", ingredients = "Ức gà 150g, rau sống 100g, nước mắm gừng"),
            MealEntity(name = "Cơm gạo lứt tôm hấp bông cải", goal = goal, mealType = "Trưa", calories = 380, prepTimeMinutes = 25, description = "Kết hợp đạm hải sản lành mạnh và rau xanh chống oxy hóa.", ingredients = "Cơm gạo lứt 100g, tôm tươi 120g, bông cải xanh 100g"),
            MealEntity(name = "Bún lứt trộn ức gà rau củ", goal = goal, mealType = "Trưa", calories = 360, prepTimeMinutes = 20, description = "Thanh mát, không ngấy dầu mỡ, giữ dáng hiệu quả.", ingredients = "Bún gạo lứt 60g, ức gà xé 120g, dưa leo, giá đỗ"),
            MealEntity(name = "Salad cá ngừ ngâm nước dưa leo", goal = goal, mealType = "Trưa", calories = 280, prepTimeMinutes = 10, description = "Món salad giòn mát giàu đạm nạc.", ingredients = "Cá ngừ ngâm nước 100g, dưa leo, xà lách, sốt chanh bơ"),
            MealEntity(name = "Mực hấp sả gừng & cơm gạo lứt", goal = goal, mealType = "Trưa", calories = 340, prepTimeMinutes = 20, description = "Mực hấp giòn ngọt ít calo.", ingredients = "Mực tươi 150g, sả, gừng, cơm gạo lứt 80g"),
            MealEntity(name = "Miến xào nấm rau củ chay", goal = goal, mealType = "Trưa", calories = 310, prepTimeMinutes = 15, description = "Miến dong dai ngon kết hợp nhiều rau nấm.", ingredients = "Miến dong 50g, nấm đùi gà 60g, cà rốt, hẹ"),
            MealEntity(name = "Bún chả cá thát lát thì là", goal = goal, mealType = "Trưa", calories = 330, prepTimeMinutes = 20, description = "Chả cá tự làm dai ngọt không độn bột.", ingredients = "Bún tươi 100g, chả cá thát lát 100g, thì là, cà chua"),
            MealEntity(name = "Cơm gạo lứt bò xào ớt chuông ít dầu", goal = goal, mealType = "Trưa", calories = 390, prepTimeMinutes = 20, description = "Thịt bò giàu sắt kết hợp ớt chuông vitamin C.", ingredients = "Cơm lứt 100g, thịt bò thăn 100g, ớt chuông"),
            MealEntity(name = "Tôm nướng muối ớt & rau xà lách", goal = goal, mealType = "Trưa", calories = 300, prepTimeMinutes = 15, description = "Tôm nướng đậm đà ít mỡ.", ingredients = "Tôm sú 150g, muối ớt, xà lách, rau thơm"),
            MealEntity(name = "Gỏi ngó sen tôm thịt ức gà", goal = goal, mealType = "Trưa", calories = 290, prepTimeMinutes = 20, description = "Gỏi ngó sen giòn sần sật thanh lọc cơ thể.", ingredients = "Ngó sen 100g, ức gà 80g, tôm 50g, nước mắm chua ngọt"),
            MealEntity(name = "Cá thu sốt cà chua ít mỡ & cơm lứt", goal = goal, mealType = "Trưa", calories = 370, prepTimeMinutes = 25, description = "Cá thu giàu omega-3 bổ dưỡng.", ingredients = "Cá thu 120g, cà chua 2 quả, cơm lứt 80g"),
            MealEntity(name = "Bún mọc dọc mùng thanh nhẹ", goal = goal, mealType = "Trưa", calories = 340, prepTimeMinutes = 20, description = "Bún mọc nước dùng trong ngọt thanh.", ingredients = "Bún 100g, mọc nạc 80g, dọc mùng, tía tô"),

            // ---- 12 Tối ----
            MealEntity(name = "Canh đậu hũ rong biển thịt băm", goal = goal, mealType = "Tối", calories = 250, prepTimeMinutes = 15, description = "Bữa tối nhẹ bụng, thanh lọc cơ thể.", ingredients = "Đậu hũ non 100g, rong biển khô 5g, thịt heo băm 40g"),
            MealEntity(name = "Cá rô phi hấp sả gừng rau luộc", goal = goal, mealType = "Tối", calories = 280, prepTimeMinutes = 20, description = "Cung cấp omega-3 và đạm dễ tiêu hóa cho bữa tối ít calo.", ingredients = "Phi lê cá rô phi 150g, sả, gừng, đậu ve luộc 80g"),
            MealEntity(name = "Canh bí đỏ thịt băm thanh đạm", goal = goal, mealType = "Tối", calories = 260, prepTimeMinutes = 15, description = "Vị ngọt thanh giúp bữa tối ngon miệng không tích mỡ.", ingredients = "Bí đỏ 120g, thịt băm 50g, hành ngò"),
            MealEntity(name = "Canh hến nấu rau đắng", goal = goal, mealType = "Tối", calories = 210, prepTimeMinutes = 15, description = "Canh hến giải nhiệt thanh mát.", ingredients = "Thịt hến 100g, rau đắng 100g, hành tím"),
            MealEntity(name = "Thịt heo nạc luộc & xà lách chấm mắm gừng", goal = goal, mealType = "Tối", calories = 310, prepTimeMinutes = 20, description = "Thịt nạc luộc không mỡ đạm chất lượng.", ingredients = "Thịt thăn heo 120g, xà lách, nước mắm gừng"),
            MealEntity(name = "Canh khổ qua nhồi thịt nạc", goal = goal, mealType = "Tối", calories = 240, prepTimeMinutes = 25, description = "Khổ qua giúp hạ đường huyết và thanh nhiệt.", ingredients = "Khổ qua 1 trái, thịt nạc dăm 60g, mộc nhĩ"),
            MealEntity(name = "Canh cua đồng nấu rau mồng tơi", goal = goal, mealType = "Tối", calories = 220, prepTimeMinutes = 20, description = "Canh cua mát lành giàu canxi.", ingredients = "Cua đồng xay 100g, rau mồng tơi 100g"),
            MealEntity(name = "Canh tôm nấu bầu sắn", goal = goal, mealType = "Tối", calories = 230, prepTimeMinutes = 15, description = "Canh bầu ngọt tự nhiên nhẹ bụng.", ingredients = "Tôm tươi 80g, bầu gọt vỏ 150g"),
            MealEntity(name = "Cá lóc hấp bầu rau thơm", goal = goal, mealType = "Tối", calories = 270, prepTimeMinutes = 25, description = "Cá lóc hấp ngọt thịt không dầu mỡ.", ingredients = "Thịt cá lóc 150g, bầu khúc 100g, hành ngò"),
            MealEntity(name = "Salad ức gà nướng dầu olive", goal = goal, mealType = "Tối", calories = 300, prepTimeMinutes = 20, description = "Salad rau xanh kết hợp ức gà áp chảo.", ingredients = "Ức gà 120g, xà lách, xốt bơ chanh olive"),
            MealEntity(name = "Canh riêu cua mồng tơi", goal = goal, mealType = "Tối", calories = 240, prepTimeMinutes = 20, description = "Món canh dân dã đậm đà miền Bắc.", ingredients = "Cua đồng 100g, cà chua, mồng tơi"),
            MealEntity(name = "Bầu luộc chấm trứng dầm nước mắm", goal = goal, mealType = "Tối", calories = 200, prepTimeMinutes = 10, description = "Món ăn thanh đạm tối giản calo.", ingredients = "Bầu luộc 200g, trứng gà luộc 1 quả, nước mắm"),

            // ---- 10 Phụ ----
            MealEntity(name = "Sữa chua Hy Lạp & dâu tây", goal = goal, mealType = "Phụ", calories = 150, prepTimeMinutes = 3, description = "Giàu protein giúp kìm hãm cơn thèm ăn giữa buổi.", ingredients = "Sữa chua Hy Lạp không đường 100g, dâu tây 50g"),
            MealEntity(name = "Táo xanh & hạt hạnh nhân", goal = goal, mealType = "Phụ", calories = 160, prepTimeMinutes = 2, description = "Chất béo tốt và chất xơ giòn ngọt.", ingredients = "Táo xanh 1 quả, hạnh nhân rang 15g"),
            MealEntity(name = "Sinh tố cần tây dứa nguyên chất", goal = goal, mealType = "Phụ", calories = 120, prepTimeMinutes = 5, description = "Hỗ trợ tiêu hóa và thúc đẩy trao đổi chất.", ingredients = "Cần tây 100g, dứa 80g"),
            MealEntity(name = "Bưởi da xanh chín", goal = goal, mealType = "Phụ", calories = 130, prepTimeMinutes = 5, description = "Bưởi mọng nước giàu vitamin C và chất xơ.", ingredients = "Múi bưởi 200g, muối ớt"),
            MealEntity(name = "Ổi tươi rắc xí muội", goal = goal, mealType = "Phụ", calories = 125, prepTimeMinutes = 3, description = "Ổi giòn ngọt cực giàu vitamin C.", ingredients = "Ổi 150g, muối xí muội"),
            MealEntity(name = "Hạt óc chó rang mộc", goal = goal, mealType = "Phụ", calories = 140, prepTimeMinutes = 2, description = "Giàu omega-3 cho trí脳 minh mẫn.", ingredients = "Hạt óc chó 20g, hạnh nhân 10g"),
            MealEntity(name = "Sữa hạt macca không đường", goal = goal, mealType = "Phụ", calories = 130, prepTimeMinutes = 2, description = "Sữa hạt thơm béo tự nhiên.", ingredients = "Sữa hạt macca 200ml, hạt chia 5g"),
            MealEntity(name = "Dưa hấu mọng nước", goal = goal, mealType = "Phụ", calories = 120, prepTimeMinutes = 3, description = "Giải khát mọng nước giàu lycopene.", ingredients = "Dưa hấu miếng 250g, muối ớt"),
            MealEntity(name = "Thanh long ruột đỏ", goal = goal, mealType = "Phụ", calories = 125, prepTimeMinutes = 3, description = "Giàu anthocyanin chống lão hóa.", ingredients = "Thanh long 200g, hạt chia 5g"),
            MealEntity(name = "Nước ép cà rốt cam", goal = goal, mealType = "Phụ", calories = 135, prepTimeMinutes = 5, description = "Thức uống bổ mắt đẹp da.", ingredients = "Cà rốt 100g, cam tươi 1 quả")
        )
    }
}
