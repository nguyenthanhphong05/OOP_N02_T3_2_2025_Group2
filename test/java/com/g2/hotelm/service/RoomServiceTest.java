package com.g2.hotelm.service;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.g2.hotelm.model.Room;
import com.g2.hotelm.repository.RoomRepository;





@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);
    }

    @Test
    void testFindAllRooms() {
        List<Room> rooms = Arrays.asList(room);
        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> result = roomService.findAllRooms();

        assertEquals(rooms, result);
        verify(roomRepository).findAll();
    }

    @Test
    void testSaveRoom() {
        when(roomRepository.save(room)).thenReturn(room);

        Room result = roomService.saveRoom(room);

        assertEquals(room, result);
        verify(roomRepository).save(room);
    }

    @Test
    void testDeleteRoom() {
        Long id = 1L;

        roomService.deleteRoom(id);

        verify(roomRepository).deleteById(id);
    }

    @Test
    void testDeleteRoomWithNullId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> roomService.deleteRoom(null)
        );

        assertEquals("K Ton tai Room", exception.getMessage());
        verify(roomRepository, never()).deleteById(any());
    }

    @Test
    void testUpdateRoom() {
        when(roomRepository.save(room)).thenReturn(room);

        roomService.updateRoom(room);

        verify(roomRepository).save(room);
    }

    @Test
    void testUpdateRoomWithNullRoom() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> roomService.updateRoom(null)
        );

        assertEquals("K Ton tai Room", exception.getMessage());
        verify(roomRepository, never()).save(any());
    }

    @Test
    void testUpdateRoomWithNullId() {
        room.setId(null);

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> roomService.updateRoom(room)
        );

        assertEquals("K Ton tai Room", exception.getMessage());
        verify(roomRepository, never()).save(any());
    }
}