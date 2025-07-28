package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;
import model.Room;

/**
 * Method 1: Display available rooms
 * Phương thức hiển thị phòng trống
 */
public class AvailableRoomsDisplay {
    
    /**
     * Get available rooms RIGHT NOW
     * @param rooms all rooms from hotel
     * @param reservations all reservations
     * @return rooms available at current moment
     */
    public List<Room> getAvailableRoomsNow(List<Room> rooms, List<Reservation> reservations) {
        LocalDateTime now = LocalDateTime.now();
        List<Room> availableRooms = new ArrayList<>();
        
        for (Room room : rooms) {
            if (room.isAvailable() && !isRoomOccupiedNow(room, reservations, now)) {
                availableRooms.add(room);
            }
        }
        
        return availableRooms;
    }
    
    /**
     * Check if room is occupied at current time
     * @param room the room to check
     * @param reservations all reservations
     * @param currentTime current time
     * @return true if occupied, false if available
     */
    private boolean isRoomOccupiedNow(Room room, List<Reservation> reservations, LocalDateTime currentTime) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                LocalDateTime checkIn = reservation.getCheckInDate();
                LocalDateTime checkOut = reservation.getCheckOutDate();
                
                // Room is occupied if current time is between check-in and check-out
                if (!currentTime.isBefore(checkIn) && currentTime.isBefore(checkOut)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Display available rooms for right now
     * @param rooms all rooms
     * @param reservations all reservations
     */
    public void displayAvailableRoomsNow(List<Room> rooms, List<Reservation> reservations) {
        LocalDateTime now = LocalDateTime.now();
        List<Room> availableRooms = getAvailableRoomsNow(rooms, reservations);
        
        System.out.println("🏨 AVAILABLE ROOMS RIGHT NOW");
        System.out.println("   Phòng trống lúc này: " + now.toLocalDate());
        System.out.println("═══════════════════════════════════════");
        
        if (availableRooms.isEmpty()) {
            System.out.println("❌ No rooms available right now");
            System.out.println("   Không có phòng trống lúc này");
            return;
        }
        
        System.out.printf("✅ Found %d available rooms:%n", availableRooms.size());
        System.out.println("─────────────────────────────────────");
        
        for (Room room : availableRooms) {
            System.out.printf("🚪 Room %-8s | %-12s | Available%n", 
                            room.getRoomNumber(), 
                            room.getRoomType());
        }
        
        System.out.println("═══════════════════════════════════════");
    }
}
