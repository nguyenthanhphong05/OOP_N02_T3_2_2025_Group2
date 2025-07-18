package test;

import java.time.LocalDateTime;
import model.Customer;
import model.Reservation;
import model.Room;
import service.HotelM;

public class HotelMTest {
    public static void main(String[] args) {
        System.out.println("Testing HotelM Service...\n");
        
        // Create a HotelM service instance
        HotelM hotel = new HotelM();
        
        // Test 1: Add rooms
        System.out.println("=== Testing Room Management ===");
        Room room1 = new Room("101", "Deluxe", true);
        Room room2 = new Room("102", "Standard", true);
        
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        
        System.out.println("Added 2 rooms to hotel");
        System.out.println("Total rooms: " + hotel.getRooms().size());
        
        // Test 2: Add customers
        System.out.println("\n=== Testing Customer Management ===");
        Customer customer1 = new Customer("C001", "John Doe", "john@example.com", "123-456-7890");
        Customer customer2 = new Customer("C002", "Jane Smith", "jane@example.com", "987-654-3210");
        
        hotel.addCustomer(customer1);
        hotel.addCustomer(customer2);
        
        System.out.println("Added 2 customers to hotel");
        System.out.println("Total customers: " + hotel.getCustomers().size());
        
        // Test 3: Add reservations
        System.out.println("\n=== Testing Reservation Management ===");
        Reservation reservation1 = new Reservation("R001", room1, customer1, 
                                                   LocalDateTime.now(), 
                                                   LocalDateTime.now().plusDays(3));
        Reservation reservation2 = new Reservation("R002", room2, customer2, 
                                                   LocalDateTime.now().plusDays(1), 
                                                   LocalDateTime.now().plusDays(4));
        
        hotel.addReservation(reservation1);
        hotel.addReservation(reservation2);
        
        System.out.println("Added 2 reservations to hotel");
        System.out.println("Total reservations: " + hotel.getReservations().size());
        
        // Test 4: Display all data
        System.out.println("\n=== Hotel Summary ===");
        System.out.println("Rooms: " + hotel.getRooms().size());
        System.out.println("Customers: " + hotel.getCustomers().size());
        System.out.println("Reservations: " + hotel.getReservations().size());
        
        // Test 5: Display specific reservation details
        System.out.println("\n=== Reservation Details ===");
        for (Reservation reservation : hotel.getReservations()) {
            System.out.println(reservation.getReservationInfo());
            System.out.println("---");
        }
        
        System.out.println("\nAll HotelM service tests completed successfully!");
    }
}
