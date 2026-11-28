package com.example.fitcheck.data

import com.example.fitcheck.data.entity.MealEntity

object SeedMealsKeep {
    fun getMeals(): List<MealEntity> {
        val goal = SeedData.GOAL_KEEP
        return listOf(
            // ---- 12 Sáng ----
            MealEntity(name = "Phở bò tái truyền thống", goal = goal, mealType = "Sáng", calories = 400, prepTimeMinutes = 25, description = "Cung cấp đủ năng lượng và đạm cho buổi sáng năng động.", ingredients = "Bánh phở 120g, thịt bò tái 90g, nước dùng xương, hành lá"),
            MealEntity(name = "Bánh mì pate chả lụa", goal = goal, mealType = "Sáng", calories = 380, prepTimeMinutes = 10, description = "Bữa sáng nhanh gọn, hương vị đậm đà quen thuộc.", ingredients = "Bánh mì 1 ổ, pate 20g, chả lụa 50g, dưa leo"),
            MealEntity(name = "Hủ tiếu Nam Vang khô", goal = goal, mealType = "Sáng", calories = 420, prepTimeMinutes = 20, description = "Sự kết hợp đa dạng mang lại bữa sáng tròn vị.", ingredients = "Hủ tiếu 100g, thịt băm, tôm luộc, trứng cút"),
            MealEntity(name = "Bún bò Huế chay giò nấm", goal = goal, mealType = "Sáng", calories = 380, prepTimeMinutes = 20, description = "Bún bò Huế thơm nức mùi sả ớt thanh đạm.", ingredients = "Bún 120g, chả chay nấm 80g, nước dùng sả"),
            MealEntity(name = "Bánh cuốn nóng thịt bằm mộc nhĩ", goal = goal, mealType = "Sáng", calories = 360, prepTimeMinutes = 15, description = "Bánh cuốn nóng tráng mỏng mềm mượt.", ingredients = "Bánh cuốn 150g, thịt heo băm 60g, mộc nhĩ, nước mắm"),
            MealEntity(name = "Xôi gấc xé gà nạc", goal = goal, mealType = "Sáng", calories = 410, prepTimeMinutes = 20, description = "Xôi gấc đỏ tươi giàu lycopene kết hợp gà xé.", ingredients = "Xôi gấc 120g, thịt gà xé 70g, dưa leo"),
            MealEntity(name = "Hủ tiếu mọc sườn non", goal = goal, mealType = "Sáng", calories = 400, prepTimeMinutes = 20, description = "Tô hủ tiếu sườn ngọt thanh năng lượng vừa dồi dào.", ingredients = "Hủ tiếu 100g, sườn non 80g, mọc nạc 40g"),
            MealEntity(name = "Bánh mì ốp la xúc xích nạc", goal = goal, mealType = "Sáng", calories = 390, prepTimeMinutes = 10, description = "Bánh mì giòn rụm với trứng kẹp nóng hổi.", ingredients = "Bánh mì 1 ổ, trứng gà 1 quả, xúc xích nạc 1 cây"),
            MealEntity(name = "Cháo lòng heo dồi sụn vừa", goal = goal, mealType = "Sáng", calories = 420, prepTimeMinutes = 25, description = "Tô cháo ấm áp đậm đà hương vị truyền thống.", ingredients = "Gạo 50g, tim gan lòng heo 80g, hành gừng"),
            MealEntity(name = "Miến lươn giòn Nghệ An", goal = goal, mealType = "Sáng", calories = 370, prepTimeMinutes = 20, description = "Miến lươn thơm ngon đặc sản xứ Nghệ.", ingredients = "Miến 80g, lươn chiên giòn 60g, rau nổ, răm"),
            MealEntity(name = "Bánh mì chả cá Nha Trang", goal = goal, mealType = "Sáng", calories = 380, prepTimeMinutes = 10, description = "Chả cá hấp chiên dai thơm kẹp bánh mì giòn.", ingredients = "Bánh mì 1 ổ, chả cá chiên 80g, dưa chua, ngò"),
            MealEntity(name = "Phở gà ta nước trong", goal = goal, mealType = "Sáng", calories = 390, prepTimeMinutes = 20, description = "Phở gà ta nước dùng thanh ngọt dịu nhẹ.", ingredients = "Bánh phở 120g, thịt gà ta xé 80g, lá chanh, hành"),

            // ---- 12 Trưa ----
            MealEntity(name = "Cơm tấm sườn nướng", goal = goal, mealType = "Trưa", calories = 520, prepTimeMinutes = 30, description = "Món trưa truyền thống thơm ngon, năng lượng cân bằng.", ingredients = "Cơm tấm 150g, sườn nướng 100g, đồ chua, nước mắm"),
            MealEntity(name = "Bún chả Hà Nội", goal = goal, mealType = "Trưa", calories = 510, prepTimeMinutes = 25, description = "Thịt nướng thơm lừng ăn kèm bún tươi và rau sống.", ingredients = "Bún tươi 150g, chả nướng 120g, nước mắm, rau sống"),
            MealEntity(name = "Cá hồi áp chảo sốt bơ chanh khoai tây", goal = goal, mealType = "Trưa", calories = 540, prepTimeMinutes = 25, description = "Giàu omega-3 tốt cho tim mạch và trí não.", ingredients = "Phi lê cá hồi 140g, khoai tây nghiền 100g, bơ lạt"),
            MealEntity(name = "Cơm gà Hội An xé phay", goal = goal, mealType = "Trưa", calories = 500, prepTimeMinutes = 25, description = "Cơm gấc vàng ươm ăn kèm gỏi gà xé phay.", ingredients = "Cơm dẻo 150g, thịt gà ta 100g, hành tây, rau răm"),
            MealEntity(name = "Bún riêu cua giò heo vừa", goal = goal, mealType = "Trưa", calories = 490, prepTimeMinutes = 25, description = "Bún riêu chua ngọt thanh nhẹ dịu mát.", ingredients = "Bún 120g, riêu cua 60g, khoanh giò heo nhỏ 60g, đậu hũ"),
            MealEntity(name = "Mì Quảng gà tôm đặc sản", goal = goal, mealType = "Trưa", calories = 530, prepTimeMinutes = 25, description = "Đặc sản miền Trung đậm đà thơm béo phồng tôm.", ingredients = "Mì Quảng 120g, thịt gà 80g, tôm 50g, bánh tráng nướng"),
            MealEntity(name = "Cơm bò xào bông thiên lý", goal = goal, mealType = "Trưa", calories = 480, prepTimeMinutes = 20, description = "Bò xào bông thiên lý giòn ngọt tự nhiên.", ingredients = "Cơm trắng 120g, thịt bò 100g, bông thiên lý 100g"),
            MealEntity(name = "Bún đậu mắm tôm thịt luộc chả cốm", goal = goal, mealType = "Trưa", calories = 550, prepTimeMinutes = 20, description = "Set bún đậu đầy đủ chuẩn vị Hà thành.", ingredients = "Bún lá 120g, đậu hũ chiên 80g, thịt chân giò 60g, chả cốm"),
            MealEntity(name = "Cơm mực xào sa tế rau củ", goal = goal, mealType = "Trưa", calories = 490, prepTimeMinutes = 20, description = "Mực xào cay nhẹ đậm vị ăn rất tốn cơm.", ingredients = "Cơm 120g, mực tươi 120g, hành tây, cần tây"),
            MealEntity(name = "Bún cá Rạch Giá chấm mắm ớt", goal = goal, mealType = "Trưa", calories = 470, prepTimeMinutes = 25, description = "Bún cá kiên giang thơm ngọt thịt cá lóc.", ingredients = "Bún 120g, cá lóc phi lê 100g, tôm rim, nước lèo"),
            MealEntity(name = "Cơm sườn kho trứng cút", goal = goal, mealType = "Trưa", calories = 530, prepTimeMinutes = 25, description = "Cơm gia đình ấm cúng đậm đà bắt vị.", ingredients = "Cơm trắng 130g, sườn heo 100g, trứng cút 3 quả"),
            MealEntity(name = "Bún hải sản tôm bề bề", goal = goal, mealType = "Trưa", calories = 510, prepTimeMinutes = 25, description = "Tô bún hải sản chua cay ngọt nước cốt tôm.", ingredients = "Bún 120g, bề bề 60g, tôm 60g, chả mực 40g"),

            // ---- 12 Tối ----
            MealEntity(name = "Canh chua cá lóc & cơm", goal = goal, mealType = "Tối", calories = 480, prepTimeMinutes = 30, description = "Món canh đậm đà vị miền Nam, dễ ăn cho bữa tối.", ingredients = "Cá lóc 120g, bạc hà, đậu bắp, thơm, 1 bát cơm nhỏ"),
            MealEntity(name = "Thịt luộc cà pháo mắm tôm & cơm", goal = goal, mealType = "Tối", calories = 500, prepTimeMinutes = 25, description = "Hương vị mộc mạc cung cấp đạm và tinh bột vừa đủ.", ingredients = "Thịt ba chỉ luộc 120g, cà pháo, mắm tôm, cơm trắng"),
            MealEntity(name = "Mì xào bò rau củ", goal = goal, mealType = "Tối", calories = 520, prepTimeMinutes = 20, description = "Bữa tối nhanh chóng, đầy đủ dưỡng chất.", ingredients = "Mì vàng 100g, thịt bò 100g, cải ngọt, hành tây"),
            MealEntity(name = "Cá chép om dưa & cơm nóng", goal = goal, mealType = "Tối", calories = 490, prepTimeMinutes = 30, description = "Cá chép om dưa chua thanh dịu ngon miệng.", ingredients = "Cá chép 150g, dưa chua 100g, 1 bát cơm trắng"),
            MealEntity(name = "Cơm đùi gà kho gừng sả", goal = goal, mealType = "Tối", calories = 510, prepTimeMinutes = 25, description = "Đùi gà kho sả ớt ấm bụng ngày se lạnh.", ingredients = "Cơm trắng 120g, đùi gà 130g, sả, ớt"),
            MealEntity(name = "Canh sườn nấu bung dọc mùng", goal = goal, mealType = "Tối", calories = 470, prepTimeMinutes = 30, description = "Canh sườn béo nhẹ bung dọc mùng thanh mát.", ingredients = "Sườn heo 100g, dọc mùng 100g, cà chua, 1 bát cơm"),
            MealEntity(name = "Thịt kho tàu củ cải & cơm", goal = goal, mealType = "Tối", calories = 530, prepTimeMinutes = 35, description = "Thịt kho củ cải ngọt đậm đà hương vị mâm cơm Việt.", ingredients = "Thịt heo kho 120g, củ cải trắng 80g, 1 bát cơm"),
            MealEntity(name = "Cá bống kho tiêu mặn ngọt & cơm", goal = goal, mealType = "Tối", calories = 460, prepTimeMinutes = 25, description = "Cá bống kho tiêu săn cứng cay cay thơm phức.", ingredients = "Cá bống kho 100g, rau cải luộc, 1 bát cơm"),
            MealEntity(name = "Mực dồn thịt sốt cà chua", goal = goal, mealType = "Tối", calories = 490, prepTimeMinutes = 25, description = "Mực tươi nhồi thịt bằm sốt cà chua đỏ mọng.", ingredients = "Mực ống 120g, thịt nạc bằm 60g, cà chua, cơm"),
            MealEntity(name = "Canh khoai mỡ thịt băm & cơm", goal = goal, mealType = "Tối", calories = 450, prepTimeMinutes = 20, description = "Canh khoai mỡ tím dẻo thơm dịu mát.", ingredients = "Khoai mỡ 150g, thịt heo băm 50g, ngò ôm, cơm"),
            MealEntity(name = "Tôm rim mặn ngọt bún tươi", goal = goal, mealType = "Tối", calories = 480, prepTimeMinutes = 20, description = "Tôm rim bóc vỏ vỏ giòn mặn ngọt cuộn bún.", ingredients = "Tôm tươi 120g, bún tươi 120g, rau sống"),
            MealEntity(name = "Bò nhúng dấm rau rừng cuốn bánh tráng", goal = goal, mealType = "Tối", calories = 500, prepTimeMinutes = 20, description = "Bò nhúng dấm chua ngọt ăn kèm rau rừng thanh vị.", ingredients = "Bắp bò 120g, nước dấm dừa, bánh tráng, rau sống"),

            // ---- 10 Phụ ----
            MealEntity(name = "Chè đậu đen cốt dừa ít đường", goal = goal, mealType = "Phụ", calories = 220, prepTimeMinutes = 10, description = "Món ăn vặt thanh nhiệt, bổ sung năng lượng buổi chiều.", ingredients = "Đậu đen 100g, nước cốt dừa 30ml, đường phèn"),
            MealEntity(name = "Bánh flan truyền thống", goal = goal, mealType = "Phụ", calories = 210, prepTimeMinutes = 15, description = "Món tráng miệng ngọt ngào từ trứng sữa thơm béo.", ingredients = "Trứng gà, sữa đặc, sữa tươi, caramel"),
            MealEntity(name = "Sinh tố xoài chín nguyên chất", goal = goal, mealType = "Phụ", calories = 200, prepTimeMinutes = 5, description = "Giải khát giàu vitamin C và năng lượng tự nhiên.", ingredients = "Xoài chín 150g, sữa tươi 100ml"),
            MealEntity(name = "Chè hạt sen nhãn Hưng Yên", goal = goal, mealType = "Phụ", calories = 180, prepTimeMinutes = 15, description = "Chè hạt sen an thần giúp ngủ ngon.", ingredients = "Hạt sen tươi 60g, long nhãn 30g, đường thốt nốt"),
            MealEntity(name = "Sữa chua nếp cẩm", goal = goal, mealType = "Phụ", calories = 230, prepTimeMinutes = 5, description = "Sữa chua dẻo sánh kết hợp nếp cẩm bùi thơm.", ingredients = "Sữa chua 100g, nếp cẩm nấu dẻo 50g"),
            MealEntity(name = "Bánh chuối hấp nước cốt dừa", goal = goal, mealType = "Phụ", calories = 240, prepTimeMinutes = 15, description = "Bánh chuối dẻo thơm nước cốt dừa béo ngậy.", ingredients = "Chuối chín, bột năng, nước cốt dừa, vừng rang"),
            MealEntity(name = "Nước dừa tươi quả xiêm", goal = goal, mealType = "Phụ", calories = 150, prepTimeMinutes = 2, description = "Nước dừa tự nhiên bổ sung điện giải cực tốt.", ingredients = "Nước dừa tươi 300ml, cơm dừa nạo"),
            MealEntity(name = "Sinh tố bơ sữa tươi ít đường", goal = goal, mealType = "Phụ", calories = 250, prepTimeMinutes = 5, description = "Sinh tố bơ dẻo mịn béo thơm.", ingredients = "Bơ sáp 100g, sữa tươi 100ml, đá xay"),
            MealEntity(name = "Chè thạch sương sáo hạt chia", goal = goal, mealType = "Phụ", calories = 170, prepTimeMinutes = 5, description = "Thạch sương sáo đen mát lạnh thanh lọc cơ thể.", ingredients = "Sương sáo 100g, hạt chia 10g, nước đường thanh"),
            MealEntity(name = "Bánh bò hấp thốt nốt", goal = goal, mealType = "Phụ", calories = 220, prepTimeMinutes = 10, description = "Bánh bò xốp mềm thơm mùi đường thốt nốt An Giang.", ingredients = "Bánh bò thốt nốt 100g, dừa nạo")
        )
    }
}
