package com.g2.hotelm.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.g2.hotelm.model.Room;
import com.g2.hotelm.model.RoomType;

@DataJpaTest
class RoomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void testFindByIsAvailable() {
        // Given
        Room availableRoom = new Room("101", RoomType.SINGLE, 100.0, true, "Available room");
        Room unavailableRoom = new Room("102", RoomType.DOUBLE, 200.0, false, "Unavailable room");
        
        entityManager.persist(availableRoom);
        entityManager.persist(unavailableRoom);
        entityManager.flush();

        // When
        List<Room> availableRooms = roomRepository.findByIsAvailable(true);

        // Then
        assertEquals(1, availableRooms.size());
        assertEquals("101", availableRooms.get(0).getRoomId());
        assertTrue(availableRooms.get(0).getIsAvailable());
    }

    @Test
    void testFindByType() {
        // Given
        Room singleRoom = new Room("101", RoomType.SINGLE, 100.0, true, "Single room");
        Room doubleRoom = new Room("201", RoomType.DOUBLE, 200.0, true, "Double room");
        
        entityManager.persist(singleRoom);
        entityManager.persist(doubleRoom);
        entityManager.flush();

        // When
        List<Room> singleRooms = roomRepository.findByType(RoomType.SINGLE);

        // Then
        assertEquals(1, singleRooms.size());
        assertEquals(RoomType.SINGLE, singleRooms.get(0).getType());
    }

    @Test
    void testFindByRoomId() {
        // Given
        Room room = new Room("101", RoomType.SINGLE, 100.0, true, "Test room");
        entityManager.persist(room);
        entityManager.flush();

        // When
        Optional<Room> foundRoom = roomRepository.findByRoomId("101");

        // Then
        assertTrue(foundRoom.isPresent());
        assertEquals("101", foundRoom.get().getRoomId());
    }

    @Test
    void testFindByRoomIdNotFound() {
        // When
        Optional<Room> foundRoom = roomRepository.findByRoomId("999");

        // Then
        assertFalse(foundRoom.isPresent());
    }

    @Test
    void testSaveRoom() {
        // Given
        Room room = new Room("301", RoomType.SUITE, 500.0, true, "Suite room");

        // When
        Room savedRoom = roomRepository.save(room);

        // Then
        assertNotNull(savedRoom.getId());
        assertEquals("301", savedRoom.getRoomId());
        assertEquals(RoomType.SUITE, savedRoom.getType());
    }

    @Test
    void testFindByTypeAndIsAvailable() {
        // Given
        Room availableSingle = new Room("101", RoomType.SINGLE, 100.0, true, "Available single");
        Room unavailableSingle = new Room("102", RoomType.SINGLE, 100.0, false, "Unavailable single");
        Room availableDouble = new Room("201", RoomType.DOUBLE, 200.0, true, "Available double");
        
        entityManager.persist(availableSingle);
        entityManager.persist(unavailableSingle);
        entityManager.persist(availableDouble);
        entityManager.flush();

        // When
        List<Room> availableSingleRooms = roomRepository.findByTypeAndIsAvailable(RoomType.SINGLE, true);

        // Then
        assertEquals(1, availableSingleRooms.size());
        assertEquals(RoomType.SINGLE, availableSingleRooms.get(0).getType());
        assertTrue(availableSingleRooms.get(0).getIsAvailable());
    }
}
