package com.g2.hotelm.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.Room;
import com.g2.hotelm.model.RoomType;
import com.g2.hotelm.repository.ReservationRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation testReservation;
    private Room testRoom;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testRoom = new Room("101", RoomType.SINGLE, 100.0, true, "Test room");
        testCustomer = new Customer("John Doe", "john@example.com", "123456789", "ID123");
        testReservation = new Reservation(testRoom, testCustomer, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
        testReservation.setId(1L);
    }

    @Test
    void testFindAllReservations() {
        // Given
        List<Reservation> reservations = Arrays.asList(testReservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // When
        List<Reservation> result = reservationService.findAllReservations();

        // Then
        assertEquals(1, result.size());
        assertEquals(testReservation, result.get(0));
    }

    @Test
    void testFindReservationById() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));

        // When
        Optional<Reservation> result = reservationService.findReservationById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testReservation, result.get());
    }

    @Test
    void testSaveReservation() {
        // Given
        when(reservationRepository.findConflictingReservationsForRoom(any(), any(), any())).thenReturn(Arrays.asList());
        when(reservationRepository.save(any())).thenReturn(testReservation);

        // When
        Reservation result = reservationService.saveReservation(testReservation);

        // Then
        assertNotNull(result);
        verify(reservationRepository).save(testReservation);
    }

    @Test
    void testSaveReservationWithNullReservation() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> 
            reservationService.saveReservation(null)
        );
    }

    @Test
    void testIsRoomAvailableForPeriod() {
        // Given
        when(reservationRepository.findConflictingReservationsForRoom(any(), any(), any())).thenReturn(Arrays.asList());

        // When
        boolean result = reservationService.isRoomAvailableForPeriod(testRoom, LocalDate.now(), LocalDate.now().plusDays(1));

        // Then
        assertTrue(result);
    }

    @Test
    void testDeleteReservation() {
        // Given
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(testReservation));

        // When
        reservationService.deleteReservation(1L);

        // Then
        verify(reservationRepository).deleteById(1L);
    }

    @Test
    void testDeleteReservationWithNullId() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> 
            reservationService.deleteReservation(null)
        );
    }

    @Test
    void testCountReservations() {
        // Given
        when(reservationRepository.count()).thenReturn(5L);

        // When
        long result = reservationService.countReservations();

        // Then
        assertEquals(5L, result);
    }

    @Test
    void testReservationExists() {
        // Given
        when(reservationRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = reservationService.reservationExists(1L);

        // Then
        assertTrue(result);
    }
}
