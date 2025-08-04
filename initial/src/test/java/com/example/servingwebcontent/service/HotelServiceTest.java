package com.example.servingwebcontent.service;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Reservation;
import com.example.servingwebcontent.model.Room;

class HotelServiceTest {

    private HotelService hotelService;
    private Room testRoom1;
    private Room testRoom2;
    private Customer testCustomer1;
    private Customer testCustomer2;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService();
        
        testRoom1 = new Room("101", "Single", 80, true);
        testRoom2 = new Room("102", "Double", 120, true);
        
        testCustomer1 = new Customer("C001", "John Doe", "john@example.com", "123-456-7890");
        testCustomer2 = new Customer("C002", "Jane Smith", "jane@example.com", "098-765-4321");
    }

    @Test
    void testAddRoom() {
        // When
        hotelService.addRoom(testRoom1);

        // Then
        List<Room> rooms = hotelService.getRooms();
        assertEquals(1, rooms.size());
        assertEquals("101", rooms.get(0).getRoomNumber());
        assertEquals("Single", rooms.get(0).getRoomType());
        assertEquals(80, rooms.get(0).getPricePerNight());
        assertTrue(rooms.get(0).isAvailable());
    }

    @Test
    void testAddMultipleRooms() {
        // When
        hotelService.addRoom(testRoom1);
        hotelService.addRoom(testRoom2);

        // Then
        List<Room> rooms = hotelService.getRooms();
        assertEquals(2, rooms.size());
        
        Room room1 = rooms.get(0);
        assertEquals("101", room1.getRoomNumber());
        assertEquals("Single", room1.getRoomType());
        
        Room room2 = rooms.get(1);
        assertEquals("102", room2.getRoomNumber());
        assertEquals("Double", room2.getRoomType());
    }

    @Test
    void testAddCustomer() {
        // When
        hotelService.addCustomer(testCustomer1);

        // Then
        List<Customer> customers = hotelService.getCustomers();
        assertEquals(1, customers.size());
        assertEquals("C001", customers.get(0).getCustomerID());
        assertEquals("John Doe", customers.get(0).getName());
        assertEquals("john@example.com", customers.get(0).getEmail());
        assertEquals("123-456-7890", customers.get(0).getPhoneNumber());
    }

    @Test
    void testAddMultipleCustomers() {
        // When
        hotelService.addCustomer(testCustomer1);
        hotelService.addCustomer(testCustomer2);

        // Then
        List<Customer> customers = hotelService.getCustomers();
        assertEquals(2, customers.size());
        assertEquals("C001", customers.get(0).getCustomerID());
        assertEquals("C002", customers.get(1).getCustomerID());
    }

    @Test
    void testAddReservation() {
        // Given
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(3);
        Reservation reservation = new Reservation("R001", testRoom1, testCustomer1, checkIn, checkOut);

        // When
        hotelService.addReservation(reservation);

        // Then
        List<Reservation> reservations = hotelService.getReservations();
        assertEquals(1, reservations.size());
        assertEquals("R001", reservations.get(0).getResID());
        assertEquals(testRoom1, reservations.get(0).getRoom());
        assertEquals(testCustomer1, reservations.get(0).getCustomer());
        assertEquals(checkIn, reservations.get(0).getCheckInDate());
        assertEquals(checkOut, reservations.get(0).getCheckOutDate());
    }

    @Test
    void testFindRoomByNumber() {
        // Given
        hotelService.addRoom(testRoom1);
        hotelService.addRoom(testRoom2);

        // When
        Room foundRoom = hotelService.findRoomByNumber("101");

        // Then
        assertNotNull(foundRoom);
        assertEquals("101", foundRoom.getRoomNumber());
        assertEquals("Single", foundRoom.getRoomType());
    }

    @Test
    void testFindRoomByNumber_NotFound() {
        // Given
        hotelService.addRoom(testRoom1);

        // When
        Room foundRoom = hotelService.findRoomByNumber("999");

        // Then
        assertNull(foundRoom);
    }

    @Test
    void testFindCustomerById() {
        // Given
        hotelService.addCustomer(testCustomer1);
        hotelService.addCustomer(testCustomer2);

        // When
        Customer foundCustomer = hotelService.findCustomerById("C001");

        // Then
        assertNotNull(foundCustomer);
        assertEquals("C001", foundCustomer.getCustomerID());
        assertEquals("John Doe", foundCustomer.getName());
    }

    @Test
    void testFindCustomerById_NotFound() {
        // Given
        hotelService.addCustomer(testCustomer1);

        // When
        Customer foundCustomer = hotelService.findCustomerById("C999");

        // Then
        assertNull(foundCustomer);
    }

    @Test
    void testFindReservationById() {
        // Given
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(3);
        Reservation reservation = new Reservation("R001", testRoom1, testCustomer1, checkIn, checkOut);
        hotelService.addReservation(reservation);

        // When
        Reservation foundReservation = hotelService.findReservationById("R001");

        // Then
        assertNotNull(foundReservation);
        assertEquals("R001", foundReservation.getResID());
        assertEquals(testRoom1, foundReservation.getRoom());
        assertEquals(testCustomer1, foundReservation.getCustomer());
    }

    @Test
    void testUpdateRoom() {
        // Given
        hotelService.addRoom(testRoom1);

        // When
        boolean updated = hotelService.updateRoom(testRoom1, "Deluxe", false);

        // Then
        assertTrue(updated);
        assertEquals("Deluxe", testRoom1.getRoomType());
        assertFalse(testRoom1.isAvailable());
    }

    @Test
    void testUpdateCustomer() {
        // Given
        hotelService.addCustomer(testCustomer1);

        // When
        boolean updated = hotelService.updateCustomer("C001", "John Smith", "johnsmith@example.com", "555-123-4567");

        // Then
        assertTrue(updated);
        Customer updatedCustomer = hotelService.findCustomerById("C001");
        assertEquals("John Smith", updatedCustomer.getName());
        assertEquals("johnsmith@example.com", updatedCustomer.getEmail());
        assertEquals("555-123-4567", updatedCustomer.getPhoneNumber());
    }

    @Test
    void testDeleteRoom() {
        // Given
        hotelService.addRoom(testRoom1);
        hotelService.addRoom(testRoom2);

        // When
        boolean deleted = hotelService.deleteRoom("101");

        // Then
        assertTrue(deleted);
        assertEquals(1, hotelService.getRooms().size());
        assertNull(hotelService.findRoomByNumber("101"));
        assertNotNull(hotelService.findRoomByNumber("102"));
    }

    @Test
    void testDeleteCustomer() {
        // Given
        hotelService.addCustomer(testCustomer1);
        hotelService.addCustomer(testCustomer2);

        // When
        boolean deleted = hotelService.deleteCustomer("C001");

        // Then
        assertTrue(deleted);
        assertEquals(1, hotelService.getCustomers().size());
        assertNull(hotelService.findCustomerById("C001"));
        assertNotNull(hotelService.findCustomerById("C002"));
    }

    @Test
    void testDeleteReservation() {
        // Given
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(3);
        Reservation reservation = new Reservation("R001", testRoom1, testCustomer1, checkIn, checkOut);
        hotelService.addReservation(reservation);

        // When
        boolean deleted = hotelService.deleteReservation("R001");

        // Then
        assertTrue(deleted);
        assertEquals(0, hotelService.getReservations().size());
        assertNull(hotelService.findReservationById("R001"));
    }

    @Test
    void testGetRooms_EmptyInitially() {
        // Then
        List<Room> rooms = hotelService.getRooms();
        assertTrue(rooms.isEmpty());
    }

    @Test
    void testGetCustomers_EmptyInitially() {
        // Then
        List<Customer> customers = hotelService.getCustomers();
        assertTrue(customers.isEmpty());
    }

    @Test
    void testGetReservations_EmptyInitially() {
        // Then
        List<Reservation> reservations = hotelService.getReservations();
        assertTrue(reservations.isEmpty());
    }
}
