package com.example.fitcheck.data

import com.example.fitcheck.data.entity.MealEntity

object SeedMealsGain {
    fun getMeals(): List<MealEntity> {
        val goal = SeedData.GOAL_GAIN
        return listOf(
            // ---- 12 Sáng ----
            MealEntity(name = "Bánh mì bò kho đặc ruột", goal = goal, mealType = "Sáng", calories = 580, prepTimeMinutes = 35, description = "Bữa sáng giàu đạm và calo giúp tăng cân hiệu quả.", ingredients = "Bánh mì 2 ổ, nạm bò 180g, cà rốt, sả"),
            MealEntity(name = "Xôi mặn thập cẩm lạp xưởng", goal = goal, mealType = "Sáng", calories = 620, prepTimeMinutes = 20, description = "Năng lượng dồi dào từ nếp và topping giàu dinh dưỡng.", ingredients = "Xôi nếp 200g, lạp xưởng 50g, chà bông 30g, trứng cút"),
            MealEntity(name = "Bánh cuốn nhân thịt chả lụa", goal = goal, mealType = "Sáng", calories = 550, prepTimeMinutes = 25, description = "Món ăn mềm mịn cung cấp lượng calo cao.", ingredients = "Bánh cuốn 250g, thịt băm, chả lụa 70g, hành phi"),
            MealEntity(name = "Phở bắp bò gân béo", goal = goal, mealType = "Sáng", calories = 600, prepTimeMinutes = 25, description = "Tô phở gân bò thơm béo dồi dào chất đạm.", ingredients = "Bánh phở 150g, bắp bò 120g, gân bò 50g, nước dùng béo"),
            MealEntity(name = "Bún bò Huế giò heo chả giòn", goal = goal, mealType = "Sáng", calories = 630, prepTimeMinutes = 25, description = "Tô bún bò huế đậm đà thêm khoanh giò heo béo ngậy.", ingredients = "Bún to 150g, giò heo 120g, chả cua, bắp bò"),
            MealEntity(name = "Bánh mì xíu mại trứng muối", goal = goal, mealType = "Sáng", calories = 560, prepTimeMinutes = 15, description = "Xíu mại băm béo bùi kẹp bánh mì rưới sốt cay.", ingredients = "Bánh mì 2 ổ, xíu mại heo 120g, trứng muối 1 quả"),
            MealEntity(name = "Xôi xéo hành phi chà bông mỡ hành", goal = goal, mealType = "Sáng", calories = 590, prepTimeMinutes = 15, description = "Xôi dẻo thơm ngậy mỡ hành đậu xanh bùi.", ingredients = "Nếp dẻo 180g, đậu xanh 50g, chà bông, mỡ hành"),
            MealEntity(name = "Bún riêu giò heo đầy đủ topping", goal = goal, mealType = "Sáng", calories = 610, prepTimeMinutes = 25, description = "Bún riêu cua thơm nức giò heo huyết huyết tô lớn.", ingredients = "Bún 150g, khoanh giò 120g, riêu cua, ốc"),
            MealEntity(name = "Hủ tiếu mì tôm mực bún chả", goal = goal, mealType = "Sáng", calories = 620, prepTimeMinutes = 20, description = "Tô hủ tiếu hải sản tô lớn nạp calo dồi dào.", ingredients = "Hủ tiếu mì 150g, tôm sú 80g, mực 80g, chả"),
            MealEntity(name = "Bánh chưng rán kẹp giò thủ", goal = goal, mealType = "Sáng", calories = 640, prepTimeMinutes = 15, description = "Bánh chưng rán giòn rụm béo bùi đậm đà.", ingredients = "Bánh chưng 150g, giò thủ 60g, dưa hành"),
            MealEntity(name = "Bánh canh mọc cua giò heo", goal = goal, mealType = "Sáng", calories = 600, prepTimeMinutes = 25, description = "Bánh canh bột lọc dai dẻo nước lèo sệt ngọt.", ingredients = "Bánh canh 150g, giò heo 100g, mọc cua 60g"),
            MealEntity(name = "Miến gà đùi chặt cay", goal = goal, mealType = "Sáng", calories = 570, prepTimeMinutes = 20, description = "Miến gà nước lèo béo thơm thịt gà ngon.", ingredients = "Miến dong 120g, đùi gà chặt 150g, lòng gà"),

            // ---- 12 Trưa ----
            MealEntity(name = "Cơm gà chiên mắm bơ tỏi", goal = goal, mealType = "Trưa", calories = 780, prepTimeMinutes = 35, description = "Gà chiên giòn thơm bơ tỏi kích thích vị giác tối đa.", ingredients = "Cơm trắng 200g, đùi gà góc tư 200g, bơ lạt, nước mắm tỏi"),
            MealEntity(name = "Bò bít tết khoai tây chiên", goal = goal, mealType = "Trưa", calories = 760, prepTimeMinutes = 30, description = "Bữa trưa phong phú protein và chất béo xây dựng cơ bắp.", ingredients = "Thịt bò thăn 200g, khoai tây chiên 120g, sốt tiêu đen"),
            MealEntity(name = "Cơm sườn bì chả đặc biệt", goal = goal, mealType = "Trưa", calories = 800, prepTimeMinutes = 30, description = "Phiên bản đầy đủ topping nạp lượng lớn calo.", ingredients = "Cơm trắng 200g, sườn nướng 120g, bì 40g, chả trứng 50g"),
            MealEntity(name = "Cơm thịt heo quay giòn rụm & trứng kho", goal = goal, mealType = "Trưa", calories = 750, prepTimeMinutes = 25, description = "Thịt quay giòn béo ngậy kèm trứng kho mặn ngọt.", ingredients = "Cơm 200g, thịt heo quay 150g, trứng kho 1 quả"),
            MealEntity(name = "Bún mắm miền Tây tôm mực thịt quay", goal = goal, mealType = "Trưa", calories = 720, prepTimeMinutes = 30, description = "Tô bún mắm đậm đà thơm lừng hải sản miền Tây.", ingredients = "Bún 150g, mắm cá linh, tôm 80g, mực 80g, heo quay"),
            MealEntity(name = "Cơm xào bò lúc lắc khoai tây chiên", goal = goal, mealType = "Trưa", calories = 790, prepTimeMinutes = 25, description = "Cơm bò lúc lắc khoai chiên đậm vị xốt.", ingredients = "Cơm chiên 180g, bò lúc lắc 180g, khoai tây 100g"),
            MealEntity(name = "Cơm cá lóc chiên xù xốt chua ngọt", goal = goal, mealType = "Trưa", calories = 740, prepTimeMinutes = 30, description = "Cá lóc chiên giòn rụm xốt chua ngọt đưa cơm.", ingredients = "Cơm 200g, cá lóc chiên 180g, xốt chua ngọt"),
            MealEntity(name = "Mì xào giòn hải sản thập cẩm", goal = goal, mealType = "Trưa", calories = 770, prepTimeMinutes = 25, description = "Mì chiên giòn rụm xốt hải sản đậm đà.", ingredients = "Mì giòn 120g, tôm mực cật heo 180g, rau cải"),
            MealEntity(name = "Cơm đùi vịt quay xốt tiêu đen", goal = goal, mealType = "Trưa", calories = 810, prepTimeMinutes = 30, description = "Vịt quay béo ngậy thơm nức xốt tiêu.", ingredients = "Cơm trắng 200g, đùi vịt quay 200g, xốt tiêu"),
            MealEntity(name = "Bún đậu mắm tôm đặc biệt nem chua chiên", goal = goal, mealType = "Trưa", calories = 830, prepTimeMinutes = 25, description = "Mẹt bún đậu đầy đủ nem chua chiên chả cốm béo ngậy.", ingredients = "Bún 180g, nem chua chiên 80g, chả cốm, dồi chiên"),
            MealEntity(name = "Cơm cari gà béo ngậy nước cốt dừa", goal = goal, mealType = "Trưa", calories = 760, prepTimeMinutes = 35, description = "Cari gà béo dẻo thơm lừng cốt dừa khoai khoai.", ingredients = "Cơm 200g, thịt gà 180g, khoai tây, nước cốt dừa 100ml"),
            MealEntity(name = "Cơm chiên dương châu tôm lạp xưởng", goal = goal, mealType = "Trưa", calories = 780, prepTimeMinutes = 20, description = "Cơm chiên hạt vàng ươm nhiều topping đậm vị.", ingredients = "Cơm chiên 200g, lạp xưởng 60g, tôm 60g, trứng, đậu hà lan"),

            // ---- 12 Tối ----
            MealEntity(name = "Lẩu thái hải sản tôm mực", goal = goal, mealType = "Tối", calories = 790, prepTimeMinutes = 40, description = "Bữa tối thị soạn giàu hải sản tươi và mì tôm đậm đà.", ingredients = "Tôm sú 150g, mực 120g, nghêu 100g, mì gói 2 vắt"),
            MealEntity(name = "Gà kho gừng nước cốt dừa", goal = goal, mealType = "Tối", calories = 750, prepTimeMinutes = 30, description = "Thịt gà béo ngậy nước cốt dừa ăn kèm 2 bát cơm.", ingredients = "Thịt gà ta 200g, nước cốt dừa 50ml, gừng, 2 bát cơm"),
            MealEntity(name = "Bò lúc lắc khoai tây chiên bơ", goal = goal, mealType = "Tối", calories = 770, prepTimeMinutes = 25, description = "Thịt bò mềm mọng xào đậm vị kết hợp khoai tây chiên.", ingredients = "Thịt bò thăn 180g, ớt chuông, khoai tây chiên 100g"),
            MealEntity(name = "Cơm đuôi bò hầm đậu ngự", goal = goal, mealType = "Tối", calories = 820, prepTimeMinutes = 45, description = "Đuôi bò hầm mềm dẻo bổ dưỡng đậm đà.", ingredients = "Đuôi bò 200g, đậu ngự 80g, cà rốt, cơm trắng 200g"),
            MealEntity(name = "Cơm sườn cừu áp chảo bơ tỏi", goal = goal, mealType = "Tối", calories = 800, prepTimeMinutes = 30, description = "Sườn cừu thơm ngậy bơ tỏi sang trọng.", ingredients = "Sườn cừu 200g, bơ tỏi, khoai tây, cơm trắng 180g"),
            MealEntity(name = "Lẩu riêu cua bắp bò giò heo", goal = goal, mealType = "Tối", calories = 830, prepTimeMinutes = 35, description = "Lẩu riêu cua chua ngọt bắp bò díp béo.", ingredients = "Bắp bò 150g, giò heo 120g, riêu cua, bún 150g"),
            MealEntity(name = "Cơm thịt kho trứng hai quả", goal = goal, mealType = "Tối", calories = 760, prepTimeMinutes = 30, description = "Thịt kho trứng vị mặn ngọt đậm đà vét sạch cơm.", ingredients = "Thịt ba chỉ 180g, trứng gà kho 2 quả, 2 bát cơm"),
            MealEntity(name = "Mì ý xốt bò bằm phô mai sợi", goal = goal, mealType = "Tối", calories = 780, prepTimeMinutes = 25, description = "Mì ý béo ngậy xốt cà chua bò bằm phủ phô mai.", ingredients = "Mì ý 150g, bò bằm 150g, xốt cà chua, phô mai sợi"),
            MealEntity(name = "Cơm chả giò hải sản chiên xù", goal = goal, mealType = "Tối", calories = 740, prepTimeMinutes = 25, description = "Chả giò chiên xù xốt mayonnaise béo giòn.", ingredients = "Chả giò 150g, cơm trắng 200g, xốt bơ trứng"),
            MealEntity(name = "Lẩu dê hầm thuốc bắc bún tươi", goal = goal, mealType = "Tối", calories = 810, prepTimeMinutes = 40, description = "Thịt dê hầm bổ dưỡng tăng cường sinh lực.", ingredients = "Thịt dê 200g, thuốc bắc, bún tươi 150g, rau khoai"),
            MealEntity(name = "Cơm cá tẩm bột chiên bơ cay", goal = goal, mealType = "Tối", calories = 770, prepTimeMinutes = 25, description = "Cá tẩm bột chiên bơ giòn cay béo thơm.", ingredients = "Phi lê cá 180g, bột chiên giòn, bơ lạt, cơm trắng 200g"),
            MealEntity(name = "Cơm canh sườn heo hầm hạt sen củ cải", goal = goal, mealType = "Tối", calories = 750, prepTimeMinutes = 35, description = "Sườn heo hầm hạt sen củ cải bùi dẻo.", ingredients = "Sườn heo 180g, hạt sen 60g, củ cải, 2 bát cơm"),

            // ---- 10 Phụ ----
            MealEntity(name = "Sinh tố bơ đậu phộng chuối", goal = goal, mealType = "Phụ", calories = 420, prepTimeMinutes = 5, description = "Thức uống giúp tăng cân nhanh chóng cho người gầy.", ingredients = "Bơ 1 quả, chuối 1 quả, bơ đậu phộng 30g, sữa tươi 200ml"),
            MealEntity(name = "Chè chuối nước cốt dừa trân châu", goal = goal, mealType = "Phụ", calories = 380, prepTimeMinutes = 20, description = "Món ngọt béo ngậy cung cấp đường và calo cao.", ingredients = "Chuối sứ 2 quả, bột báng, nước cốt dừa 80ml"),
            MealEntity(name = "Sữa tươi nguyên kem & bánh quy bơ", goal = goal, mealType = "Phụ", calories = 400, prepTimeMinutes = 2, description = "Bữa phụ nhanh gọn, giàu năng lượng và chất béo.", ingredients = "Sữa tươi nguyên kem 300ml, bánh quy bơ 6 cái"),
            MealEntity(name = "Sinh tố bơ dừa sáp đặc biệt", goal = goal, mealType = "Phụ", calories = 450, prepTimeMinutes = 5, description = "Sinh tố dừa sáp dẻo quánh béo ngậy thơm ngon.", ingredients = "Dừa sáp 100g, bơ sáp 100g, sữa đặc, đá xay"),
            MealEntity(name = "Chè bưởi An Giang béo ngậy", goal = goal, mealType = "Phụ", calories = 390, prepTimeMinutes = 20, description = "Chè bưởi giòn giòn sần sật béo nước cốt dừa.", ingredients = "Củi bưởi 80g, đậu xanh 60g, nước cốt dừa 60ml"),
            MealEntity(name = "Bánh mì nướng bơ đường phô mai", goal = goal, mealType = "Phụ", calories = 410, prepTimeMinutes = 10, description = "Bánh mì giòn rụm béo thơm phô mai tan chảy.", ingredients = "Bánh mì 1 ổ, bơ lạt 20g, phô mai lát 2 miếng"),
            MealEntity(name = "Sữa hạt óc chó mác ca cacao béo", goal = goal, mealType = "Phụ", calories = 370, prepTimeMinutes = 5, description = "Sữa hạt giàu calo và dinh dưỡng cao cấp.", ingredients = "Sữa hạt 250ml, bột cacao 15g, mật ong"),
            MealEntity(name = "Bánh su kem phô mai bơ", goal = goal, mealType = "Phụ", calories = 360, prepTimeMinutes = 15, description = "Bánh su kem béo tan ngay trong miệng.", ingredients = "Bánh su kem 3 cái, nhân phô mai bơ"),
            MealEntity(name = "Chè trôi nước đường mật gừng cốt dừa", goal = goal, mealType = "Phụ", calories = 430, prepTimeMinutes = 25, description = "Viên chè trôi nước dẻo quánh nhân đậu xanh.", ingredients = "Bột nếp 100g, đậu xanh 50g, đường thốt nốt, cốt dừa"),
            MealEntity(name = "Sinh tố sầu riêng bơ béo", goal = goal, mealType = "Phụ", calories = 480, prepTimeMinutes = 5, description = "Sinh tố sầu riêng thơm lừng béo đậm.", ingredients = "Cơm sầu riêng 80g, bơ chín 80g, sữa đặc 30ml")
        )
    }
}
