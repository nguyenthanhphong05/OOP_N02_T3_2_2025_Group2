package com.example.servingwebcontent.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.servingwebcontent.model.Reservation;
import com.example.servingwebcontent.model.Room;

@Service
public class AvailableRoomsService {
    
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
}
