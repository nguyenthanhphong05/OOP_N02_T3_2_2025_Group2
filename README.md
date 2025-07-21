## [Group 2] 
Nguyễn Thanh Phong MSV: 24100259

Đặng Đức Kiên MSV: 24100323

# Hotel Management System - 

## 3 Main Operational Methods (3 Phương thức hoạt động chính)

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

## Project Structure

```
src/
├── App.java                           # Main application entry point
├── model/                            # Data models (Entity classes)
│   ├── Customer.java                # Customer entity
│   ├── Reservation.java             # Reservation entity
│   └── Room.java                    # Room entity
├── service/                         # Business logic services
│   ├── HotelM.java                  # Hotel management CRUD operations
│   ├── AvailableRoomsDisplay.java   
│   ├── ReservationSearch.java      
│   └── CustomerCheckout.java
├── utils/DateParser.java            # Auto fixing date format input           
└── test/                           # Test classes
    ├── TestAvailableRoomsDisplay.java  # Test for Method 1
    ├── TestReservationSearch.java     # Test for Method 2
    ├── TestCustomerCheckout.java      # Test for Method 3
    ├── TestRoom.java                  # Room entity tests
    ├── TestCustomer.java             # Customer entity tests
    └── CRUDTest.java                # CRUD operation tests
```

## UML and stuffs
*uml not found....*
*mercy pls*

## Models

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



---
*everything was logged in "change log folder" check it out 
