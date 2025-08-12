package com.g2.hotelm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2.hotelm.model.Room;
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

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
    
    public void deleteRoom(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("K Ton tai Room");
        }
        roomRepository.deleteById(id);
    }

    public void updateRoom(Room room) {
        if (room == null || room.getId() == null) {
            throw new IllegalArgumentException("K Ton tai Room");
        }
        roomRepository.save(room);
    }

}
