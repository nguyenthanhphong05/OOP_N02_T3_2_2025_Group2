package com.example.servingwebcontent.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.servingwebcontent.model.Room;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final List<Room> rooms = new ArrayList<>();
    
    // Initialize with some sample data
    public RoomController() {
        rooms.add(new Room("101", "Single", 80, true));
        rooms.add(new Room("102", "Double", 120, true));
        rooms.add(new Room("201", "Suite", 200, false));
        rooms.add(new Room("202", "Single", 80, true));
    }

    // List all rooms
    @GetMapping
    public String listRooms(Model model) {
        model.addAttribute("rooms", rooms);
        return "rooms/list";
    }

    // Show form for creating new room
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("room", new Room("", "", 0, true));
        return "rooms/form";
    }

    // Show form for editing existing room
    @GetMapping("/edit/{roomNumber}")
    public String showEditForm(@PathVariable String roomNumber, Model model) {
        Optional<Room> room = rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst();
        
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
        // Check if room already exists (for updates)
        Optional<Room> existingRoom = rooms.stream()
                .filter(r -> r.getRoomNumber().equals(room.getRoomNumber()))
                .findFirst();

        if (existingRoom.isPresent()) {
            // Update existing room
            Room existing = existingRoom.get();
            existing.setRoomType(room.getRoomType());
            existing.setPricePerNight(room.getPricePerNight());
            existing.setAvailable(room.isAvailable());
            redirectAttributes.addFlashAttribute("message", "Room updated successfully!");
        } else {
            // Create new room
            rooms.add(room);
            redirectAttributes.addFlashAttribute("message", "Room created successfully!");
        }

        return "redirect:/rooms";
    }

    // Delete room
    @GetMapping("/delete/{roomNumber}")
    public String deleteRoom(@PathVariable String roomNumber, RedirectAttributes redirectAttributes) {
        rooms.removeIf(room -> room.getRoomNumber().equals(roomNumber));
        redirectAttributes.addFlashAttribute("message", "Room deleted successfully!");
        return "redirect:/rooms";
    }

    // View room details
    @GetMapping("/view/{roomNumber}")
    public String viewRoom(@PathVariable String roomNumber, Model model) {
        Optional<Room> room = rooms.stream()
                .filter(r -> r.getRoomNumber().equals(roomNumber))
                .findFirst();
        
        if (room.isPresent()) {
            model.addAttribute("room", room.get());
            return "rooms/view";
        } else {
            return "redirect:/rooms";
        }
    }
}
