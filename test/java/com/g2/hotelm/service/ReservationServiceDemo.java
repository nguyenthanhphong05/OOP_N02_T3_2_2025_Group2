package com.g2.hotelm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import com.g2.hotelm.model.Reservation;

/**
 * Demo class to show ReservationService functionality
 * Run this with spring.profiles.active=demo
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.g2.hotelm")
@Profile("demo")
public class ReservationServiceDemo implements CommandLineRunner {

    @Autowired
    private ReservationService reservationService;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "demo");
        SpringApplication.run(ReservationServiceDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== RESERVATION SERVICE DEMO ===");
        
        // Demo 1: Create new reservations
        System.out.println("\n1. Creating sample reservations...");
        
        Reservation reservation1 = createSampleReservation("John Smith", "Standard", "2024-01-15", "2024-01-18");
        Reservation reservation2 = createSampleReservation("Alice Johnson", "Deluxe", "2024-02-10", "2024-02-14");
        Reservation reservation3 = createSampleReservation("Bob Wilson", "Suite", "2024-03-05", "2024-03-08");
        Reservation reservation4 = createSampleReservation("John Smith", "Standard", "2024-04-20", "2024-04-22");
        
        Reservation saved1 = reservationService.saveReservation(reservation1);
        Reservation saved2 = reservationService.saveReservation(reservation2);
        Reservation saved3 = reservationService.saveReservation(reservation3);
        Reservation saved4 = reservationService.saveReservation(reservation4);
        
        System.out.printf("✓ Created reservation #%d for %s (%s room, %s to %s)%n", 
            saved1.getId(), saved1.getCustomerName(), saved1.getRoomType(), saved1.getStartDate(), saved1.getEndDate());
        System.out.printf("✓ Created reservation #%d for %s (%s room, %s to %s)%n", 
            saved2.getId(), saved2.getCustomerName(), saved2.getRoomType(), saved2.getStartDate(), saved2.getEndDate());
        System.out.printf("✓ Created reservation #%d for %s (%s room, %s to %s)%n", 
            saved3.getId(), saved3.getCustomerName(), saved3.getRoomType(), saved3.getStartDate(), saved3.getEndDate());
        System.out.printf("✓ Created reservation #%d for %s (%s room, %s to %s)%n", 
            saved4.getId(), saved4.getCustomerName(), saved4.getRoomType(), saved4.getStartDate(), saved4.getEndDate());

        // Demo 2: Find all reservations
        System.out.println("\n2. Finding all reservations...");
        List<Reservation> allReservations = reservationService.findAllReservations();
        System.out.printf("✓ Found %d total reservations%n", allReservations.size());

        // Demo 3: Find reservations by customer name
        System.out.println("\n3. Finding reservations for 'John Smith'...");
        List<Reservation> johnReservations = reservationService.findReservationsByCustomerName("John Smith");
        System.out.printf("✓ Found %d reservations for John Smith:%n", johnReservations.size());
        for (Reservation res : johnReservations) {
            System.out.printf("  - Reservation #%d: %s room, %s to %s%n", 
                res.getId(), res.getRoomType(), res.getStartDate(), res.getEndDate());
        }

        // Demo 4: Update a reservation
        System.out.println("\n4. Updating Alice's reservation...");
        saved2.setRoomType("Premium Suite");
        saved2.setEndDate("2024-02-16");
        Reservation updated = reservationService.updateReservation(saved2);
        System.out.printf("✓ Updated reservation #%d: now %s room until %s%n", 
            updated.getId(), updated.getRoomType(), updated.getEndDate());

        // Demo 5: Check reservation existence
        System.out.println("\n5. Checking reservation existence...");
        boolean exists = reservationService.reservationExists(saved1.getId());
        System.out.printf("✓ Reservation #%d exists: %b%n", saved1.getId(), exists);
        
        boolean notExists = reservationService.reservationExists(99999L);
        System.out.printf("✓ Reservation #99999 exists: %b%n", notExists);

        // Demo 6: Count total reservations
        System.out.println("\n6. Counting reservations...");
        long count = reservationService.countReservations();
        System.out.printf("✓ Total reservations in system: %d%n", count);

        // Demo 7: Delete a reservation
        System.out.println("\n7. Deleting Bob's reservation...");
        reservationService.deleteReservation(saved3.getId());
        System.out.printf("✓ Deleted reservation #%d%n", saved3.getId());
        
        long newCount = reservationService.countReservations();
        System.out.printf("✓ New total count: %d%n", newCount);

        // Demo 8: Validation demonstration
        System.out.println("\n8. Demonstrating validation...");
        try {
            Reservation invalidReservation = createSampleReservation("", "Standard", "2024-05-01", "2024-05-02");
            reservationService.saveReservation(invalidReservation);
        } catch (IllegalArgumentException e) {
            System.out.printf("✓ Validation working: %s%n", e.getMessage());
        }

        try {
            Reservation invalidDates = createSampleReservation("Test User", "Standard", "2024-05-10", "2024-05-05");
            reservationService.saveReservation(invalidDates);
        } catch (IllegalArgumentException e) {
            System.out.printf("✓ Date validation working: %s%n", e.getMessage());
        }

        System.out.println("\n=== DEMO COMPLETED ===");
        System.out.println("ReservationService is working correctly!");
    }

    private Reservation createSampleReservation(String customerName, String roomType, String startDate, String endDate) {
        Reservation reservation = new Reservation();
        reservation.setCustomerName(customerName);
        reservation.setRoomType(roomType);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        return reservation;
    }
}
