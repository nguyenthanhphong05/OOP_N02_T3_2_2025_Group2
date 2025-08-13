package com.g2.hotelm.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.g2.hotelm.model.Reservation;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testSaveReservation() {
        // Given
        Reservation reservation = new Reservation();
        reservation.setCustomerName("John Doe");
        reservation.setRoomType("Single");
        reservation.setStartDate("2024-01-01");
        reservation.setEndDate("2024-01-03");

        // When
        Reservation savedReservation = reservationService.saveReservation(reservation);

        // Then
        assertThat(savedReservation).isNotNull();
        assertThat(savedReservation.getId()).isNotNull();
        assertThat(savedReservation.getCustomerName()).isEqualTo("John Doe");
        assertThat(savedReservation.getRoomType()).isEqualTo("Single");
    }

    @Test
    public void testFindAllReservations() {
        // Given
        Reservation reservation1 = createTestReservation("Alice", "Double", "2024-02-01", "2024-02-03");
        Reservation reservation2 = createTestReservation("Bob", "Suite", "2024-03-01", "2024-03-05");
        
        reservationService.saveReservation(reservation1);
        reservationService.saveReservation(reservation2);

        // When
        List<Reservation> reservations = reservationService.findAllReservations();

        // Then
        assertThat(reservations).hasSizeGreaterThanOrEqualTo(2);
        assertThat(reservations).extracting(Reservation::getCustomerName)
                .contains("Alice", "Bob");
    }

    @Test
    public void testFindReservationById() {
        // Given
        Reservation reservation = createTestReservation("Charlie", "Deluxe", "2024-04-01", "2024-04-02");
        Reservation savedReservation = reservationService.saveReservation(reservation);

        // When
        Optional<Reservation> foundReservation = reservationService.findReservationById(savedReservation.getId());

        // Then
        assertThat(foundReservation).isPresent();
        assertThat(foundReservation.get().getCustomerName()).isEqualTo("Charlie");
    }

    @Test
    public void testFindReservationsByCustomerName() {
        // Given
        Reservation reservation1 = createTestReservation("David", "Single", "2024-05-01", "2024-05-02");
        Reservation reservation2 = createTestReservation("David", "Double", "2024-06-01", "2024-06-03");
        Reservation reservation3 = createTestReservation("Eve", "Suite", "2024-07-01", "2024-07-04");
        
        reservationService.saveReservation(reservation1);
        reservationService.saveReservation(reservation2);
        reservationService.saveReservation(reservation3);

        // When
        List<Reservation> davidReservations = reservationService.findReservationsByCustomerName("David");

        // Then
        assertThat(davidReservations).hasSize(2);
        assertThat(davidReservations).allMatch(r -> r.getCustomerName().equals("David"));
    }

    @Test
    public void testUpdateReservation() {
        // Given
        Reservation reservation = createTestReservation("Frank", "Single", "2024-08-01", "2024-08-02");
        Reservation savedReservation = reservationService.saveReservation(reservation);

        // When
        savedReservation.setRoomType("Double");
        savedReservation.setEndDate("2024-08-03");
        Reservation updatedReservation = reservationService.updateReservation(savedReservation);

        // Then
        assertThat(updatedReservation.getRoomType()).isEqualTo("Double");
        assertThat(updatedReservation.getEndDate()).isEqualTo("2024-08-03");
    }

    @Test
    public void testDeleteReservation() {
        // Given
        Reservation reservation = createTestReservation("Grace", "Suite", "2024-09-01", "2024-09-03");
        Reservation savedReservation = reservationService.saveReservation(reservation);
        Long reservationId = savedReservation.getId();

        // When
        reservationService.deleteReservation(reservationId);

        // Then
        Optional<Reservation> deletedReservation = reservationService.findReservationById(reservationId);
        assertThat(deletedReservation).isEmpty();
    }

    @Test
    public void testValidationThrowsExceptionForNullReservation() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.saveReservation(null);
        });
        assertThat(exception.getMessage()).contains("Reservation cannot be null");
    }

    @Test
    public void testValidationThrowsExceptionForEmptyCustomerName() {
        // Given
        Reservation reservation = createTestReservation("", "Single", "2024-10-01", "2024-10-02");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.saveReservation(reservation);
        });
        assertThat(exception.getMessage()).contains("Customer name is required");
    }

    @Test
    public void testValidationThrowsExceptionForInvalidDates() {
        // Given
        Reservation reservation = createTestReservation("Henry", "Double", "2024-11-03", "2024-11-01");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            reservationService.saveReservation(reservation);
        });
        assertThat(exception.getMessage()).contains("Start date must be before end date");
    }

    @Test
    public void testCountReservations() {
        // Given
        long initialCount = reservationService.countReservations();
        
        Reservation reservation = createTestReservation("Ivy", "Single", "2024-12-01", "2024-12-02");
        reservationService.saveReservation(reservation);

        // When
        long newCount = reservationService.countReservations();

        // Then
        assertThat(newCount).isEqualTo(initialCount + 1);
    }

    @Test
    public void testReservationExists() {
        // Given
        Reservation reservation = createTestReservation("Jack", "Deluxe", "2025-01-01", "2025-01-02");
        Reservation savedReservation = reservationService.saveReservation(reservation);

        // When & Then
        assertThat(reservationService.reservationExists(savedReservation.getId())).isTrue();
        assertThat(reservationService.reservationExists(999999L)).isFalse();
        assertThat(reservationService.reservationExists(null)).isFalse();
    }

    // Helper method to create test reservations
    private Reservation createTestReservation(String customerName, String roomType, String startDate, String endDate) {
        Reservation reservation = new Reservation();
        reservation.setCustomerName(customerName);
        reservation.setRoomType(roomType);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return reservation;
    }
}
