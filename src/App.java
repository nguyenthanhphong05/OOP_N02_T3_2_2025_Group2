import test.TestAvailableRoomsDisplay;
import test.TestCustomerCheckout;
import review.TestStudent;
import test.ReservationSearchTest;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("=== HOTEL MANAGEMENT SYSTEM - OOP PRACTICE 5 ===");
        System.out.println("Testing 3 Main Operational Methods\n");
        
        // Test Method 1: Available Rooms Display
        System.out.println("1. TESTING AVAILABLE ROOMS DISPLAY");
        System.out.println("==================================");
        TestAvailableRoomsDisplay.main(args);
        
        System.out.println("\n\n");
        
        // Test Method 2: Reservation Search
        System.out.println("2. TESTING RESERVATION SEARCH");
        System.out.println("=============================");
        ReservationSearchTest.main(args);
        
        System.out.println("\n\n");
        
        // Test Method 3: Customer Checkout
        System.out.println("3. TESTING CUSTOMER CHECKOUT");
        System.out.println("============================");
        TestCustomerCheckout.main(args);
        
        System.out.println("\n\n=== ALL TESTS COMPLETED ===");
        System.out.println("Hotel Management System - Practice 5 Completed Successfully!");
    
    }
}
