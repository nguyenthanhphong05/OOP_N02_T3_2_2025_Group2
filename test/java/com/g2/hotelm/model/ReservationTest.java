package com.g2.hotelm.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class ReservationTest {

    @Test
    public void testGetStartDate() {
        // Arrange
        String expectedStartDate = "2023-12-01";
        Reservation reservation = new Reservation(1L, "John Doe", "Single", expectedStartDate, "2023-12-05");
        
        // Act
        String actualStartDate = reservation.getStartDate();
        
        // Assert
        assertEquals(expectedStartDate, actualStartDate);
    }

    @Test
    public void testGetStartDateWithDifferentFormat() {
        // Arrange
        String expectedStartDate = "01/12/2023";
        Reservation reservation = new Reservation(2L, "Jane Smith", "Double", expectedStartDate, "05/12/2023");
        
        // Act
        String actualStartDate = reservation.getStartDate();
        
        // Assert
        assertEquals(expectedStartDate, actualStartDate);
    }

    @Test
    public void testGetStartDateAfterSetStartDate() {
        // Arrange
        Reservation reservation = new Reservation(3L, "Bob Johnson", "Suite", "2023-11-15", "2023-11-20");
        String newStartDate = "2023-11-20";
        
        // Act
        reservation.setStartDate(newStartDate);
        String actualStartDate = reservation.getStartDate();
        
        // Assert
        assertEquals(newStartDate, actualStartDate);
    }
}