package com.example.servingwebcontent.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.repository.RoomRepository;

@ExtendWith(MockitoExtension.class)
class SimpleFileServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private SimpleFileService simpleFileService;

    private Room testRoom1;
    private Room testRoom2;

    @BeforeEach
    void setUp() {
        testRoom1 = new Room("101", "Single", 80, true);
        testRoom2 = new Room("102", "Double", 120, false);
        
        // Clean up test files before each test
        cleanupTestFiles();
    }

    @AfterEach
    void tearDown() {
        // Clean up test files after each test
        cleanupTestFiles();
    }

    private void cleanupTestFiles() {
        File dataDir = new File("data");
        if (dataDir.exists()) {
            File[] files = dataDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    @Test
    void testSaveRoomToFile() throws IOException {
        // When
        simpleFileService.saveRoomToFile(testRoom1);

        // Then
        File roomFile = new File("data/room_101.txt");
        assertTrue(roomFile.exists());
        
        String content = Files.readString(roomFile.toPath());
        assertAll(
            () -> assertTrue(content.contains("Room Information")),
            () -> assertTrue(content.contains("Room Number: 101")),
            () -> assertTrue(content.contains("Room Type: Single")),
            () -> assertTrue(content.contains("Price per Night: $80")),
            () -> assertTrue(content.contains("Available: Yes")),
            () -> assertTrue(content.contains("Generated on:"))
        );
    }

    @Test
    void testSaveRoomToFile_UnavailableRoom() throws IOException {
        // When
        simpleFileService.saveRoomToFile(testRoom2);

        // Then
        File roomFile = new File("data/room_102.txt");
        assertTrue(roomFile.exists());
        
        String content = Files.readString(roomFile.toPath());
        assertAll(
            () -> assertTrue(content.contains("Room Number: 102")),
            () -> assertTrue(content.contains("Room Type: Double")),
            () -> assertTrue(content.contains("Price per Night: $120")),
            () -> assertTrue(content.contains("Available: No"))
        );
    }

    @Test
    void testReadRoomFromFile() throws IOException {
        // Given
        File dataDir = new File("data");
        dataDir.mkdirs();
        
        File roomFile = new File("data/room_101.txt");
        String fileContent = """
                Room Information
                ================
                Room Number: 101
                Room Type: Single
                Price per Night: $80
                Available: Yes
                """;
        Files.writeString(roomFile.toPath(), fileContent);

        // When
        String content = simpleFileService.readRoomFromFile("101");

        // Then
        assertAll(
            () -> assertTrue(content.contains("Room Information")),
            () -> assertTrue(content.contains("Room Number: 101")),
            () -> assertTrue(content.contains("Room Type: Single")),
            () -> assertTrue(content.contains("Price per Night: $80")),
            () -> assertTrue(content.contains("Available: Yes"))
        );
    }

    @Test
    void testReadRoomFromFile_FileNotExists() throws IOException {
        // When
        String content = simpleFileService.readRoomFromFile("999");

        // Then
        assertEquals("File not found for room: 999", content);
    }

    @Test
    void testImportRoomsFromCSV() throws IOException {
        // Given
        File dataDir = new File("data");
        dataDir.mkdirs();
        
        File csvFile = new File("data/rooms.csv");
        String csvContent = """
                Room Number,Room Type,Price Per Night,Is Available
                201,Suite,200,true
                202,Deluxe,300,false
                203,Standard,150,true
                """;
        Files.writeString(csvFile.toPath(), csvContent);

        // When
        List<Room> importedRooms = simpleFileService.importRoomsFromCSV();

        // Then
        assertEquals(3, importedRooms.size());
        
        Room room1 = importedRooms.get(0);
        assertAll(
            () -> assertEquals("201", room1.getRoomNumber()),
            () -> assertEquals("Suite", room1.getRoomType()),
            () -> assertEquals(200, room1.getPricePerNight()),
            () -> assertTrue(room1.isAvailable())
        );
        
        Room room2 = importedRooms.get(1);
        assertAll(
            () -> assertEquals("202", room2.getRoomNumber()),
            () -> assertEquals("Deluxe", room2.getRoomType()),
            () -> assertEquals(300, room2.getPricePerNight()),
            () -> assertFalse(room2.isAvailable())
        );
        
        Room room3 = importedRooms.get(2);
        assertAll(
            () -> assertEquals("203", room3.getRoomNumber()),
            () -> assertEquals("Standard", room3.getRoomType()),
            () -> assertEquals(150, room3.getPricePerNight()),
            () -> assertTrue(room3.isAvailable())
        );
    }

    @Test
    void testImportRoomsFromCSV_FileNotExists() throws IOException {
        // When
        List<Room> importedRooms = simpleFileService.importRoomsFromCSV();

        // Then
        assertTrue(importedRooms.isEmpty());
    }

    @Test
    void testImportRoomsFromCSV_EmptyFile() throws IOException {
        // Given
        File dataDir = new File("data");
        dataDir.mkdirs();
        
        File csvFile = new File("data/rooms.csv");
        String csvContent = "Room Number,Room Type,Price Per Night,Is Available\n";
        Files.writeString(csvFile.toPath(), csvContent);

        // When
        List<Room> importedRooms = simpleFileService.importRoomsFromCSV();

        // Then
        assertTrue(importedRooms.isEmpty());
    }

    @Test
    void testImportRoomsFromCSV_MalformedData() throws IOException {
        // Given
        File dataDir = new File("data");
        dataDir.mkdirs();
        
        File csvFile = new File("data/rooms.csv");
        String csvContent = """
                Room Number,Room Type,Price Per Night,Is Available
                301,Suite,200
                302,Deluxe,300,false,extra
                303,Standard,invalid,true
                """;
        Files.writeString(csvFile.toPath(), csvContent);

        // When
        List<Room> importedRooms = simpleFileService.importRoomsFromCSV();

        // Then - Only the valid row should be imported
        assertEquals(1, importedRooms.size());
        Room validRoom = importedRooms.get(0);
        assertEquals("302", validRoom.getRoomNumber());
        assertEquals("Deluxe", validRoom.getRoomType());
    }
}
