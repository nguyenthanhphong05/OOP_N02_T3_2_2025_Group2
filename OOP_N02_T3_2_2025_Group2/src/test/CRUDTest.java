package test;

import model.Customer;
import model.Room;
import service.HotelM;

public class CRUDTest {
    public static void testCRUD() {
        System.out.println("Hotel Management System - CRUD Demo\n");
        
        HotelM hotel = new HotelM();
        
        // CREATE - Add some data
        System.out.println("CREATE: Adding rooms and customers...");
        hotel.addRoom(new Room("101", "Deluxe", true));
        hotel.addRoom(new Room("102", "Standard", true));
        hotel.addCustomer(new Customer("C001", "John Doe", "john@example.com", "123-456-7890"));
        System.out.println("Added rooms and customers!");
        
        // READ - Show what we have
        System.out.println("\nREAD: Current data:");
        System.out.println("Total rooms: " + hotel.getRooms().size());
        System.out.println("Total customers: " + hotel.getCustomers().size());
        
        // UPDATE - Change room 102
        System.out.println("\nUPDATE: Changing room 102 to Suite...");
        hotel.updateRoom(new Room("102", "Suite", true), "Suite", false);
        System.out.println("Room 102 updated!");
        
        // DELETE - Remove room 102
        System.out.println("\nDELETE: Removing room 102...");
        hotel.deleteRoom(102);
        System.out.println("Room 102 deleted!");
        
    }
}
