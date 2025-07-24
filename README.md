## [Group 2] 
Nguyễn Thanh Phong MSV: 24100259

Đặng Đức Kiên MSV: 24100323

# Hotel Management System - 

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
**Class:** `AutoCheckout.java`
**Purpose:** Automatically process daily checkouts when checkout time has passed
**Methods:**
- `processAutoCheckout()` - Automatically checkout expired reservations
- `getTodaysCheckouts()` - Get all reservations ending today
- `getDailyOperationsSummary()` - Generate daily operations summary

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
│  Autocheckout.java
├── utils/DateParser.java            # Auto fixing date format input           
└── test/                           # Test classes

```

## UML and stuffs
*uml not found....*
*mercy pls*

## Models

### Room
- Room number, type (Deluxe, Standard, Suite, etc.) Proc
- Room pricepPerNight (Int) #new - just added
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


### To Do Next?
- Adding Abstract class and interface to the project

---
*everything was logged in "change log folder" check it out 


