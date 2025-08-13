package com.g2.hotelm.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ReservationTest {
    
    private Reservation reservation;
    
    @BeforeEach
    void setUp() {
        reservation = new Reservation(1L, "John Doe", "Single", "2023-12-01", "2023-12-05");
    }
    
    @Test
    void testConstructor() {
        assertEquals(1L, reservation.getId());
        assertEquals("John Doe", reservation.getCustomerName());
        assertEquals("Single", reservation.getRoomType());
        assertEquals("2023-12-01", reservation.getStartDate());
        assertEquals("2023-12-05", reservation.getEndDate());
    }
    
    @Test
    void testGetAndSetId() {
        reservation.setId(2L);
        assertEquals(2L, reservation.getId());
    }
    
    @Test
    void testGetAndSetCustomerName() {
        reservation.setCustomerName("Jane Smith");
        assertEquals("Jane Smith", reservation.getCustomerName());
    }
    
    @Test
    void testGetAndSetRoomType() {
        reservation.setRoomType("Double");
        assertEquals("Double", reservation.getRoomType());
    }
    
    @Test
    void testGetAndSetStartDate() {
        reservation.setStartDate("2023-12-10");
        assertEquals("2023-12-10", reservation.getStartDate());
    }
    
    @Test
    void testGetAndSetEndDate() {
        reservation.setEndDate("2023-12-15");
        assertEquals("2023-12-15", reservation.getEndDate());
    }
    
    @Test
    void testConstructorWithNullValues() {
        Reservation nullReservation = new Reservation(null, null, null, null, null);
        assertNull(nullReservation.getId());
        assertNull(nullReservation.getCustomerName());
        assertNull(nullReservation.getRoomType());
        assertNull(nullReservation.getStartDate());
        assertNull(nullReservation.getEndDate());
    }
}