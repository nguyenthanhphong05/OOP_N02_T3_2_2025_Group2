package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleFileService {

    @Autowired
    private RoomRepository roomRepository;

    private static final String CSV_FILE_PATH = "data/rooms.csv";
    private static final String BACKUP_FILE_PATH = "data/rooms_backup.csv";

    @PostConstruct
    public void initializeDataFromCSV() {
        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
                createInitialCSVFile();
            }

            // Load data from CSV if database is empty
            if (roomRepository.count() == 0) {
                List<Room> roomsFromCSV = importRoomsFromCSV();
                if (!roomsFromCSV.isEmpty()) {
                    for (Room room : roomsFromCSV) {
                        roomRepository.save(room);
                        saveRoomToFile(room); // Save individual file for each room
                    }
                    System.out.println("Initialized " + roomsFromCSV.size() + " rooms from CSV file");
                } else {
                    // If no CSV file exists, create default rooms and save to CSV
                    createDefaultRooms();
                    autoSaveToCSV();
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to initialize data from CSV: " + e.getMessage());
            // Create default rooms if CSV loading fails
            createDefaultRooms();
        }
    }

    private void createInitialCSVFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            writer.println("Room Number,Room Type,Price Per Night,Is Available");
            writer.println("101,Single,80,true");
            writer.println("102,Double,120,true");
            writer.println("201,Suite,200,false");
            writer.println("202,Single,80,true");
            writer.println("301,Deluxe,150,true");
        }
    }

    private void createDefaultRooms() {
        if (roomRepository.count() == 0) {
            roomRepository.save(new Room("101", "Single", 80, true));
            roomRepository.save(new Room("102", "Double", 120, true));
            roomRepository.save(new Room("201", "Suite", 200, false));
            roomRepository.save(new Room("202", "Single", 80, true));
            roomRepository.save(new Room("301", "Deluxe", 150, true));
            System.out.println("Created default rooms");
        }
    }

    public void autoSaveToCSV() {
        try {
            exportRoomsToCSV();
        } catch (IOException e) {
            System.err.println("Auto-save to CSV failed: " + e.getMessage());
        }
    }

    public void saveRoomAndAutoSave(Room room) throws IOException {
        roomRepository.save(room);
        saveRoomToFile(room);
        autoSaveToCSV();
    }

    public void deleteRoomAndAutoSave(String roomNumber) throws IOException {
        roomRepository.deleteById(roomNumber);
        deleteRoomFile(roomNumber);
        autoSaveToCSV();
    }

    private void deleteRoomFile(String roomNumber) {
        String filename = "data/room_" + roomNumber + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    public void exportRoomsToCSV() throws IOException {
        List<Room> rooms = roomRepository.findAll();
        
        // Create directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            // Write header
            writer.println("Room Number,Room Type,Price Per Night,Is Available");

            // Write room data
            for (Room room : rooms) {
                writer.printf("%s,%s,%d,%s%n",
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getPricePerNight(),
                    room.isAvailable() ? "true" : "false"
                );
            }
        }
    }

    public List<Room> importRoomsFromCSV() throws IOException {
        List<Room> rooms = new ArrayList<>();
        File csvFile = new File(CSV_FILE_PATH);
        
        if (!csvFile.exists()) {
            return rooms; // Return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Room room = new Room();
                    room.setRoomNumber(parts[0].trim());
                    room.setRoomType(parts[1].trim());
                    room.setPricePerNight(Integer.parseInt(parts[2].trim()));
                    room.setAvailable(Boolean.parseBoolean(parts[3].trim()));
                    rooms.add(room);
                }
            }
        }
        return rooms;
    }

    public void backupDatabase() throws IOException {
        exportRoomsToCSV();
        
        // Copy CSV to backup
        File sourceFile = new File(CSV_FILE_PATH);
        File backupFile = new File(BACKUP_FILE_PATH);
        
        if (sourceFile.exists()) {
            try (FileInputStream fis = new FileInputStream(sourceFile);
                 FileOutputStream fos = new FileOutputStream(backupFile)) {
                
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }
        }
    }

    public void restoreFromBackup() throws IOException {
        File backupFile = new File(BACKUP_FILE_PATH);
        if (backupFile.exists()) {
            // Clear existing data
            roomRepository.deleteAll();
            
            // Import from backup
            List<Room> rooms = importRoomsFromCSV();
            roomRepository.saveAll(rooms);
        }
    }

    public void saveRoomToFile(Room room) throws IOException {
        String filename = "data/room_" + room.getRoomNumber() + ".txt";
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Room Information");
            writer.println("================");
            writer.println("Room Number: " + room.getRoomNumber());
            writer.println("Room Type: " + room.getRoomType());
            writer.println("Price per Night: $" + room.getPricePerNight());
            writer.println("Available: " + (room.isAvailable() ? "Yes" : "No"));
            writer.println("Generated on: " + java.time.LocalDateTime.now());
        }
    }

    public String readRoomFromFile(String roomNumber) throws IOException {
        String filename = "data/room_" + roomNumber + ".txt";
        File file = new File(filename);
        
        if (!file.exists()) {
            return "File not found for room: " + roomNumber;
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
