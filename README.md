# Hotel Management System (HotelM)

A simple Java-based hotel management system that demonstrates object-oriented programming principles with proper separation of concerns.

## Project Structure

```
src/
├── model/          # Data models (objects)
│   ├── Room.java
│   ├── Customer.java
│   └── Reservation.java
├── service/        # Business logic
│   └── HotelM.java
└── test/          # Test classes
    ├── TestRoom.java
    ├── Reservationtest.java
    └── HotelMTest.java
└── UI/ # Not yet
    ├── UISwing.java
    └── UIconsole.java  
App.java (Main run - has option to choose between 2 UI)    
```

## Features

- **Room Management**: Create and manage hotel rooms with room numbers, types, and availability
- **Customer Management**: Store customer information (ID, name, email, phone)
- **Reservation System**: Book rooms for customers with check-in/check-out dates
- **Hotel Service**: Centralized management of rooms, customers, and reservations

## Models

### Room
- Room number, type (Deluxe, Standard, etc.)
- Availability status (boolean)
- Room information display

### Customer
- Customer ID, name, email, phone number
- Customer information display

### Reservation
- Reservation ID linking customers to rooms
- Check-in and check-out dates using LocalDateTime
- Complete reservation information display

### Work to do next?
- Work with file (maybe txt) 
- Editting logic (for all objects)
- Better timeanddate formating (with auto parsching format?) 
- UI