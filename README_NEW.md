# 🏨 Hotel Management System - Group 2

## 📋 Thông tin nhóm
**Nhóm 2 - Object Oriented Programming**
- **Nguyễn Thanh Phong - 24100259**
- **Đặng Đức Kiên - 24100323**
- **Chatgpt 4.1**
- **Claude 4**

## 🎯 Mô tả dự án
Hệ thống quản lý khách sạn được phát triển bằng **Spring Boot 3.3.0** với thiết kế hướng đối tượng hoàn chỉnh, thay thế cho thiết kế data-centric trước đây.

### ✨ Tính năng chính
- ✅ Quản lý phòng khách sạn (Room Management)
- ✅ Quản lý đặt phòng (Reservation Management)
- ✅ Giao diện web thân thiện với Bootstrap 5
- ✅ Cơ sở dữ liệu H2 tích hợp
- ✅ Validation và xử lý lỗi toàn diện

## 🏗️ Kiến trúc hệ thống

### 📊 UML Class Diagram
```
Room (1) ←→ (0..*) Reservation
  ↓                    ↓
RoomType enum    ReservationStatus enum
```

### 🗂️ Cấu trúc thư mục
```
complete/
├── src/main/java/com/g2/hotelm/
│   ├── model/
│   │   ├── Room.java              # Entity Room với quan hệ bidirectional
│   │   ├── Reservation.java       # Entity Reservation với business logic
│   │   ├── RoomType.java          # Enum loại phòng
│   │   └── ReservationStatus.java # Enum trạng thái đặt phòng
│   ├── repository/
│   │   ├── RoomRepository.java         # JPA Repository cho Room
│   │   └── ReservationRepository.java  # JPA Repository cho Reservation
│   ├── service/
│   │   ├── RoomService.java           # Business logic cho Room
│   │   └── ReservationService.java    # Business logic cho Reservation
│   ├── controller/
│   │   ├── RoomController.java        # REST endpoints cho Room
│   │   ├── ReservationController.java # REST endpoints cho Reservation
│   │   └── HomeController.java        # Trang chủ và điều hướng
│   └── HotelManagementApplication.java # Spring Boot main class
├── src/main/resources/
│   ├── templates/                     # Thymeleaf templates
│   ├── static/                        # CSS, JS, assets
│   └── application.properties         # Cấu hình ứng dụng
└── src/test/java/                     # Unit tests
```

## 🚀 Công nghệ sử dụng

### Backend
- **Spring Boot 3.3.0** - Framework chính
- **Spring Data JPA** - ORM và repository pattern
- **Hibernate 6.5.2** - ORM implementation
- **H2 Database** - Cơ sở dữ liệu nhúng
- **Spring Web MVC** - Web framework
- **Thymeleaf** - Template engine

### Frontend
- **Bootstrap 5.1.3** - UI framework
- **HTML5/CSS3** - Markup và styling
- **JavaScript** - Client-side interactions

### Development Tools
- **Gradle 8.7** - Build tool
- **Spring Boot DevTools** - Hot reload
- **Java 21** - Programming language

## 🔧 Cài đặt và chạy

### Yêu cầu hệ thống
- Java 21+
- Gradle 8.7+

### Các bước chạy ứng dụng

1. **Clone repository**
```bash
git clone https://github.com/nguyenthanhphong05/OOP_N02_T3_2_2025_Group2.git
cd OOP_N02_T3_2_2025_Group2/complete
```

2. **Chạy ứng dụng**
```bash
# Windows
.\gradlew bootRun

# Linux/Mac
./gradlew bootRun
```

3. **Truy cập ứng dụng**
- Web Interface: http://localhost:8080
- H2 Database Console: http://localhost:8080/h2-console

