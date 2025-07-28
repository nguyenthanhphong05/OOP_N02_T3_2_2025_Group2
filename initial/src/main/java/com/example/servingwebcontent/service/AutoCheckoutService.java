package com.example.servingwebcontent.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.servingwebcontent.model.Reservation;
import com.example.servingwebcontent.model.Room;

@Service
public class AutoCheckoutService {
    
    @Autowired
    private HotelService hotelService;
    
    /**
     * Auto checkout - process all reservations that end today
     * This should be called daily (e.g., at 12:00 PM)
     * @return number of rooms checked out
     */
    public int processAutoCheckout() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        int checkedOutRooms = 0;
        
        List<Reservation> reservations = hotelService.getReservations();
        
        for (Reservation reservation : reservations) {
            LocalDate checkoutDate = reservation.getCheckOutDate().toLocalDate();
            LocalDateTime checkoutTime = reservation.getCheckOutDate();
            
            // If checkout date is today and checkout time has passed
            if (checkoutDate.isEqual(today) && now.isAfter(checkoutTime)) {
                Room room = reservation.getRoom();
                
                // Make room available
                room.setAvailable(true);
                checkedOutRooms++;
            }
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
        
        List<Reservation> reservations = hotelService.getReservations();
        
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
        summary.append("Total rooms: ").append(hotelService.getRooms().size()).append("\n");
        summary.append("Total customers: ").append(hotelService.getCustomers().size()).append("\n");
        summary.append("Total reservations: ").append(hotelService.getReservations().size()).append("\n");
        
        return summary.toString();
    }
}
