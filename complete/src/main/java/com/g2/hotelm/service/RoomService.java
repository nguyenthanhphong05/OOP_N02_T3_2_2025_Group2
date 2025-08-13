package com.g2.hotelm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2.hotelm.model.Room;
import com.g2.hotelm.model.RoomType;
import com.g2.hotelm.repository.RoomRepository;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> findRoomById(Long id) {
        return roomRepository.findById(id);
    }
    
    public Optional<Room> findRoomByRoomId(String roomId) {
        return roomRepository.findByRoomId(roomId);
    }
    
    public List<Room> findRoomsByType(RoomType type) {
        return roomRepository.findByType(type);
    }
    
    public List<Room> findAvailableRoomsByType(RoomType type, LocalDate checkIn, LocalDate checkOut) {
        return roomRepository.findAvailableRoomsByType(type, checkIn, checkOut);
    }
    
    public boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        return room.isAvailableForPeriod(checkIn, checkOut);
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    
    public void deleteRoom(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        roomRepository.deleteById(id);
    }

    public void updateRoom(Room room) {
        if (room == null || room.getId() == null) {
            throw new IllegalArgumentException("Room or Room ID cannot be null");
        }
        roomRepository.save(room);
    }

}
