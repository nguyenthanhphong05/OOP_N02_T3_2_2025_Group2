package com.example.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Reservation;
import com.example.model.Room;

/**
 * Auto Checkout Service - handles automatic daily checkout operations
 * Separate from CRUD operations in HotelM
 */
public class AutoCheckout {
    
    private HotelM hotelManager;
    
    public AutoCheckout(HotelM hotelManager) {
        this.hotelManager = hotelManager;
    }
    
    /**
     * Auto checkout - process all reservations that end today
     * This should be called daily (e.g., at 12:00 PM)
     * @return number of rooms checked out
     */
    public int processAutoCheckout() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        int checkedOutRooms = 0;
        
        System.out.println("Processing automatic checkout for: " + today);
        System.out.println("Current time: " + now);
        System.out.println("----------------------------------------");
        
        List<Reservation> reservations = hotelManager.getReservations();
        
        for (Reservation reservation : reservations) {
            LocalDate checkoutDate = reservation.getCheckOutDate().toLocalDate();
            LocalDateTime checkoutTime = reservation.getCheckOutDate();
            
            // If checkout date is today and checkout time has passed
            if (checkoutDate.isEqual(today) && now.isAfter(checkoutTime)) {
                Room room = reservation.getRoom();
                
                System.out.println("Auto-checking out:");
                System.out.println("  Customer: " + reservation.getCustomer().getName());
                System.out.println("  Room: " + room.getRoomNumber() + " (" + room.getRoomType() + ")");
                System.out.println("  Scheduled checkout: " + checkoutTime);
                
                // Make room available
                room.setAvailable(true);
                checkedOutRooms++;
                
                System.out.println("  Room " + room.getRoomNumber() + " is now available");
                System.out.println("  ---");
            }
        }
        
        if (checkedOutRooms == 0) {
            System.out.println("No automatic checkouts needed today.");
        } else {
            System.out.println("Completed " + checkedOutRooms + " automatic checkouts.");
        }
        
        return checkedOutRooms;
    }
    
    /**
     * Get all reservations that should checkout today
     * @return list of reservations ending today
     */
    public List<Reservation> getTodaysCheckouts() {
        LocalDate today = LocalDate.now();
        List<Reservation> todaysCheckouts = new ArrayList<>();
        
        List<Reservation> reservations = hotelManager.getReservations();
        
        for (Reservation reservation : reservations) {
            if (reservation.getCheckOutDate().toLocalDate().isEqual(today)) {
                todaysCheckouts.add(reservation);
            }
        }
        
        return todaysCheckouts;
    }
    
    /**
     * Get daily operations summary
     * @return summary of today's hotel operations
     */
    public String getDailyOperationsSummary() {
        LocalDate today = LocalDate.now();
        List<Reservation> todaysCheckouts = getTodaysCheckouts();
        
        StringBuilder summary = new StringBuilder();
        summary.append("DAILY OPERATIONS SUMMARY - ").append(today).append("\n");
        summary.append("=========================================\n");
        summary.append("Scheduled checkouts today: ").append(todaysCheckouts.size()).append("\n");
        summary.append("Total rooms: ").append(hotelManager.getRooms().size()).append("\n");
        summary.append("Total customers: ").append(hotelManager.getCustomers().size()).append("\n");
        summary.append("Total reservations: ").append(hotelManager.getReservations().size()).append("\n");
        
        return summary.toString();
    }
}