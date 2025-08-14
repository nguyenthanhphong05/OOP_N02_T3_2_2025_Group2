package com.g2.hotelm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.g2.hotelm.model.Room;
import com.g2.hotelm.model.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomId(String roomId);
    List<Room> findByType(RoomType type);
    List<Room> findByIsAvailable(Boolean isAvailable);
    List<Room> findByTypeAndIsAvailable(RoomType type, Boolean isAvailable);
    
    @Query("SELECT r FROM Room r WHERE r.type = :type AND r.isAvailable = true AND r.id NOT IN " +
           "(SELECT res.room.id FROM Reservation res WHERE res.checkInDate <= :checkOut AND res.checkOutDate >= :checkIn " +
           ")")
    List<Room> findAvailableRoomsByType(@Param("type") RoomType type, 
                                       @Param("checkIn") LocalDate checkIn, 
                                       @Param("checkOut") LocalDate checkOut);
    
    @Query("SELECT r FROM Room r WHERE r.isAvailable = true AND r.id NOT IN " +
           "(SELECT res.room.id FROM Reservation res WHERE res.checkInDate <= :checkOut AND res.checkOutDate >= :checkIn " +
           ")")
    List<Room> findAvailableRooms(@Param("checkIn") LocalDate checkIn, 
                                 @Param("checkOut") LocalDate checkOut);
}
