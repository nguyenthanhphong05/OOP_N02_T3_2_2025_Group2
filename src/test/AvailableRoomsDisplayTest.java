package test;

import java.time.LocalDateTime;

import model.Customer;
import model.Reservation;
import model.Room;
import service.AvailableRoomsDisplay;
import service.HotelM;

public class AvailableRoomsDisplayTest {
    public static void testAvailableRoomsDisplay() {
        // Create test data
        HotelM hotel = new HotelM();
        AvailableRoomsDisplay service = new AvailableRoomsDisplay();
        
        // Add rooms
        hotel.addRoom(new Room("101", "Single", true));
        hotel.addRoom(new Room("102", "Double", true));
        hotel.addRoom(new Room("103", "Suite", true));
        hotel.addRoom(new Room("104", "Single", false)); // Not available
        
        // Add customer
        Customer customer = new Customer("C001", "John Doe", "john@email.com", "123456789");
        hotel.addCustomer(customer);
        
        System.out.println("=== TEST 1: All Rooms Available (No Reservations) ===");
        service.displayAvailableRoomsNow(hotel.getRooms(), hotel.getReservations());
        
        // Add reservation that occupies room 101 (current time period)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime checkIn = now.minusHours(2);  // Started 2 hours ago
        LocalDateTime checkOut = now.plusHours(3);  // Ends in 3 hours
        
        Reservation reservation1 = new Reservation("R001", hotel.getRooms().get(0), customer, checkIn, checkOut);
        hotel.addReservation(reservation1);
        
        System.out.println("\n=== TEST 2: With Active Reservation (Room 101 Occupied) ===");
        service.displayAvailableRoomsNow(hotel.getRooms(), hotel.getReservations());
        
        // Add more reservations to occupy more rooms
        Reservation reservation2 = new Reservation("R002", hotel.getRooms().get(1), customer, 
                                                  now.minusHours(1), now.plusHours(2));
        hotel.addReservation(reservation2);
        
        System.out.println("\n=== TEST 3: Multiple Reservations (Room 101 & 102 Occupied) ===");
        service.displayAvailableRoomsNow(hotel.getRooms(), hotel.getReservations());
        
        // Make remaining available room unavailable
        hotel.getRooms().get(2).setAvailable(false); // Suite 103
        
        System.out.println("\n=== TEST 4: No Available Rooms ===");
        service.displayAvailableRoomsNow(hotel.getRooms(), hotel.getReservations());
        
        // Test with past reservation (should not affect availability)
        Reservation pastReservation = new Reservation("R003", hotel.getRooms().get(2), customer,
                                                     now.minusDays(2), now.minusDays(1));
        hotel.addReservation(pastReservation);
        hotel.getRooms().get(2).setAvailable(true); // Make room 103 available again
        
        System.out.println("\n=== TEST 5: Past Reservation (Should Not Affect Current Availability) ===");
        service.displayAvailableRoomsNow(hotel.getRooms(), hotel.getReservations());
    }
}