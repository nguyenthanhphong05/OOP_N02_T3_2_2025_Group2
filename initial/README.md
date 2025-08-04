# Hotel Management System

## [Group 2] 
**Nguyễn Thanh Phong** - MSV: 24100259  
**Đặng Đức Kiên** - MSV: 24100323

---

## Overview

A **Hotel Management System** built with **Spring Boot** featuring a web interface for managing rooms and customers with real-time dashboard.


## Project Structure

```
src/
├── controller/          # Web controllers (HomeController, RoomController, CustomerController)
├── model/              # Data models (Room, Customer, Reservation)
├── repository/         # Database access (RoomRepository, CustomerRepository)
├── service/           # Business logic (SimpleFileService, HotelService, etc.)
└── resources/
    ├── static/css/    # CSS files (hotel-theme.css)
    └── templates/     # HTML pages (index.html, rooms/, customers/)
```

## Data Models

**Room**: Room number, type, price, availability status  
**Customer**: Customer ID, name, email, phone  
**Reservation**: Reservation ID, customer, room, dates

## How to Run

```bash
# Clone the repository
git clone https://github.com/nguyenthanhphong05/OOP_N02_T3_2_2025_Group2.git

# Navigate to project
cd OOP_N02_T3_2_2025_Group2/initial

# Run the application
./mvnw spring-boot:run

# Open browser: http://localhost:8080
```

- **Dashboard**: http://localhost:8080/ 
- **Rooms**: http://localhost:8080/rooms
- **Customers**: http://localhost:8080/customers

# Everything was logged in changelog, take a look
*Coded with GitHub Copilot (Claude 4). Engineered by @nguyenthanhphong.*

