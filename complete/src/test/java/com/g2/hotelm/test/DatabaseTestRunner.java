package com.g2.hotelm.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.g2.hotelm.model.Room;
import com.g2.hotelm.repository.RoomRepository;
import com.g2.hotelm.service.RoomService;

@SpringBootApplication
@ComponentScan(basePackages = "com.g2.hotelm")
public class DatabaseTestRunner implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    public static void main(String[] args) {
        SpringApplication.run(DatabaseTestRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== DATABASE TEST RUNNER ===");
        
        // Test 1: Database Connection
        System.out.println("\n1. Testing database connection...");
        try {
            long initialCount = roomRepository.count();
            System.out.println("✓ Database connected. Initial room count: " + initialCount);
        } catch (Exception e) {
            System.out.println("✗ Database connection failed: " + e.getMessage());
            return;
        }

        // Test 2: Save Room
        System.out.println("\n2. Testing room save operation...");
        try {
            Room testRoom = new Room();
            testRoom.setRoomId(101);
            testRoom.setType("Standard");
            testRoom.setPrice(99.99);
            testRoom.setIsAvailable(true);
            testRoom.setDescription("Test room for database verification");

            Room savedRoom = roomService.saveRoom(testRoom);
            System.out.println("✓ Room saved with ID: " + savedRoom.getId());
            System.out.println("  Room details: " + savedRoom.getRoomId() + " - " + savedRoom.getType() + " - $" + savedRoom.getPrice());
        } catch (Exception e) {
            System.out.println("✗ Room save failed: " + e.getMessage());
            System.out.println("  Error details: " + e.getClass().getSimpleName());
        }

        // Test 3: Find All Rooms
        System.out.println("\n3. Testing find all rooms...");
        try {
            var allRooms = roomService.findAllRooms();
            System.out.println("✓ Found " + allRooms.size() + " room(s) in database");
            for (Room room : allRooms) {
                System.out.println("  - Room " + room.getRoomId() + ": " + room.getType() + " ($" + room.getPrice() + ")");
            }
        } catch (Exception e) {
            System.out.println("✗ Find all rooms failed: " + e.getMessage());
        }

        // Test 4: Save Multiple Rooms
        System.out.println("\n4. Testing multiple room saves...");
        try {
            Room room2 = new Room();
            room2.setRoomId(102);
            room2.setType("Deluxe");
            room2.setPrice(149.99);
            room2.setIsAvailable(false);
            room2.setDescription("Deluxe room for testing");

            Room room3 = new Room();
            room3.setRoomId(103);
            room3.setType("Suite");
            room3.setPrice(199.99);
            room3.setIsAvailable(true);
            room3.setDescription("Suite room for testing");

            roomService.saveRoom(room2);
            roomService.saveRoom(room3);
            
            long totalCount = roomRepository.count();
            System.out.println("✓ Multiple rooms saved. Total count: " + totalCount);
        } catch (Exception e) {
            System.out.println("✗ Multiple room save failed: " + e.getMessage());
        }

        // Test 5: Find Available Rooms
        System.out.println("\n5. Testing find available rooms...");
        try {
            var availableRooms = roomRepository.findByIsAvailableTrue();
            System.out.println("✓ Found " + availableRooms.size() + " available room(s)");
            for (Room room : availableRooms) {
                System.out.println("  - Available: Room " + room.getRoomId() + " (" + room.getType() + ")");
            }
        } catch (Exception e) {
            System.out.println("✗ Find available rooms failed: " + e.getMessage());
        }

        // Test 6: Update Room
        System.out.println("\n6. Testing room update...");
        try {
            var allRooms = roomService.findAllRooms();
            if (!allRooms.isEmpty()) {
                Room roomToUpdate = allRooms.get(0);
                String originalType = roomToUpdate.getType();
                roomToUpdate.setType("Updated " + originalType);
                roomToUpdate.setPrice(roomToUpdate.getPrice() + 50.0);
                
                roomService.updateRoom(roomToUpdate);
                
                var updatedRoom = roomService.findRoomById(roomToUpdate.getId());
                if (updatedRoom.isPresent()) {
                    System.out.println("✓ Room updated successfully");
                    System.out.println("  - Type changed to: " + updatedRoom.get().getType());
                    System.out.println("  - Price changed to: $" + updatedRoom.get().getPrice());
                } else {
                    System.out.println("✗ Updated room not found");
                }
            } else {
                System.out.println("✗ No rooms to update");
            }
        } catch (Exception e) {
            System.out.println("✗ Room update failed: " + e.getMessage());
        }

        // Test 7: Delete Room
        System.out.println("\n7. Testing room deletion...");
        try {
            var allRooms = roomService.findAllRooms();
            if (!allRooms.isEmpty()) {
                Room roomToDelete = allRooms.get(allRooms.size() - 1);
                Long idToDelete = roomToDelete.getId();
                
                roomService.deleteRoom(idToDelete);
                
                var deletedRoom = roomService.findRoomById(idToDelete);
                if (deletedRoom.isEmpty()) {
                    System.out.println("✓ Room deleted successfully");
                } else {
                    System.out.println("✗ Room still exists after deletion");
                }
                
                long finalCount = roomRepository.count();
                System.out.println("  - Final room count: " + finalCount);
            } else {
                System.out.println("✗ No rooms to delete");
            }
        } catch (Exception e) {
            System.out.println("✗ Room deletion failed: " + e.getMessage());
        }

        System.out.println("\n=== DATABASE TEST COMPLETED ===");
        System.out.println("All database operations have been tested.");
        System.out.println("Check the output above for any failures.");
    }
}
