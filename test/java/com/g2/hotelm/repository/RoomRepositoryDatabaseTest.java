package com.g2.hotelm.repository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.g2.hotelm.model.Room;

@DataJpaTest
@ActiveProfiles("test")
class RoomRepositoryDatabaseTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    private Room testRoom;

    @BeforeEach
    void setUp() {
        testRoom = new Room();
        testRoom.setRoomId(101);
        testRoom.setType("Standard");
        testRoom.setPrice(99.99);
        testRoom.setIsAvailable(true);
        testRoom.setDescription("Test room for database operations");
    }

    @Test
    void testSaveAndRetrieveRoom() {
        // Given - a room to save
        Room roomToSave = testRoom;

        // When - saving the room
        Room savedRoom = roomRepository.save(roomToSave);
        entityManager.flush(); // Force database write
        entityManager.clear(); // Clear persistence context

        // Then - room should be saved with generated ID
        assertNotNull(savedRoom.getId(), "Saved room should have an ID");
        assertTrue(savedRoom.getId() > 0, "ID should be positive");

        // And - room should be retrievable from database
        Optional<Room> retrievedRoom = roomRepository.findById(savedRoom.getId());
        assertTrue(retrievedRoom.isPresent(), "Room should be found in database");
        
        Room foundRoom = retrievedRoom.get();
        assertEquals(101, foundRoom.getRoomId());
        assertEquals("Standard", foundRoom.getType());
        assertEquals(99.99, foundRoom.getPrice());
        assertTrue(foundRoom.getIsAvailable());
        assertEquals("Test room for database operations", foundRoom.getDescription());
    }

    @Test
    void testFindAllRooms() {
        // Given - multiple rooms in database
        Room room1 = createRoom(101, "Standard", 99.99, true, "Room 101");
        Room room2 = createRoom(102, "Deluxe", 149.99, false, "Room 102");
        Room room3 = createRoom(103, "Suite", 199.99, true, "Room 103");

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        entityManager.flush();

        // When - retrieving all rooms
        List<Room> allRooms = roomRepository.findAll();

        // Then - all rooms should be returned
        assertEquals(3, allRooms.size(), "Should find exactly 3 rooms");
        
        // Verify all room IDs are present
        List<Integer> roomIds = allRooms.stream()
                .map(Room::getRoomId)
                .toList();
        
        assertThat(roomIds).containsExactlyInAnyOrder(101, 102, 103);
    }

    @Test
    void testUpdateRoom() {
        // Given - a saved room
        Room savedRoom = roomRepository.save(testRoom);
        entityManager.flush();
        entityManager.clear();

        // When - updating the room
        savedRoom.setType("Premium");
        savedRoom.setPrice(199.99);
        savedRoom.setIsAvailable(false);
        savedRoom.setDescription("Updated premium room");

        Room updatedRoom = roomRepository.save(savedRoom);
        entityManager.flush();
        entityManager.clear();

        // Then - changes should be persisted
        Optional<Room> retrievedRoom = roomRepository.findById(updatedRoom.getId());
        assertTrue(retrievedRoom.isPresent());
        
        Room room = retrievedRoom.get();
        assertEquals("Premium", room.getType());
        assertEquals(199.99, room.getPrice());
        assertFalse(room.getIsAvailable());
        assertEquals("Updated premium room", room.getDescription());
        assertEquals(101, room.getRoomId()); // Original roomId should remain
    }

    @Test
    void testDeleteRoom() {
        // Given - a saved room
        Room savedRoom = roomRepository.save(testRoom);
        Long roomId = savedRoom.getId();
        entityManager.flush();

        // Verify room exists
        assertTrue(roomRepository.findById(roomId).isPresent());

        // When - deleting the room
        roomRepository.deleteById(roomId);
        entityManager.flush();

        // Then - room should no longer exist
        assertFalse(roomRepository.findById(roomId).isPresent(), 
                   "Room should be deleted from database");
        
        // And - count should be zero
        assertEquals(0, roomRepository.count(), "Repository should be empty");
    }

    @Test
    void testFindByIsAvailableTrue() {
        // Given - rooms with different availability
        Room availableRoom1 = createRoom(101, "Standard", 99.99, true, "Available room 1");
        Room availableRoom2 = createRoom(102, "Deluxe", 149.99, true, "Available room 2");
        Room unavailableRoom = createRoom(103, "Suite", 199.99, false, "Unavailable room");

        roomRepository.save(availableRoom1);
        roomRepository.save(availableRoom2);
        roomRepository.save(unavailableRoom);
        entityManager.flush();

        // When - finding only available rooms
        List<Room> availableRooms = roomRepository.findByIsAvailableTrue();

        // Then - only available rooms should be returned
        assertEquals(2, availableRooms.size(), "Should find exactly 2 available rooms");
        
        // Verify all returned rooms are available
        assertTrue(availableRooms.stream().allMatch(Room::getIsAvailable), 
                  "All returned rooms should be available");
        
        // Verify correct room IDs
        List<Integer> roomIds = availableRooms.stream()
                .map(Room::getRoomId)
                .toList();
        assertThat(roomIds).containsExactlyInAnyOrder(101, 102);
    }

    @Test
    void testDatabasePersistenceAfterFlush() {
        // Given - a room to save
        Room room = testRoom;

        // When - saving and flushing to database
        Room savedRoom = roomRepository.save(room);
        entityManager.flush(); // Force write to database
        
        Long savedId = savedRoom.getId();
        assertNotNull(savedId, "Room should have been assigned an ID");

        // Clear the persistence context to ensure we're reading from database
        entityManager.clear();

        // Then - room should still be retrievable from database
        Optional<Room> roomFromDb = roomRepository.findById(savedId);
        assertTrue(roomFromDb.isPresent(), "Room should persist in database after flush");
        
        Room persistedRoom = roomFromDb.get();
        assertEquals(savedRoom.getRoomId(), persistedRoom.getRoomId());
        assertEquals(savedRoom.getType(), persistedRoom.getType());
        assertEquals(savedRoom.getPrice(), persistedRoom.getPrice());
        assertEquals(savedRoom.getIsAvailable(), persistedRoom.getIsAvailable());
        assertEquals(savedRoom.getDescription(), persistedRoom.getDescription());
    }

    @Test
    void testMultipleOperationsInSequence() {
        // Test a sequence of database operations to verify consistency
        
        // 1. Create initial room
        Room room = roomRepository.save(testRoom);
        entityManager.flush();
        Long roomId = room.getId();
        
        // 2. Verify it exists
        assertEquals(1, roomRepository.count());
        
        // 3. Update the room
        room.setType("Updated");
        roomRepository.save(room);
        entityManager.flush();
        
        // 4. Verify update persisted
        Room updatedRoom = roomRepository.findById(roomId).orElseThrow();
        assertEquals("Updated", updatedRoom.getType());
        
        // 5. Add another room
        Room secondRoom = createRoom(102, "Second", 150.0, false, "Second room");
        roomRepository.save(secondRoom);
        entityManager.flush();
        
        // 6. Verify both rooms exist
        assertEquals(2, roomRepository.count());
        
        // 7. Delete first room
        roomRepository.deleteById(roomId);
        entityManager.flush();
        
        // 8. Verify only second room remains
        assertEquals(1, roomRepository.count());
        List<Room> remainingRooms = roomRepository.findAll();
        assertEquals(102, remainingRooms.get(0).getRoomId());
    }

    private Room createRoom(int roomId, String type, double price, boolean isAvailable, String description) {
        Room room = new Room();
        room.setRoomId(roomId);
        room.setType(type);
        room.setPrice(price);
        room.setIsAvailable(isAvailable);
        room.setDescription(description);
        return room;
    }
}
