package com.g2.hotelm.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RoomTest {
    @Test
    void testConstructorAndGetters() {
        Room room = new Room("101", RoomType.SINGLE, 50.0, true, "Single room");
        assertEquals("101", room.getRoomId());
        assertEquals(RoomType.SINGLE, room.getType());
        assertEquals(50.0, room.getPrice());
        assertTrue(room.getIsAvailable());
        assertEquals("Single room", room.getDescription());
    }

    @Test
    void testSetters() {
        Room room = new Room();
        room.setRoomId("102");
        room.setType(RoomType.DOUBLE);
        room.setPrice(80.0);
        room.setIsAvailable(false);
        room.setDescription("Double room");
        assertEquals("102", room.getRoomId());
        assertEquals(RoomType.DOUBLE, room.getType());
        assertEquals(80.0, room.getPrice());
        assertFalse(room.getIsAvailable());
        assertEquals("Double room", room.getDescription());
    }

    @Test
    void testToString() {
        Room room = new Room("201", RoomType.SUITE, 150.0, true, "Luxury suite");
        String str = room.toString();
        assertTrue(str.contains("201"));
    assertTrue(str.contains(RoomType.SUITE.toString()));
        assertTrue(str.contains("150.0"));
        assertTrue(str.contains("Luxury suite"));
    }
}
