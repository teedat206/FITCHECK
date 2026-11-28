# Bảng Test Cases - Tầng Data (FitCheck)

| ID | Chức năng | Dữ liệu vào | Kết quả mong đợi | Loại (Đúng / Sai / Trống / Biên / Offline) | Kết quả thực tế | Trạng thái |
|---|---|---|---|---|---|---|
| TC_DATA_01 | WeightDao - getAllByUser | Truy vấn theo `userId` hợp lệ | Trả về danh sách `WeightRecord` của đúng user đó, sắp xếp giảm dần theo ID | Đúng / Offline | Khớp dữ liệu | Pass |
| TC_DATA_02 | WeightDao - getBetween | Khoảng ngày `2026-05-01` đến `2026-05-31` (định dạng `dd/MM/yyyy`) | Trả về chính xác các bản ghi trong khoảng ngày (xử lý đảo chuỗi SQL `substr`) | Biên / Đúng | Khớp chính xác | Pass |
| TC_DATA_03 | AuthRepository - Đăng ký hợp lệ | Tên, Email hợp lệ, Mật khẩu >= 6 ký tự | Trả về `AuthResult.Success(userId)`, lưu DB thành công với PBKDF2 hash | Đúng | Thành công | Pass |
| TC_DATA_04 | AuthRepository - Trùng Email | Đăng ký với email đã tồn tại trong DB | Trả về `AuthResult.Error("Email đã tồn tại")` | Sai | Báo lỗi chuẩn | Pass |
| TC_DATA_05 | AuthRepository - Mật khẩu biên | Mật khẩu đúng 6 ký tự / Mật khẩu 5 ký tự | 6 ký tự: Thành công / 5 ký tự: Trả về lỗi "Mật khẩu phải có ít nhất 6 ký tự" | Biên / Sai | Khớp logic | Pass |
| TC_DATA_06 | AuthRepository - Đăng nhập sai | Sai email hoặc sai mật khẩu | Trả về `AuthResult.Error("Sai email hoặc mật khẩu")` (không lộ thông tin) | Sai / Bảo mật | Trả về thông báo chung | Pass |
| TC_DATA_07 | SeedData - Khởi tạo CSDL | CSDL khởi tạo mới / mở lần đầu | Đủ 12 món ăn và 12 bài tập, không bị nhân đôi do seed ở `onOpen` với `count() == 0` | Đúng / Khởi tạo | Đủ 36 món & 12 bài tập | Pass |
| TC_DATA_08 | MealDao - Tìm kiếm & lọc | Từ khóa, mục tiêu, khoảng kcal, thời gian | Lọc kết hợp chính xác, trả về danh sách phù hợp | Đúng / Biên | Khớp kết quả | Pass |
| TC_DATA_09 | WeightRepository - Xuất CSV | Danh sách bản ghi cân nặng có dấu phẩy / dấu ngoặc kép | Chuỗi CSV đúng header `date,session,weight`, escape dấu phẩy và ngoặc kép an toàn | Đúng / Định dạng | CSV hợp lệ | Pass |
| TC_DATA_10 | WeightRepository - Validate cân nặng | Cân nặng < 20kg hoặc > 300kg | Trả về `WeightResult.Error` | Biên / Sai | Chặn thành công | Pass |
