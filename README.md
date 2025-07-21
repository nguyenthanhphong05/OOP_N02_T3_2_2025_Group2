# Hotel Management System - OOP Practice 5
## Polymorphism and Object-Oriented Programming Concepts

### Course: Object Oriented Programming (CSE703029)
### Practice 5: Polymorphism Implementation

A comprehensive Java-based hotel management system demonstrating advanced object-oriented programming principles with proper separation of concerns and polymorphic behavior.

## 🎯 Assignment Overview
This project demonstrates the implementation of Object-Oriented Programming concepts including:
- **Polymorphism** (Đa hình)
- **Access Modifiers** (Truy cập)
- **Reuse** (Tái sử dụng)
  - Composition (has-a relationship)
  - Inheritance (is-a relationship)
- **Reference** (Tham chiếu)
- **Interface**
- **Package Structure**

## 🏨 3 Main Operational Methods (3 Phương thức hoạt động chính)

### 1. Available Rooms Display (Hiển thị phòng trống)
**Class:** `AvailableRoomsDisplay.java`
**Purpose:** Display available rooms for the current date and filter by room type
**Methods:**
- `displayAvailableRooms(List<Room> rooms)` - Shows all available rooms
- `displayAvailableRoomsByType(List<Room> rooms, String roomType)` - Filter by room type

### 2. Reservation Search (Tìm kiếm đặt phòng)
**Class:** `ReservationSearch.java`
**Purpose:** Search reservations using stream operations
**Methods:**
- `searchReservationsByCustomerID(List<Reservation> reservations, String customerID)` - Search by customer ID
- `searchReservationsByCustomerName(List<Reservation> reservations, String customerName)` - Search by customer name
- `getActiveReservations(List<Reservation> reservations)` - Get current active reservations

### 3. Customer Checkout (Checkout khách hàng)
**Class:** `CustomerCheckout.java`
**Purpose:** Process customer checkout for the current day
**Methods:**
- `processCheckoutToday(List<Reservation> reservations)` - Process all checkouts for today
- `getEligibleCheckouts(List<Reservation> reservations)` - Get customers eligible for checkout
- `checkoutByReservationID(List<Reservation> reservations, String reservationID)` - Individual checkout

## 📁 Project Structure

```
src/
├── App.java                           # Main application entry point
├── model/                            # Data models (Entity classes)
│   ├── Customer.java                # Customer entity
│   ├── Reservation.java             # Reservation entity
│   └── Room.java                    # Room entity
├── service/                         # Business logic services
│   ├── HotelM.java                  # Hotel management CRUD operations
│   ├── AvailableRoomsDisplay.java   # Method 1: Room availability
│   ├── ReservationSearch.java       # Method 2: Search functionality
│   └── CustomerCheckout.java        # Method 3: Checkout process
└── test/                           # Test classes
    ├── TestAvailableRoomsDisplay.java  # Test for Method 1
    ├── TestReservationSearch.java     # Test for Method 2
    ├── TestCustomerCheckout.java      # Test for Method 3
    ├── TestRoom.java                  # Room entity tests
    ├── TestCustomer.java             # Customer entity tests
    └── CRUDTest.java                # CRUD operation tests
```

## 🚀 How to Run

### Compile the project:
```bash
javac -d bin -cp src src/model/*.java src/test/*.java src/service/*.java src/*.java
```

### Run the main application (tests all 3 methods):
```bash
java -cp bin App
```

### Run individual tests:
```bash
# Test Method 1: Available Rooms Display
java -cp bin test.TestAvailableRoomsDisplay

# Test Method 2: Reservation Search
java -cp bin test.TestReservationSearch

# Test Method 3: Customer Checkout
java -cp bin test.TestCustomerCheckout
```

### Using VS Code Tasks:
- **Compile:** Run "Compile Java" task
- **Test:** Run individual test tasks from the command palette

## 💡 Features

### Core Hotel Management
- **Room Management**: Create and manage hotel rooms with room numbers, types, and availability
- **Customer Management**: Store customer information (ID, name, email, phone)
- **Reservation System**: Book rooms for customers with check-in/check-out dates
- **Hotel Service**: Centralized CRUD operations for all entities

### Advanced Operations
- **Real-time Room Availability**: Display current available rooms with date filtering
- **Stream-based Search**: Efficient reservation searching using Java 8+ streams
- **Automated Checkout Processing**: Handle customer checkouts with room status updates
- **Date-based Operations**: Work with current date for daily operations

## 📋 Models

### Room
- Room number, type (Deluxe, Standard, Suite, etc.)
- Availability status (boolean)
- Room information display with formatting

### Customer
- Customer ID, name, email, phone number
- Customer information display
- Customer validation and management

### Reservation
- Reservation ID linking customers to rooms
- Check-in and check-out dates using LocalDateTime
- Complete reservation information display
- Date range validation

## 🏆 Sample Output
The application demonstrates:
1. **Polymorphism** through method overloading and interface implementations
2. **Encapsulation** with private fields and public getter/setter methods
3. **Composition** with Room, Customer, and Reservation relationships
4. **Stream operations** for efficient data filtering and searching
5. **Date/Time operations** for checkout processing

## ✅ Assignment Completion
This project fulfills the requirements for **OOP Practice 5**:
- [x] **Code 1:** Created 3 operational classes in SRC folder
- [x] **Code 2:** Created test classes in TEST folder
- [x] **Code 3:** Updated App.java to call all tests
- [x] **Code 4:** Ready for Git commit to group repository

## 👥 Group Members
- **Method 1 (Available Rooms Display):** [Student Name 1]
- **Method 2 (Reservation Search):** [Student Name 2] 
- **Method 3 (Customer Checkout):** [Student Name 3]

## 🛠️ Technologies Used
- **Language:** Java 8+
- **IDE:** Visual Studio Code
- **Build System:** Manual compilation with javac
- **Testing:** Custom test classes with main() methods
- **Version Control:** Git

## 🔧 Future Enhancements
- Work with file persistence (txt/JSON)
- Enhanced UI (Swing/Console)
- Better date/time formatting with auto-parsing
- Database integration
- Web interface
- Advanced reporting features

---
**Note:** This project demonstrates practical implementation of OOP concepts as required for CSE703029 Practice 5 assignment.