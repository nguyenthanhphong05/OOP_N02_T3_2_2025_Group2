package com.g2.hotelm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.ReservationStatus;
import com.g2.hotelm.model.Room;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerName(String customerName);
    List<Reservation> findByRoom(Room room);
    List<Reservation> findByStatus(ReservationStatus status);
    
    @Query("SELECT r FROM Reservation r WHERE r.room = :room AND r.status IN :statuses")
    List<Reservation> findByRoomAndStatusIn(@Param("room") Room room, @Param("statuses") List<ReservationStatus> statuses);
    
    @Query("SELECT r FROM Reservation r WHERE r.checkInDate <= :endDate AND r.checkOutDate >= :startDate AND r.status IN :statuses")
    List<Reservation> findConflictingReservations(@Param("startDate") LocalDate startDate, 
                                                  @Param("endDate") LocalDate endDate, 
                                                  @Param("statuses") List<ReservationStatus> statuses);
    
    @Query("SELECT r FROM Reservation r WHERE r.room = :room AND r.checkInDate <= :endDate AND r.checkOutDate >= :startDate AND r.status IN :statuses")
    List<Reservation> findConflictingReservationsForRoom(@Param("room") Room room,
                                                         @Param("startDate") LocalDate startDate, 
                                                         @Param("endDate") LocalDate endDate, 
                                                         @Param("statuses") List<ReservationStatus> statuses);
}
