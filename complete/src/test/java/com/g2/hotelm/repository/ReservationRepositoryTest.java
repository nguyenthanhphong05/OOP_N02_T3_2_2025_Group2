package com.g2.hotelm.repository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.Room;
import com.g2.hotelm.model.RoomType;

@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void testFindByRoom() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("123456789");
        customer.setIdNumber("ID123");
        entityManager.persist(customer);

        Room room = new Room("101", RoomType.SINGLE, 100.0, true, "Test room");
        entityManager.persist(room);

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(LocalDate.now());
        reservation.setCheckOutDate(LocalDate.now().plusDays(2));
        reservation.setTotalPrice(200.0);
        entityManager.persist(reservation);
        entityManager.flush();

        // When
        List<Reservation> reservations = reservationRepository.findByRoom(room);

        // Then
        assertEquals(1, reservations.size());
        assertEquals(room.getId(), reservations.get(0).getRoom().getId());
    }

    @Test
    void testFindByCustomer_FullName() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("Jane Smith");
        customer.setEmail("jane@example.com");
        customer.setPhone("987654321");
        customer.setIdNumber("ID456");
        entityManager.persist(customer);

        Room room = new Room("201", RoomType.DOUBLE, 200.0, true, "Double room");
        entityManager.persist(room);

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(LocalDate.now().plusDays(1));
        reservation.setCheckOutDate(LocalDate.now().plusDays(3));
        reservation.setTotalPrice(400.0);
        entityManager.persist(reservation);
        entityManager.flush();

        // When
        List<Reservation> reservations = reservationRepository.findByCustomer_FullName("Jane Smith");

        // Then
        assertEquals(1, reservations.size());
        assertEquals("Jane Smith", reservations.get(0).getCustomer().getFullName());
    }

    @Test
    void testFindConflictingReservations() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("Bob Wilson");
        customer.setEmail("bob@example.com");
        customer.setPhone("555123456");
        customer.setIdNumber("ID789");
        entityManager.persist(customer);

        Room room = new Room("301", RoomType.SUITE, 500.0, true, "Suite room");
        entityManager.persist(room);

        LocalDate checkInDate = LocalDate.of(2024, 6, 15);
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkInDate.plusDays(3));
        reservation.setTotalPrice(1500.0);
        entityManager.persist(reservation);
        entityManager.flush();

        // When
        List<Reservation> reservations = reservationRepository.findConflictingReservations(
            LocalDate.of(2024, 6, 14), 
            LocalDate.of(2024, 6, 16)
        );

        // Then
        assertEquals(1, reservations.size());
        assertEquals(checkInDate, reservations.get(0).getCheckInDate());
    }

    @Test
    void testFindConflictingReservationsForRoom() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("Alice Johnson");
        customer.setEmail("alice@example.com");
        customer.setPhone("555789012");
        customer.setIdNumber("ID987");
        entityManager.persist(customer);

        Room room = new Room("401", RoomType.DELUXE, 300.0, true, "Deluxe room");
        entityManager.persist(room);

        LocalDate checkInDate = LocalDate.of(2024, 7, 10);
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkInDate.plusDays(2));
        reservation.setTotalPrice(600.0);
        entityManager.persist(reservation);
        entityManager.flush();

        // When
        List<Reservation> reservations = reservationRepository.findConflictingReservationsForRoom(
            room,
            LocalDate.of(2024, 7, 9), 
            LocalDate.of(2024, 7, 11)
        );

        // Then
        assertEquals(1, reservations.size());
        assertEquals(room.getId(), reservations.get(0).getRoom().getId());
    }

    @Test
    void testSaveReservation() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setPhone("123123123");
        customer.setIdNumber("TEST123");
        entityManager.persist(customer);

        Room room = new Room("999", RoomType.STANDARD, 150.0, true, "Standard room");
        entityManager.persist(room);

        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(room);
        reservation.setCheckInDate(LocalDate.now().plusDays(7));
        reservation.setCheckOutDate(LocalDate.now().plusDays(10));
        reservation.setTotalPrice(450.0);

        // When
        Reservation savedReservation = reservationRepository.save(reservation);

        // Then
        assertNotNull(savedReservation.getId());
        assertEquals(customer.getId(), savedReservation.getCustomer().getId());
        assertEquals(room.getId(), savedReservation.getRoom().getId());
        assertEquals(450.0, savedReservation.getTotalPrice());
    }

    @Test
    void testFindAll() {
        // Given
        Customer customer1 = new Customer();
        customer1.setFullName("Customer 1");
        customer1.setEmail("customer1@example.com");
        customer1.setPhone("111111111");
        customer1.setIdNumber("ID001");
        entityManager.persist(customer1);

        Customer customer2 = new Customer();
        customer2.setFullName("Customer 2");
        customer2.setEmail("customer2@example.com");
        customer2.setPhone("222222222");
        customer2.setIdNumber("ID002");
        entityManager.persist(customer2);

        Room room1 = new Room("501", RoomType.SINGLE, 100.0, true, "Room 1");
        Room room2 = new Room("502", RoomType.DOUBLE, 200.0, true, "Room 2");
        entityManager.persist(room1);
        entityManager.persist(room2);

        Reservation reservation1 = new Reservation();
        reservation1.setCustomer(customer1);
        reservation1.setRoom(room1);
        reservation1.setCheckInDate(LocalDate.now());
        reservation1.setCheckOutDate(LocalDate.now().plusDays(2));
        reservation1.setTotalPrice(200.0);

        Reservation reservation2 = new Reservation();
        reservation2.setCustomer(customer2);
        reservation2.setRoom(room2);
        reservation2.setCheckInDate(LocalDate.now().plusDays(1));
        reservation2.setCheckOutDate(LocalDate.now().plusDays(4));
        reservation2.setTotalPrice(600.0);

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.flush();

        // When
        List<Reservation> allReservations = reservationRepository.findAll();

        // Then
        assertEquals(2, allReservations.size());
    }

    @Test
    void testCalculateTotalPrice() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("Price Test Customer");
        customer.setEmail("pricetest@example.com");
        customer.setPhone("999888777");
        customer.setIdNumber("PRICE123");
        entityManager.persist(customer);

        Room room = new Room("777", RoomType.SUITE, 250.0, true, "Price test room");
        entityManager.persist(room);

        Reservation reservation = new Reservation(room, customer, 
            LocalDate.now(), LocalDate.now().plusDays(3));
        entityManager.persist(reservation);
        entityManager.flush();

        // When
        Double calculatedPrice = reservation.calculateTotalPrice();

        // Then
        assertEquals(750.0, calculatedPrice); // 250 * 3 nights
        assertEquals(3, reservation.getDurationInDays());
    }
}
