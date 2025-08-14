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

import com.g2.hotelm.model.Room;
import com.g2.hotelm.model.RoomType;
import com.g2.hotelm.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room testRoom;

    @BeforeEach
    void setUp() {
        testRoom = new Room("101", RoomType.SINGLE, 100.0, true, "Test room");
        testRoom.setId(1L);
    }

    @Test
    void testFindAllRooms() {
        // Given
        List<Room> rooms = Arrays.asList(testRoom);
        when(roomRepository.findAll()).thenReturn(rooms);

        // When
        List<Room> result = roomService.findAllRooms();

        // Then
        assertEquals(1, result.size());
        assertEquals(testRoom, result.get(0));
    }

    @Test
    void testFindRoomById() {
        // Given
        when(roomRepository.findById(1L)).thenReturn(Optional.of(testRoom));

        // When
        Optional<Room> result = roomService.findRoomById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testRoom, result.get());
    }

    @Test
    void testFindRoomByRoomId() {
        // Given
        when(roomRepository.findByRoomId("101")).thenReturn(Optional.of(testRoom));

        // When
        Optional<Room> result = roomService.findRoomByRoomId("101");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testRoom, result.get());
    }

    @Test
    void testFindRoomsByType() {
        // Given
        List<Room> rooms = Arrays.asList(testRoom);
        when(roomRepository.findByType(RoomType.SINGLE)).thenReturn(rooms);

        // When
        List<Room> result = roomService.findRoomsByType(RoomType.SINGLE);

        // Then
        assertEquals(1, result.size());
        assertEquals(testRoom, result.get(0));
    }

    @Test
    void testFindAvailableRoomsByType() {
        // Given
        List<Room> rooms = Arrays.asList(testRoom);
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);
        when(roomRepository.findAvailableRoomsByType(RoomType.SINGLE, checkIn, checkOut)).thenReturn(rooms);

        // When
        List<Room> result = roomService.findAvailableRoomsByType(RoomType.SINGLE, checkIn, checkOut);

        // Then
        assertEquals(1, result.size());
        assertEquals(testRoom, result.get(0));
    }

    @Test
    void testFindAvailableRoomsByDateRange() {
        // Given
        List<Room> rooms = Arrays.asList(testRoom);
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now().plusDays(2);
        when(roomRepository.findAvailableRooms(checkIn, checkOut)).thenReturn(rooms);

        // When
        List<Room> result = roomService.findAvailableRoomsByDateRange(checkIn, checkOut);

        // Then
        assertEquals(1, result.size());
        assertEquals(testRoom, result.get(0));
    }

    @Test
    void testIsRoomAvailable() {
        // Given
        LocalDate checkIn = LocalDate.now().plusDays(5);
        LocalDate checkOut = LocalDate.now().plusDays(7);

        // When
        boolean result = roomService.isRoomAvailable(testRoom, checkIn, checkOut);

        // Then
        assertTrue(result); // No conflicting reservations in empty room
    }

    @Test
    void testSaveRoom() {
        // Given
        when(roomRepository.save(any())).thenReturn(testRoom);

        // When
        Room result = roomService.saveRoom(testRoom);

        // Then
        assertNotNull(result);
        verify(roomRepository).save(testRoom);
    }

    @Test
    void testDeleteRoom() {
        // When
        roomService.deleteRoom(1L);

        // Then
        verify(roomRepository).deleteById(1L);
    }

    @Test
    void testDeleteRoomWithNullId() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            roomService.deleteRoom(null)
        );
        assertEquals("Room ID cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateRoom() {
        // When
        roomService.updateRoom(testRoom);

        // Then
        verify(roomRepository).save(testRoom);
    }

    @Test
    void testUpdateRoomWithNullRoom() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            roomService.updateRoom(null)
        );
        assertEquals("Room or Room ID cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateRoomWithNullId() {
        // Given
        Room roomWithoutId = new Room("102", RoomType.DOUBLE, 150.0, true, "Test room 2");
        
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            roomService.updateRoom(roomWithoutId)
        );
        assertEquals("Room or Room ID cannot be null", exception.getMessage());
    }
}
