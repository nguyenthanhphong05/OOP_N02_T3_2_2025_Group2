package com.g2.hotelm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.hotelm.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByIsAvailableTrue();
}
