package com.g2.hotelm.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.ReservationStatus;
import com.g2.hotelm.model.Room;
import com.g2.hotelm.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private RoomService roomService;

    /**
     * Find all reservations
     */
    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    /**
     * Find reservation by ID
     */
    public Optional<Reservation> findReservationById(Long id) {
        return reservationRepository.findById(id);
    }
    
    /**
     * Find reservations by room
     */
    public List<Reservation> findReservationsByRoom(Room room) {
        return reservationRepository.findByRoom(room);
    }
    
    /**
     * Find reservations by status
     */
    public List<Reservation> findReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status);
    }

    /**
     * Save a new reservation
     */
    public Reservation saveReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        
        // Basic validation
        validateReservation(reservation);
        
        // Check room availability
        if (!isRoomAvailableForPeriod(reservation.getRoom(), reservation.getCheckInDate(), reservation.getCheckOutDate())) {
            throw new IllegalArgumentException("Room is not available for the specified period");
        }
        
        // Set default status if not set
        if (reservation.getStatus() == null) {
            reservation.setStatus(ReservationStatus.PENDING);
        }
        
        Reservation savedReservation = reservationRepository.save(reservation);
        
        // Add reservation to room (bidirectional relationship)
        reservation.getRoom().addReservation(savedReservation);
        
        return savedReservation;
    }

    /**
     * Update an existing reservation
     */
    public Reservation updateReservation(Reservation reservation) {
        if (reservation == null || reservation.getId() == null) {
            throw new IllegalArgumentException("Reservation or ID cannot be null");
        }
        
        // Check if reservation exists
        if (!reservationRepository.existsById(reservation.getId())) {
            throw new IllegalArgumentException("Reservation with ID " + reservation.getId() + " does not exist");
        }
        
        // Basic validation
        validateReservation(reservation);
        
        return reservationRepository.save(reservation);
    }

    /**
     * Delete reservation by ID
     */
    public void deleteReservation(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }
        
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if (!reservationOpt.isPresent()) {
            throw new IllegalArgumentException("Reservation with ID " + id + " does not exist");
        }
        
        Reservation reservation = reservationOpt.get();
        
        // Remove from room (bidirectional relationship)
        if (reservation.getRoom() != null) {
            reservation.getRoom().removeReservation(reservation);
        }
        
        reservationRepository.deleteById(id);
    }
    
    /**
     * Check room availability for a period
     */
    public boolean isRoomAvailableForPeriod(Room room, LocalDate checkIn, LocalDate checkOut) {
        List<ReservationStatus> activeStatuses = Arrays.asList(
            ReservationStatus.CONFIRMED, 
            ReservationStatus.CHECKED_IN
        );
        
        List<Reservation> conflicts = reservationRepository.findConflictingReservationsForRoom(
            room, checkIn, checkOut, activeStatuses);
        
        return conflicts.isEmpty();
    }
    
    /**
     * Confirm a reservation
     */
    public Reservation confirmReservation(Long reservationId) {
        Optional<Reservation> reservationOpt = findReservationById(reservationId);
        if (!reservationOpt.isPresent()) {
            throw new IllegalArgumentException("Reservation not found");
        }
        
        Reservation reservation = reservationOpt.get();
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("Only pending reservations can be confirmed");
        }
        
        reservation.setStatus(ReservationStatus.CONFIRMED);
        return reservationRepository.save(reservation);
    }
    
    /**
     * Check in a reservation
     */
    public Reservation checkInReservation(Long reservationId) {
        Optional<Reservation> reservationOpt = findReservationById(reservationId);
        if (!reservationOpt.isPresent()) {
            throw new IllegalArgumentException("Reservation not found");
        }
        
        Reservation reservation = reservationOpt.get();
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException("Only confirmed reservations can be checked in");
        }
        
        reservation.setStatus(ReservationStatus.CHECKED_IN);
        return reservationRepository.save(reservation);
    }
    
    /**
     * Check out a reservation
     */
    public Reservation checkOutReservation(Long reservationId) {
        Optional<Reservation> reservationOpt = findReservationById(reservationId);
        if (!reservationOpt.isPresent()) {
            throw new IllegalArgumentException("Reservation not found");
        }
        
        Reservation reservation = reservationOpt.get();
        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {
            throw new IllegalArgumentException("Only checked-in reservations can be checked out");
        }
        
        reservation.setStatus(ReservationStatus.CHECKED_OUT);
        return reservationRepository.save(reservation);
    }
    
    /**
     * Cancel a reservation
     */
    public Reservation cancelReservation(Long reservationId) {
        Optional<Reservation> reservationOpt = findReservationById(reservationId);
        if (!reservationOpt.isPresent()) {
            throw new IllegalArgumentException("Reservation not found");
        }
        
        Reservation reservation = reservationOpt.get();
        if (reservation.getStatus() == ReservationStatus.CHECKED_OUT) {
            throw new IllegalArgumentException("Cannot cancel a checked-out reservation");
        }
        
        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }

    /**
     * Find reservations by customer name
     */
    public List<Reservation> findReservationsByCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        return reservationRepository.findByCustomerName(customerName.trim());
    }

    /**
     * Count total number of reservations
     */
    public long countReservations() {
        return reservationRepository.count();
    }

    /**
     * Check if a reservation exists by ID
     */
    public boolean reservationExists(Long id) {
        if (id == null) {
            return false;
        }
        return reservationRepository.existsById(id);
    }

    /**
     * Validate reservation data
     */
    private void validateReservation(Reservation reservation) {
        if (reservation.getCustomerName() == null || reservation.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        
        if (reservation.getRoom() == null) {
            throw new IllegalArgumentException("Room is required");
        }
        
        if (reservation.getCheckInDate() == null) {
            throw new IllegalArgumentException("Check-in date is required");
        }
        
        if (reservation.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-out date is required");
        }
        
        if (!reservation.getCheckInDate().isBefore(reservation.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        if (reservation.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }
        
        // Trim whitespace from string fields
        reservation.setCustomerName(reservation.getCustomerName().trim());
    }
}
