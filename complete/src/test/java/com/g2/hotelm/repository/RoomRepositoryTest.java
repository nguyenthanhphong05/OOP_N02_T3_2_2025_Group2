package com.g2.hotelm.repository;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.g2.hotelm.model.Room;




@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testFindByIsAvailableTrue() {
        // Given
        Room availableRoom = new Room();
        availableRoom.setRoomId(101);
        availableRoom.setIsAvailable(true);
        entityManager.persistAndFlush(availableRoom);

        Room unavailableRoom = new Room();
        unavailableRoom.setRoomId(102);
        unavailableRoom.setIsAvailable(false);
        entityManager.persistAndFlush(unavailableRoom);

        // When
        List<Room> availableRooms = roomRepository.findByIsAvailableTrue();

        // Then
        assertThat(availableRooms).hasSize(1);
        assertThat(availableRooms.get(0).getIsAvailable()).isTrue();
    }

    @Test
    public void testFindById() {
        // Given
        Room room = new Room();
        room.setRoomId(103);
        Room savedRoom = entityManager.persistAndFlush(room);

        // When
        Optional<Room> foundRoom = roomRepository.findById(savedRoom.getId());

        // Then
        assertThat(foundRoom).isPresent();
        assertThat(foundRoom.get().getId()).isEqualTo(savedRoom.getId());
    }

    @Test
    public void testFindByIdNotFound() {
        // When
        Optional<Room> foundRoom = roomRepository.findById(999L);

        // Then
        assertThat(foundRoom).isEmpty();
    }
}