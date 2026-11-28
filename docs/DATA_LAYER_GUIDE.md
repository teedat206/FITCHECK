# Hướng dẫn sử dụng Tầng Data (Data Layer Guide) cho ViewModel & UI

Tài liệu này hướng dẫn cách kết nối và sử dụng các Repository và DAO trong tầng `data/` cho các lập trình viên phụ trách `viewmodel/` và `ui/`.

## 1. Khởi tạo Repository từ AppDatabase
Mọi truy cập cơ sở dữ liệu đều đi qua `AppDatabase`. Sử dụng `getInstance(context)`:
```kotlin
val db = AppDatabase.getInstance(application)
val authRepo = AuthRepository(db.userDao())
val profileRepo = ProfileRepository(db.bodyProfileDao(), db.userDao())
val mealRepo = MealRepository(db.mealDao())
val workoutRepo = WorkoutRepository(db.workoutDao())
val weightRepo = WeightRepository(db.weightDao(), userId)
val reminderRepo = ReminderRepository(db.reminderDao())
```
Hoặc gọi hàm alias `AppDatabase.getDatabase(context)`.

## 2. Quy ước `userId`
- Mọi dữ liệu cá nhân (Cân nặng, Hồ sơ cơ thể, Nhắc nhở) bắt buộc phải gắn với `userId` của người dùng hiện tại lấy từ `AppPreferences`.
- Các bảng dùng chung (`meals`, `workouts`) không cần `userId`.

## 3. Các hàm Suspend vs Flow / LiveData
- **Suspend functions**: Phải gọi bên trong coroutine scope (ví dụ: `viewModelScope.launch(Dispatchers.IO) { ... }`). Ví dụ: `authRepo.register(...)`, `authRepo.login(...)`, `weightRepo.insert(...)`, `profileRepo.save(...)`.
- **Flow / LiveData**: Quan sát realtime dữ liệu từ Room Database. Ví dụ: `weightRepo.allWeights`, `mealRepo.search(...)`, `workoutRepo.search(...)`.

## 4. Bảng chuỗi hợp lệ (String Constants)
- **Giới tính (`gender`)**: `"Nam"`, `"Nữ"`
- **Mức độ vận động (`activity`)**: `"Ít"`, `"Vừa"`, `"Nhiều"`
- **Mục tiêu hồ sơ (`goal` trong BodyProfile)**: `"GIAM_CAN"`, `"GIU_CAN"`, `"TANG_CAN"`
- **Mục tiêu món ăn (`goal` trong MealEntity)**: `"Giảm cân"`, `"Giữ cân"`, `"Tăng cân"`
  *(Có sẵn hàm chuyển đổi trong ProfileRepository: `ProfileRepository.mapProfileGoalToMealGoal(profileGoal)`)*
- **Loại bữa ăn (`mealType`)**: `"Sáng"`, `"Trưa"`, `"Tối"`, `"Phụ"`
- **Cường độ bài tập (`intensity`)**: `"Nhẹ"`, `"Vừa"`, `"Cao"`
- **Loại nhắc nhở (`type` trong ReminderEntity)**: `"WEIGHT"`, `"MEAL"`, `"WORKOUT"`

## 5. Lưu ý quan trọng
- Ứng dụng chạy hoàn toàn **OFFLINE**, Room (`fitcheck_database`) là nguồn dữ liệu duy nhất.
- Không sửa đổi trực tiếp các file trong `data/` nếu không cần thiết; nếu cần hàm mới, hãy thảo luận hoặc bổ sung overloaded method an toàn.
