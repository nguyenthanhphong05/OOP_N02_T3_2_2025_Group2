package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.repository.RoomRepository;
import com.example.servingwebcontent.service.SimpleFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private SimpleFileService fileService;
    
    // Initialize with some sample data if database is empty
    public void initializeData() {
        if (roomRepository.count() == 0) {
            roomRepository.save(new Room("101", "Single", 80, true));
            roomRepository.save(new Room("102", "Double", 120, true));
            roomRepository.save(new Room("201", "Suite", 200, false));
            roomRepository.save(new Room("202", "Single", 80, true));
        }
    }

    // List all rooms
    @GetMapping
    public String listRooms(Model model) {
        initializeData();
        List<Room> rooms = roomRepository.findAll();
        model.addAttribute("rooms", rooms);
        return "rooms/list";
    }

    // Show form for creating new room
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room());
        return "rooms/form";
    }

    // Show form for editing existing room
    @GetMapping("/edit/{roomNumber}")
    public String showEditForm(@PathVariable String roomNumber, Model model) {
        Optional<Room> room = roomRepository.findByRoomNumber(roomNumber);
        
        if (room.isPresent()) {
            model.addAttribute("room", room.get());
            model.addAttribute("isEdit", true);
            return "rooms/form";
        } else {
            return "redirect:/rooms";
        }
    }

    // Create or update room
    @PostMapping("/save")
    public String saveRoom(@ModelAttribute Room room, RedirectAttributes redirectAttributes) {
        try {
            roomRepository.save(room);
            
            // Save room info to file
            try {
                fileService.saveRoomToFile(room);
            } catch (IOException e) {
                // Log error but don't fail the operation
                System.err.println("Failed to save room to file: " + e.getMessage());
            }
            
            redirectAttributes.addFlashAttribute("message", "Room saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save room: " + e.getMessage());
        }

        return "redirect:/rooms";
    }

    // Delete room
    @GetMapping("/delete/{roomNumber}")
    public String deleteRoom(@PathVariable String roomNumber, RedirectAttributes redirectAttributes) {
        try {
            roomRepository.deleteById(roomNumber);
            redirectAttributes.addFlashAttribute("message", "Room deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete room: " + e.getMessage());
        }
        return "redirect:/rooms";
    }

    // View room details
    @GetMapping("/view/{roomNumber}")
    public String viewRoom(@PathVariable String roomNumber, Model model) {
        Optional<Room> room = roomRepository.findByRoomNumber(roomNumber);
        
        if (room.isPresent()) {
            model.addAttribute("room", room.get());
            
            // Try to load room file info
            try {
                String fileContent = fileService.readRoomFromFile(roomNumber);
                model.addAttribute("fileContent", fileContent);
            } catch (IOException e) {
                model.addAttribute("fileContent", "No file information available.");
            }
            
            return "rooms/view";
        } else {
            return "redirect:/rooms";
        }
    }

    // Export to CSV
    @GetMapping("/export")
    public String exportRooms(RedirectAttributes redirectAttributes) {
        try {
            fileService.exportRoomsToCSV();
            redirectAttributes.addFlashAttribute("message", "Rooms exported to CSV successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to export rooms: " + e.getMessage());
        }
        return "redirect:/rooms";
    }

    // Import from CSV
    @PostMapping("/import")
    public String importRooms(RedirectAttributes redirectAttributes) {
        try {
            List<Room> importedRooms = fileService.importRoomsFromCSV();
            for (Room room : importedRooms) {
                if (!roomRepository.existsByRoomNumber(room.getRoomNumber())) {
                    roomRepository.save(room);
                }
            }
            redirectAttributes.addFlashAttribute("message", "Rooms imported successfully! " + importedRooms.size() + " rooms processed.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to import rooms: " + e.getMessage());
        }
        return "redirect:/rooms";
    }

    // Backup database
    @GetMapping("/backup")
    public String backupDatabase(RedirectAttributes redirectAttributes) {
        try {
            fileService.backupDatabase();
            redirectAttributes.addFlashAttribute("message", "Database backup created successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create backup: " + e.getMessage());
        }
        return "redirect:/rooms";
    }
}
