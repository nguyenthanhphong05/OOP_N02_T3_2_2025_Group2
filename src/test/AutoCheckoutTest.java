package test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Customer;
import model.Reservation;
import model.Room;
import service.AutoCheckout;
import service.HotelM;

public class AutoCheckoutTest {
    public static void main(String[] args) {
        // Setup test data
        HotelM hotel = new HotelM();
        
        // Add rooms - all start as occupied
        hotel.addRoom(new Room("101", "Single", false));   
        hotel.addRoom(new Room("102", "Double", false));   
        hotel.addRoom(new Room("103", "Suite", true));     // Available
        
        // Add customers
        Customer customer1 = new Customer("C001", "John Doe", "john@email.com", "123456789");
        Customer customer2 = new Customer("C002", "Jane Smith", "jane@email.com", "987654321");
        Customer customer3 = new Customer("C003", "Bob Wilson", "bob@email.com", "555123456");
        hotel.addCustomer(customer1);
        hotel.addCustomer(customer2);
        hotel.addCustomer(customer3);
        
        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        
        // Create reservations with different scenarios
        
        // 1. Checkout that should happen (checkout time already passed)
        LocalDateTime pastCheckout = now.minusHours(2); // 2 hours ago
        Reservation res1 = new Reservation("R001", hotel.getRooms().get(0), customer1, 
                                          pastCheckout.minusDays(1), pastCheckout);
        hotel.addReservation(res1);
        
        // 2. Checkout scheduled for later today (shouldn't auto-checkout yet)
        LocalDateTime futureCheckout = now.plusHours(3); // 3 hours from now
        Reservation res2 = new Reservation("R002", hotel.getRooms().get(1), customer2,
                                          futureCheckout.minusDays(1), futureCheckout);
        hotel.addReservation(res2);
        
        // 3. Checkout for tomorrow (shouldn't auto-checkout)
        LocalDateTime tomorrowCheckout = today.plusDays(1).atTime(11, 0);
        Reservation res3 = new Reservation("R003", hotel.getRooms().get(2), customer3,
                                          tomorrowCheckout.minusDays(2), tomorrowCheckout);
        hotel.addReservation(res3);
        hotel.getRooms().get(2).setAvailable(false); // Make it occupied
        
        System.out.println("=== BEFORE AUTO CHECKOUT ===");
        System.out.println("Current time: " + now);
        displayRoomStatus(hotel);
        displayReservations(hotel);
        
        // Create auto checkout service and test
        AutoCheckout autoCheckout = new AutoCheckout(hotel);
        
        System.out.println("\n=== PROCESSING AUTO CHECKOUT ===");
        int checkedOut = autoCheckout.processAutoCheckout();
        
        System.out.println("\n=== AFTER AUTO CHECKOUT ===");
        System.out.println("Rooms automatically checked out: " + checkedOut);
        displayRoomStatus(hotel);
        
        System.out.println("\n=== DAILY SUMMARY ===");
        System.out.println(autoCheckout.getDailyOperationsSummary());
        
        System.out.println("\n=== TODAY'S CHECKOUT SCHEDULE ===");
        displayTodaysCheckouts(autoCheckout);
    }
    
    private static void displayRoomStatus(HotelM hotel) {
        System.out.println("\nRoom Status:");
        System.out.println("Room No. | Type   | Available");
        System.out.println("---------|--------|----------");
        for (Room room : hotel.getRooms()) {
            System.out.printf("%-8s | %-6s | %s%n", 
                            room.getRoomNumber(), 
                            room.getRoomType(), 
                            room.isAvailable() ? "Yes" : "No");
        }
    }
    
    private static void displayReservations(HotelM hotel) {
        System.out.println("\nActive Reservations:");
        System.out.println("Res ID | Customer    | Room | Checkout Time");
        System.out.println("-------|-------------|------|---------------");
        for (Reservation res : hotel.getReservations()) {
            System.out.printf("%-6s | %-11s | %-4s | %s%n",
                            res.getResID(),
                            res.getCustomer().getName(),
                            res.getRoom().getRoomNumber(),
                            res.getCheckOutDate());
        }
    }
    
    private static void displayTodaysCheckouts(AutoCheckout autoCheckout) {
        var todaysCheckouts = autoCheckout.getTodaysCheckouts();
        if (todaysCheckouts.isEmpty()) {
            System.out.println("No checkouts scheduled for today");
        } else {
            System.out.println("Checkouts scheduled for today:");
            for (Reservation res : todaysCheckouts) {
                System.out.printf("- %s: %s (Room %s) at %s%n",
                                res.getResID(),
                                res.getCustomer().getName(),
                                res.getRoom().getRoomNumber(),
                                res.getCheckOutDate());
            }
        }
    }
}