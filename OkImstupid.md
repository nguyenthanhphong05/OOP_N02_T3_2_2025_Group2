# TÀI LIỆU GHI LẠI CÁC VẤN ĐỀ VÀ CÁCH KHẮC PHỤC
## Dự án: Hotel Management System - OOP_N02_T3_2_2025_Group2

---

## TỔNG QUAN DỰ ÁN
- **Tên dự án**: Hotel Management System
- **Framework**: Spring Boot 3.3.0
- **Database**: H2 Database
- **Frontend**: Thymeleaf + Bootstrap 5
- **Build Tool**: Maven (Gradle no dependancy config)

---

## CÁC VẤN ĐỀ ĐÃ GẶP PHẢI

### 1. VẤN ĐỀ VỚI IMPORTS SAI
**Mô tả**: Trong file model, ban đầu sử dụng import sai (JPA) 
            - Và sai build tool (Noted in 6.)


### 2. XUNG ĐỘT CONTROLLER MAPPING
**Mô tả**: Lỗi khi có 2 controller cùng map tới path "/"

**Bài học**: Chỉ nên có 1 controller handle root path "/"


### 3. VẤN ĐỀ VỚI METHOD REFERENCE TRONG CONTROLLER
**Mô tả**: Lỗi compilation khi sử dụng method reference sai

**Bài học**: Kiểm tra tên method trong model class trước khi sử dụng method reference
            - Tận dụng Outline windows.

---

### 4. DATABASE LOCK ISSUE
**Mô tả**: Lỗi database bị lock khi chuyển từ in-memory sang file-based

**Nguyên nhân**: 
- File database đã tồn tại và đang được sử dụng
- Có thể do instance khác đang chạy

**Cấu hình database**:
```properties
# File-based H2 Database
spring.datasource.url=jdbc:h2:file:./data/hoteldb
spring.jpa.hibernate.ddl-auto=update
```

---

### 5. THYMELEAF TEMPLATE SYNTAX ERROR
**Mô tả**: Lỗi trong Thymeleaf expression khi sử dụng JavaScript confirm

### 6. VSC BUILD CONFIG 
**Mô tả**: Vì framework dùng cả gradle/mvn - vsc sẽ chọn bất kì 1 compiler (Trường hợp này là Gradle), việc thêm dependency vào pom sẽ không có tác dụng khi test class gọi gradle.

---

### 7. HIBERNATE DIALECT WARNING
**Mô tả**: Warning về dialect không cần thiết
```
HHH90000025: H2Dialect does not need to be specified explicitly
```

**Cách khắc phục**: Xóa dòng sau khỏi `application.properties`
```properties
# KHÔNG CẦN THIẾT với Spring Boot 3.x
# spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

**Lý do**: Spring Boot 3.x tự động detect H2 dialect

---

## CÁC BEST PRACTICES ĐÃ HỌC ĐƯỢC

### 1. Model Class Structure
- Luôn implement `equals()`, `hashCode()`, và `toString()`
- Sử dụng proper JPA annotations
- Validation annotations cho data integrity

### 2. Controller Design
- Tách biệt concerns: 1 controller cho 1 entity
- Sử dụng proper HTTP methods (GET/POST)
- Handle validation errors gracefully

### 3. Database Configuration
- File-based database cho persistence
- `ddl-auto=update` cho development
- Proper connection pooling

### 4. Frontend Template
- Giữ template đơn giản và dễ maintain
- Sử dụng Bootstrap cho responsive design
- Proper form validation

---

## LESSONS LEARNED

### 1. Import Statements
- **Vấn đề**: Typo trong package names
- **Giải pháp**: Double-check imports, sử dụng IDE auto-completion

### 2. Method References
- **Vấn đề**: Sử dụng method reference không tồn tại
- **Giải pháp**: Kiểm tra method signature trong model class

### 3. Database Management
- **Vấn đề**: File locks và connection conflicts
- **Giải pháp**: Clean shutdown, proper configuration

### 4. Template Engine
- **Vấn đề**: Mixing server-side và client-side code
- **Giải pháp**: Proper escaping và syntax

---

## 🔧 TROUBLESHOOTING CHECKLIST

### Khi gặp compilation errors:
1. Kiểm tra imports
2. Verify method names
3. Check syntax errors

### Khi gặp runtime errors:
1. Check database connections
2. Verify controller mappings
3. Check template syntax

### Khi gặp deployment issues:
1. Clean build directory
2. Restart application
3. Check port conflicts


### Database Access:
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:file:./data/hoteldb`
- **Username**: `sa`
- **Password**: (để trống)

### Useful File Paths:
- **Main Application**: `src/main/java/com/g2/hotelm/HotelManagementApplication.java`
- **Models**: `src/main/java/com/g2/hotelm/model/`
- **Controllers**: `src/main/java/com/g2/hotelm/controller/`
- **Templates**: `src/main/resources/templates/`
- **Configuration**: `src/main/resources/application.properties`

## Unformated 
Missing no-argument constructor - JPA entities need a default constructor
Missing setEndDate implementation - the method is empty
Duplicate equals condition - there are two if (this == o) return true;

---

## Overwrite issue (Những lỗi fix source của người khác)
- Customer name uniqueness
The customerName field has unique = true, which means we can't have two reservations with the same customer name. This doesn't make sense for a hotel system where the same customer should be able to make multiple reservations.
 -> customerName field has unique = false

**Tài liệu này được cập nhật lần cuối**: 12/08/2025
**Tác giả**: Team Group 2 - OOP N02 T3 2025
*Debug - Tests Generate with Copilot*
