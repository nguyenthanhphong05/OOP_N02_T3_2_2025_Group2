package com.g2.hotelm.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.model.Reservation;
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
    public boolean isRoomAvailableForPeriod(Room room, LocalDate checkIn, LocalDate checkOut) {
        List<Reservation> conflicts = reservationRepository.findConflictingReservationsForRoom(
            room, checkIn, checkOut);
        
        return conflicts.isEmpty();
    }

    /**
     * Find reservations by customer name
     */
    public List<Reservation> findReservationsByCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
    return reservationRepository.findByCustomer_FullName(customerName.trim());
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
        // Customer is optional for now; if present, ensure minimal data
        Customer customer = reservation.getCustomer();
        if (customer != null && (customer.getFullName() == null || customer.getFullName().trim().isEmpty())) {
            throw new IllegalArgumentException("Customer name cannot be empty if provided");
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
    // No string fields to trim here; ensure total price is up to date
    reservation.setTotalPrice(reservation.calculateTotalPrice());
    }
}
