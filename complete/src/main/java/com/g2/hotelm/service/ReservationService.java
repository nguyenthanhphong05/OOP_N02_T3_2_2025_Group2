package com.g2.hotelm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

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
     * Save a new reservation
     */
    public Reservation saveReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        
        // Basic validation
        validateReservation(reservation);
        
        return reservationRepository.save(reservation);
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
        
        if (!reservationRepository.existsById(id)) {
            throw new IllegalArgumentException("Reservation with ID " + id + " does not exist");
        }
        
        reservationRepository.deleteById(id);
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
        
        if (reservation.getRoomType() == null || reservation.getRoomType().trim().isEmpty()) {
            throw new IllegalArgumentException("Room type is required");
        }
        
        if (reservation.getStartDate() == null || reservation.getStartDate().trim().isEmpty()) {
            throw new IllegalArgumentException("Start date is required");
        }
        
        if (reservation.getEndDate() == null || reservation.getEndDate().trim().isEmpty()) {
            throw new IllegalArgumentException("End date is required");
        }
        
        // Trim whitespace from string fields
        reservation.setCustomerName(reservation.getCustomerName().trim());
        reservation.setRoomType(reservation.getRoomType().trim());
        reservation.setStartDate(reservation.getStartDate().trim());
        reservation.setEndDate(reservation.getEndDate().trim());
        
        
        if (reservation.getStartDate().compareTo(reservation.getEndDate()) >= 0) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }
}
