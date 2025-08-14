// package com.g2.hotelm.model;

// import java.time.LocalDate;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.Test;

// class ReservationTest {
//     @Test
//     void testConstructorAndGetters() {
//         Room room = new Room("101", RoomType.SINGLE, 50.0, true, "Single room");
//         Reservation reservation = new Reservation(room, "Phong", LocalDate.of(2025,8,14), LocalDate.of(2025,8,16));
//         assertEquals(room, reservation.getRoom());
//         assertEquals("Phong", reservation.getCustomerName());
//         assertEquals(LocalDate.of(2025,8,14), reservation.getCheckInDate());
//         assertEquals(LocalDate.of(2025,8,16), reservation.getCheckOutDate());
//         assertEquals(ReservationStatus.PENDING, reservation.getStatus());
//         assertEquals(100.0, reservation.getTotalPrice());
//     }

//     @Test
//     void testSetters() {
//         Reservation reservation = new Reservation();
//         Room room = new Room("102", RoomType.DOUBLE, 80.0, true, "Double room");
//         reservation.setRoom(room);
//         reservation.setCustomerName("Kien");
//         reservation.setCheckInDate(LocalDate.of(2025,8,15));
//         reservation.setCheckOutDate(LocalDate.of(2025,8,17));
//         reservation.setStatus(ReservationStatus.CONFIRMED);
//         reservation.setTotalPrice(160.0);
//         assertEquals(room, reservation.getRoom());
//         assertEquals("Kien", reservation.getCustomerName());
//         assertEquals(LocalDate.of(2025,8,15), reservation.getCheckInDate());
//         assertEquals(LocalDate.of(2025,8,17), reservation.getCheckOutDate());
//         assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
//         assertEquals(160.0, reservation.getTotalPrice());
//     }

//     @Test
//     void testCalculateTotalPrice() {
//         Room room = new Room("201", RoomType.SUITE, 150.0, true, "Luxury suite");
//         Reservation reservation = new Reservation(room, "Phong", LocalDate.of(2025,8,14), LocalDate.of(2025,8,17));
//         assertEquals(450.0, reservation.calculateTotalPrice());
//     }

//     @Test
//     void testToString() {
//         Room room = new Room("301", RoomType.DELUXE, 120.0, true, "Deluxe room");
//         Reservation reservation = new Reservation(room, "Phong", LocalDate.of(2025,8,14), LocalDate.of(2025,8,15));
//         String str = reservation.toString();
//         assertTrue(str.contains("Phong"));
//         assertTrue(str.contains("DELUXE"));
//         assertTrue(str.contains("120.0"));
//     }
// }
