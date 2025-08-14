package com.g2.hotelm.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CustomerTest {
    @Test
    void testConstructorAndGetters() {
        Customer customer = new Customer("Phong", "phong@example.com", "0123456789", "123456789");
        assertEquals("Phong", customer.getFullName());
        assertEquals("phong@example.com", customer.getEmail());
        assertEquals("0123456789", customer.getPhone());
        assertEquals("123456789", customer.getIdNumber());
        assertNotNull(customer.getReservations());
        assertEquals(0, customer.getTotalReservations());
    }

    @Test
    void testSetters() {
        Customer customer = new Customer();
        customer.setFullName("Kien");
        customer.setEmail("kien@example.com");
        customer.setPhone("0987654321");
        customer.setIdNumber("987654321");
        assertEquals("Kien", customer.getFullName());
        assertEquals("kien@example.com", customer.getEmail());
        assertEquals("0987654321", customer.getPhone());
        assertEquals("987654321", customer.getIdNumber());
    }

    @Test
    void testReservationsList() {
        Customer customer = new Customer();
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();
        customer.setReservations(List.of(r1, r2));
        assertEquals(2, customer.getReservations().size());
        assertEquals(2, customer.getTotalReservations());
    }

    @Test
    void testToString() {
        Customer customer = new Customer("Phong", "phong@example.com", "0123456789", "123456789");
        String str = customer.toString();
        assertTrue(str.contains("Phong"));
        assertTrue(str.contains("phong@example.com"));
        assertTrue(str.contains("0123456789"));
        assertTrue(str.contains("123456789"));
    }
}
