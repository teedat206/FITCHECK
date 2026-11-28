# Danh sách lỗi (Bug List) - Tầng Data

| Mã lỗi | Mô tả | Cách tái hiện | Mức độ | Người phụ trách | Trạng thái |
|---|---|---|---|---|---|
| **BUG01** | Seed dữ liệu bị nhân đôi (duplicate) ở lần chạy đầu tiên | Khởi chạy app khi cơ sở dữ liệu vừa được tạo, bảng `meals` và `workouts` bị chèn dữ liệu 2 lần do gọi seed ở cả `onCreate` và `onOpen`. | Trung bình | Kỹ sư Data | Đã sửa (chỉ seed tại `onOpen` kèm kiểm tra `count() == 0`) |
| **BUG02** | Xung đột tên bảng và Entity của `WeightRecord` | Lỗi KAPT stub do `WeightDao` trỏ tới bảng `weight_records` trong khi Entity khai báo bảng `weight_table`. | Cao | Kỹ sư Data | Đã sửa (đồng nhất bảng `weight_table`) |
| **BUG03** | Lỗi so sánh khoảng ngày (`between`) cho định dạng `dd/MM/yyyy` | Dùng trực tiếp `BETWEEN` trên chuỗi `dd/MM/yyyy` dẫn đến kết quả sai thứ tự thời gian. | Cao | Kỹ sư Data | Đã sửa (dùng `substr(date,7,4) || substr(date,4,2) || substr(date,1,2)`) |
| **BUG04** | Mật khẩu lưu dạng thô (plain text / MD5 / SHA-256 đơn thuần) | Kiểm tra bảo mật cơ sở dữ liệu Room thấy mật khẩu chưa được bọc muối (salt) và hash chuẩn bảo mật. | Cao | Kỹ sư Data | Đã sửa (áp dụng PBKDF2WithHmacSHA256 với salt ngẫu nhiên 16 byte) |
