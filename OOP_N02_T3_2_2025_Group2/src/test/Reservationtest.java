package test;

import model.Customer;
import model.Room;
import model.Reservation;
import java.time.LocalDateTime;

public class Reservationtest {
    public static void testReservation() {
        // Create a Room object
        Room room = new Room("101", "Deluxe", true);

        // Create a Customer object
        Customer customer = new Customer("C001", "John Doe", "john.doe@example.com", "123-456-7890");

        Reservation reservation = new Reservation("R001", room, customer, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        // Display reservation information
        System.out.println(reservation.getReservationInfo());
    }
}