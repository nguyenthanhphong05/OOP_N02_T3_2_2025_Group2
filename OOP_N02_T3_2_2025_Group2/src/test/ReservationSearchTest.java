package test;

import model.Customer;
import model.Reservation;
import model.Room;
import service.ReservationSearch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationSearchTest {
    public static void testReservationSearch() {
        // --- 1. Create Sample Data ---
        // Rooms
        Room room101 = new Room("101", "Standard", true);
        Room room102 = new Room("102", "Deluxe", true);
        Room room201 = new Room("201", "Suite", true);

        // Customers
        Customer customerA = new Customer("C001", "Nguyen Van A", "nguyenvana@example.com", "0901234567");
        Customer customerB = new Customer("C002", "Tran Thi B", "tranthib@example.com", "0912345678");
        Customer customerC = new Customer("C003", "Le Van Cuong", "levancuong@example.com", "0987654321");
        Customer customerD = new Customer("C004", "Pham Thuy Anh", "phamthuyanh@example.com", "0976543210");
        Customer customerE = new Customer("C005", "Hoang Minh Tuan", "hoangminhtuan@example.com", "0965432187");


        // Reservations
        List<Reservation> allReservations = new ArrayList<>();
        allReservations.add(new Reservation("RES001", room101, customerA, LocalDateTime.of(2025, 7, 25, 14, 0), LocalDateTime.of(2025, 7, 28, 12, 0)));
        allReservations.add(new Reservation("RES002", room102, customerB, LocalDateTime.of(2025, 8, 10, 14, 0), LocalDateTime.of(2025, 8, 15, 12, 0)));
        allReservations.add(new Reservation("RES003", room101, customerC, LocalDateTime.of(2025, 9, 1, 14, 0), LocalDateTime.of(2025, 9, 5, 12, 0)));
        allReservations.add(new Reservation("RES004", room201, customerE, LocalDateTime.of(2025, 10, 1, 14, 0), LocalDateTime.of(2025, 10, 7, 12, 0)));
        allReservations.add(new Reservation("RES005", room102, customerD, LocalDateTime.of(2025, 11, 1, 14, 0), LocalDateTime.of(2025, 11, 3, 12, 0)));

    
        ReservationSearch reservationSearch = new ReservationSearch(allReservations);

        System.out.println("--- Starting Reservation Search Tests ---");

        //Search by Customer ID
        String searchCustomerID = "C001";
        System.out.println("\nSearching by Customer ID: '" + searchCustomerID + "'");
        List<Reservation> resultsByCustomerID = reservationSearch.searchByCustomerID(searchCustomerID);
        if (resultsByCustomerID.isEmpty()) {
            System.out.println("  No reservations found for Customer ID: " + searchCustomerID);
        } else {
            System.out.println("  Found " + resultsByCustomerID.size() + " reservation(s):");
            resultsByCustomerID.forEach(res ->
                System.out.println("    " + res.getResID() + " - Customer: " + res.getCustomer().getName() + " - Room: " + res.getRoom().getRoomNumber())
            );
        }

        //Search by Customer Name
        String searchCustomerName = "nguyen";
        System.out.println("\nSearching by Customer Name: '" + searchCustomerName + "'");
        List<Reservation> resultsByCustomerName = reservationSearch.searchByCustomerName(searchCustomerName);
        if (resultsByCustomerName.isEmpty()) {
            System.out.println("  No reservations found for Customer Name: " + searchCustomerName);
        } else {
            System.out.println("  Found " + resultsByCustomerName.size() + " reservation(s):");
            resultsByCustomerName.forEach(res ->
                System.out.println("    " + res.getResID() + " - Customer: " + res.getCustomer().getName() + " - Room: " + res.getRoom().getRoomNumber())
            );
        }

        // Search by Room ID
        String searchRoomID = "102";
        System.out.println("\nSearching by Room ID: '" + searchRoomID + "'");
        List<Reservation> resultsByRoomID = reservationSearch.searchByRoomID(searchRoomID);
        if (resultsByRoomID.isEmpty()) {
            System.out.println("  No reservations found for Room ID: " + searchRoomID);
        } else {
            System.out.println("  Found " + resultsByRoomID.size() + " reservation(s):");
            resultsByRoomID.forEach(res ->
                System.out.println("    " + res.getResID() + " - Customer: " + res.getCustomer().getName() + " - Room: " + res.getRoom().getRoomNumber())
            );
        }

        // --- Test 4: Search for a non-existent ID/Name ---
        String nonExistentID = "NONEXISTENT";
        System.out.println("\nSearching for non-existent Customer ID: '" + nonExistentID + "'");
        List<Reservation> noResults = reservationSearch.searchByCustomerID(nonExistentID);
        if (noResults.isEmpty()) {
            System.out.println("  Correctly found no results for: " + nonExistentID);
        } else {
            System.out.println("  Error: Found unexpected results for: " + nonExistentID);
        }
    }
}