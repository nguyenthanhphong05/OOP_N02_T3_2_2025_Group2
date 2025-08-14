package com.g2.hotelm.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RoomTypeTest {

    @Test
    void testRoomTypeDisplayNames() {
        assertEquals("Single Room", RoomType.SINGLE.getDisplayName());
        assertEquals("Double Room", RoomType.DOUBLE.getDisplayName());
        assertEquals("Suite", RoomType.SUITE.getDisplayName());
        assertEquals("Deluxe Room", RoomType.DELUXE.getDisplayName());
        assertEquals("Standard Room", RoomType.STANDARD.getDisplayName());
    }

    @Test
    void testRoomTypeToString() {
        assertEquals("Single Room", RoomType.SINGLE.toString());
        assertEquals("Double Room", RoomType.DOUBLE.toString());
        assertEquals("Suite", RoomType.SUITE.toString());
        assertEquals("Deluxe Room", RoomType.DELUXE.toString());
        assertEquals("Standard Room", RoomType.STANDARD.toString());
    }

    @Test
    void testRoomTypeValues() {
        RoomType[] types = RoomType.values();
        assertEquals(5, types.length);
        assertTrue(java.util.Arrays.asList(types).contains(RoomType.SINGLE));
        assertTrue(java.util.Arrays.asList(types).contains(RoomType.DOUBLE));
        assertTrue(java.util.Arrays.asList(types).contains(RoomType.SUITE));
        assertTrue(java.util.Arrays.asList(types).contains(RoomType.DELUXE));
        assertTrue(java.util.Arrays.asList(types).contains(RoomType.STANDARD));
    }

    @Test
    void testRoomTypeValueOf() {
        assertEquals(RoomType.SINGLE, RoomType.valueOf("SINGLE"));
        assertEquals(RoomType.DOUBLE, RoomType.valueOf("DOUBLE"));
        assertEquals(RoomType.SUITE, RoomType.valueOf("SUITE"));
        assertEquals(RoomType.DELUXE, RoomType.valueOf("DELUXE"));
        assertEquals(RoomType.STANDARD, RoomType.valueOf("STANDARD"));
    }
}
