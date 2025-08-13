package com.g2.hotelm.repository;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.g2.hotelm.model.Reservation;





@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testFindByCustomerName_WhenReservationsExist() {
        // Given
        Reservation reservation1 = new Reservation();
        reservation1.setCustomerName("John Doe");
        reservation1.setRoomType("Single");
        reservation1.setStartDate("2024-01-01");
        reservation1.setEndDate("2024-01-03");
        entityManager.persistAndFlush(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setCustomerName("John Doe");
        reservation2.setRoomType("Double");
        reservation2.setStartDate("2024-02-01");
        reservation2.setEndDate("2024-02-05");
        entityManager.persistAndFlush(reservation2);

        Reservation reservation3 = new Reservation();
        reservation3.setCustomerName("Jane Smith");
        reservation3.setRoomType("Suite");
        reservation3.setStartDate("2024-03-01");
        reservation3.setEndDate("2024-03-03");
        entityManager.persistAndFlush(reservation3);

        // When
        List<Reservation> reservations = reservationRepository.findByCustomerName("John Doe");

        // Then
        assertThat(reservations).hasSize(2);
        assertThat(reservations).allMatch(r -> r.getCustomerName().equals("John Doe"));
    }

    @Test
    public void testFindByCustomerName_WhenNoReservationsExist() {
        // When
        List<Reservation> reservations = reservationRepository.findByCustomerName("Nonexistent Customer");

        // Then
        assertThat(reservations).isEmpty();
    }

    @Test
    public void testFindByCustomerName_WithNullName() {
        // When
        List<Reservation> reservations = reservationRepository.findByCustomerName(null);

        // Then
        assertThat(reservations).isEmpty();
    }
}