# 📋 API Documentation - Hotel Management System

## Base URL
```
http://localhost:8080
```

## 🏠 Room Management API

### 1. Get All Rooms
```http
GET /rooms
```

**Response:**
```json
[
  {
    "roomId": "101",
    "type": "SINGLE",
    "pricePerNight": 50.0,
    "maxOccupancy": 1,
    "description": "Single room with city view",
    "reservations": []
  }
]
```

### 2. Get Room by ID
```http
GET /rooms/{roomId}
```

**Parameters:**
- `roomId` (string) - Room identifier

**Response:**
```json
{
  "roomId": "101",
  "type": "SINGLE",
  "pricePerNight": 50.0,
  "maxOccupancy": 1,
  "description": "Single room with city view",
  "reservations": [
    {
      "id": 1,
      "customerName": "John Doe",
      "checkInDate": "2025-08-15",
      "checkOutDate": "2025-08-18",
      "status": "CONFIRMED"
    }
  ]
}
```

### 3. Create New Room
```http
POST /rooms
Content-Type: application/json
```

**Request Body:**
```json
{
  "roomId": "102",
  "type": "DOUBLE",
  "pricePerNight": 80.0,
  "maxOccupancy": 2,
  "description": "Double room with sea view"
}
```

**Response:**
```json
{
  "roomId": "102",
  "type": "DOUBLE",
  "pricePerNight": 80.0,
  "maxOccupancy": 2,
  "description": "Double room with sea view",
  "reservations": []
}
```

### 4. Update Room
```http
PUT /rooms/{roomId}
Content-Type: application/json
```

**Request Body:**
```json
{
  "type": "SUITE",
  "pricePerNight": 150.0,
  "maxOccupancy": 4,
  "description": "Luxury suite with balcony"
}
```

### 5. Delete Room
```http
DELETE /rooms/{roomId}
```

**Response:** `204 No Content`

### 6. Get Available Rooms
```http
GET /rooms/available?checkIn=2025-08-15&checkOut=2025-08-18&type=SINGLE
```

**Query Parameters:**
- `checkIn` (date) - Check-in date (YYYY-MM-DD)
- `checkOut` (date) - Check-out date (YYYY-MM-DD)  
- `type` (string, optional) - Room type filter

## 🎫 Reservation Management API

### 1. Get All Reservations
```http
GET /reservations
```

**Response:**
```json
[
  {
    "id": 1,
    "customerName": "John Doe",
    "checkInDate": "2025-08-15",
    "checkOutDate": "2025-08-18",
    "status": "CONFIRMED",
    "room": {
      "roomId": "101",
      "type": "SINGLE",
      "pricePerNight": 50.0
    },
    "totalPrice": 150.0,
    "durationInDays": 3
  }
]
```

### 2. Get Reservation by ID
```http
GET /reservations/{id}
```

### 3. Create New Reservation
```http
POST /reservations
Content-Type: application/json
```

**Request Body:**
```json
{
  "customerName": "Jane Smith",
  "checkInDate": "2025-08-20",
  "checkOutDate": "2025-08-23",
  "room": {
    "roomId": "102"
  }
}
```

### 4. Update Reservation
```http
PUT /reservations/{id}
Content-Type: application/json
```

### 5. Cancel Reservation
```http
DELETE /reservations/{id}
```

### 6. Confirm Reservation
```http
POST /reservations/{id}/confirm
```

**Response:**
```json
{
  "id": 1,
  "status": "CONFIRMED",
  "message": "Reservation confirmed successfully"
}
```

### 7. Check-in
```http
POST /reservations/{id}/checkin
```

### 8. Check-out
```http
POST /reservations/{id}/checkout
```

### 9. Search Reservations by Customer
```http
GET /reservations/search?customerName=John Doe
```

## 📊 Enums

### Room Type
```
SINGLE - Single Room
DOUBLE - Double Room  
SUITE - Suite
DELUXE - Deluxe Room
Standard - Standard Room (legacy)
Deluxe - Deluxe Legacy (legacy)
```

### Reservation Status
```
PENDING - Đang chờ xác nhận
CONFIRMED - Đã xác nhận
CHECKED_IN - Đã check-in
CHECKED_OUT - Đã check-out
CANCELLED - Đã hủy
```

## ⚠️ Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2025-08-13T14:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Check-in date must be before check-out date",
  "path": "/reservations"
}
```

### 404 Not Found
```json
{
  "timestamp": "2025-08-13T14:30:00.000+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Room with ID 999 not found",
  "path": "/rooms/999"
}
```

### 409 Conflict
```json
{
  "timestamp": "2025-08-13T14:30:00.000+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Room is not available for the specified period",
  "path": "/reservations"
}
```

## 🔍 Testing with curl

### Create a Room
```bash
curl -X POST http://localhost:8080/rooms \
  -H "Content-Type: application/json" \
  -d '{
    "roomId": "201",
    "type": "DOUBLE",
    "pricePerNight": 85.0,
    "maxOccupancy": 2,
    "description": "Ocean view double room"
  }'
```

### Create a Reservation
```bash
curl -X POST http://localhost:8080/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Alice Johnson",
    "checkInDate": "2025-08-25",
    "checkOutDate": "2025-08-28",
    "room": {"roomId": "201"}
  }'
```

### Check Room Availability
```bash
curl "http://localhost:8080/rooms/available?checkIn=2025-08-20&checkOut=2025-08-25&type=DOUBLE"
```

## 📱 Frontend URLs

- **Home**: http://localhost:8080/
- **Room List**: http://localhost:8080/rooms
- **Add Room**: http://localhost:8080/rooms/new
- **Reservation List**: http://localhost:8080/reservations
- **Add Reservation**: http://localhost:8080/reservations/new
- **H2 Console**: http://localhost:8080/h2-console

---
*API Documentation for Hotel Management System v2.0*
