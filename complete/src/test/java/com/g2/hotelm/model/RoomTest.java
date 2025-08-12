package com.g2.hotelm.model;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoomTest {
    
    private Room room;
    
    @BeforeEach
    void setUp() {
        room = new Room();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNull(room.getId());
        assertEquals("standard", room.getType());
        assertEquals(0.0, room.getPrice());
        assertTrue(room.getIsAvailable());
        assertEquals("Standard room", room.getDescription());
    }
    
    @Test
    void testParameterizedConstructor() {
        Room paramRoom = new Room(1L, "deluxe", 150.0, false, "Luxury room");
        
        assertEquals(1L, paramRoom.getId());
        assertEquals("deluxe", paramRoom.getType());
        assertEquals(150.0, paramRoom.getPrice());
        assertFalse(paramRoom.getIsAvailable());
        assertEquals("Luxury room", paramRoom.getDescription());
    }
    
    @Test
    void testSetType() {
        room.setType("suite");
        assertEquals("suite", room.getType());
    }
    
    @Test
    void testSetPrice() {
        room.setPrice(200.0);
        assertEquals(200.0, room.getPrice());
    }
    
    @Test
    void testSetIsAvailable() {
        room.setIsAvailable(false);
        assertFalse(room.getIsAvailable());
    }
    
    @Test
    void testSetDescription() {
        room.setDescription("Updated description");
        assertEquals("Updated description", room.getDescription());
    }
    
    @Test
    void testGetRoomDetails() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        room.getRoomDetails();
        
        System.setOut(originalOut);
        String output = outputStream.toString();
        
        assertTrue(output.contains("Room ID: null"));
        assertTrue(output.contains("Type: standard"));
        assertTrue(output.contains("Price: $0.0"));
        assertTrue(output.contains("Available: Yes"));
        assertTrue(output.contains("Description: Standard room"));
    }
    
    @Test
    void testGetRoomDetailsWithCustomValues() {
        Room customRoom = new Room(5L, "presidential", 500.0, false, "Presidential suite");
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        customRoom.getRoomDetails();
        
        System.setOut(originalOut);
        String output = outputStream.toString();
        
        assertTrue(output.contains("Room ID: 5"));
        assertTrue(output.contains("Type: presidential"));
        assertTrue(output.contains("Price: $500.0"));
        assertTrue(output.contains("Available: No"));
        assertTrue(output.contains("Description: Presidential suite"));
    }
}