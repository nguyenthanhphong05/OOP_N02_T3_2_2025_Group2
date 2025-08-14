package com.g2.hotelm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.Room;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Derived queries
    List<Reservation> findByRoom(Room room);
    List<Reservation> findByCustomer_FullName(String fullName);
    
    // Overlap queries without status filtering
    @Query("SELECT r FROM Reservation r WHERE r.checkInDate <= :endDate AND r.checkOutDate >= :startDate")
    List<Reservation> findConflictingReservations(@Param("startDate") LocalDate startDate, 
                                                  @Param("endDate") LocalDate endDate);
    
    @Query("SELECT r FROM Reservation r WHERE r.room = :room AND r.checkInDate <= :endDate AND r.checkOutDate >= :startDate")
    List<Reservation> findConflictingReservationsForRoom(@Param("room") Room room,
                                                         @Param("startDate") LocalDate startDate, 
                                                         @Param("endDate") LocalDate endDate);
}