### Database Configuration
```properties
# H2 Database (file-based)
spring.datasource.url=jdbc:h2:file:./data/hoteldb
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 📱 API Endpoints

### Room Management
```http
GET    /rooms              # Danh sách phòng
GET    /rooms/{id}         # Chi tiết phòng
POST   /rooms              # Tạo phòng mới
PUT    /rooms/{id}         # Cập nhật phòng
DELETE /rooms/{id}         # Xóa phòng
GET    /rooms/available    # Phòng trống
```

### Reservation Management
```http
GET    /reservations           # Danh sách đặt phòng
GET    /reservations/{id}      # Chi tiết đặt phòng
POST   /reservations           # Tạo đặt phòng mới
PUT    /reservations/{id}      # Cập nhật đặt phòng
DELETE /reservations/{id}      # Hủy đặt phòng
POST   /reservations/{id}/confirm   # Xác nhận đặt phòng
POST   /reservations/{id}/checkin   # Check-in
POST   /reservations/{id}/checkout  # Check-out
```

## 🎨 Thiết kế OOP

### Core Principles Applied
1. **Encapsulation** - Dữ liệu được đóng gói trong các entity
2. **Inheritance** - Sử dụng enum và interface
3. **Polymorphism** - Service layer abstractions
4. **Abstraction** - Repository pattern và service layer

### Design Patterns
- **Repository Pattern** - Data access abstraction
- **Service Layer Pattern** - Business logic separation
- **MVC Pattern** - Web layer organization
- **Builder Pattern** - Entity construction
- **Strategy Pattern** - Validation strategies

## 🧪 Testing

### Unit Tests
```bash
# Chạy tất cả tests
.\gradlew test

# Chạy test specific class
.\gradlew test --tests "RoomServiceTest"
```

### Test Coverage
- Model layer: Business logic và validation
- Service layer: Core business operations
- Repository layer: Data access methods
- Controller layer: REST endpoints

## 📈 Tính năng nâng cao

### Business Logic
- **Room availability checking** - Kiểm tra phòng trống theo khoảng thời gian
- **Automatic pricing calculation** - Tính giá tự động theo loại phòng và thời gian
- **Reservation state management** - Quản lý trạng thái đặt phòng
- **Conflict detection** - Phát hiện xung đột lịch đặt phòng

### Data Integrity
- **Bidirectional relationships** - Đồng bộ dữ liệu hai chiều
- **Cascade operations** - Tự động xử lý các thao tác liên quan
- **Validation constraints** - Kiểm tra tính hợp lệ dữ liệu
- **Transaction management** - Đảm bảo tính nhất quán

## 🔄 Quá trình phát triển

### Version 1.0 (Branch: old)
- Thiết kế data-centric
- 3 models: Room, Customer, Booking
- Quan hệ foreign key đơn giản

### Version 2.0 (Branch: v2) - Current
- Thiết kế object-oriented hoàn chỉnh
- 2 models chính: Room, Reservation
- Quan hệ bidirectional JPA
- Business logic methods
- Enum types cho type safety

## 🤝 Phân chia công việc

### Nguyễn Thanh Phong - 24100259
- ✅ Thiết kế và triển khai Model Room
- ✅ Thiết kế cơ sở dữ liệu H2
- ✅ Triển khai model Reservation với business logic
- ✅ Thiết kế và phát triển Frontend Bootstrap
- ✅ Viết tài liệu UML và documentation
- ✅ Project construction và workflow design

### Đặng Đức Kiên - 24100323
- ✅ Triển khai và hoàn thiện Model Booking/Reservation
- ✅ Phát triển Services layer cho business logic
- ✅ Viết Unit tests cho các Model
- ✅ Thiết kế kế hoạch kiểm thử hệ thống
- ✅ Thiết kế Frontend với Bootstrap integration

## 📝 Change Log

### Version 2.0.0 (August 2025)
- ✅ Refactored từ data-centric sang object-oriented design
- ✅ Implemented bidirectional JPA relationships
- ✅ Added business logic methods to entities
- ✅ Created comprehensive service layer
- ✅ Enhanced frontend with Bootstrap 5
- ✅ Added enum types for type safety
- ✅ Implemented validation và error handling
- ✅ Added automatic commit scheduling

### Version 1.0.0 (Initial)
- ✅ Basic Spring Boot setup
- ✅ H2 Database integration
- ✅ Simple CRUD operations
- ✅ Basic web interface

## 🎯 Future Enhancements
- [ ] User authentication và authorization
- [ ] Email notifications cho reservations
- [ ] Payment integration
- [ ] Reporting và analytics
- [ ] Mobile responsive improvements
- [ ] API documentation với Swagger
- [ ] Caching với Redis
- [ ] Microservices architecture

## 📞 Liên hệ
- **Repository**: https://github.com/nguyenthanhphong05/OOP_N02_T3_2_2025_Group2
- **Branch hiện tại**: v2
- **Branch cũ**: old (lưu trữ version 1.0)

---
*Developed with ❤️ by Group 2 - OOP Course 2025*
