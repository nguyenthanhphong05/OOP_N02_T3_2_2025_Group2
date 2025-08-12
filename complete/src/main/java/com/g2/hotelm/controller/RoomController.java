package com.g2.hotelm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.g2.hotelm.model.Room;
import com.g2.hotelm.service.RoomService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String listRooms(Model model) {
        List<Room> rooms = roomService.findAllRooms();
        model.addAttribute("rooms", rooms);
        model.addAttribute("room", new Room());
        return "rooms";
    }

    @PostMapping("/save")
    public String saveRoom(@Valid Room room, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            List<Room> rooms = roomService.findAllRooms();
            model.addAttribute("rooms", rooms);
            return "rooms";
        }
        
        try {
            roomService.saveRoom(room);
            redirectAttributes.addFlashAttribute("successMessage", "Room saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving room: " + e.getMessage());
        }
        
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String editRoom(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Room> roomOpt = roomService.findRoomById(id);
        if (roomOpt.isPresent()) {
            List<Room> rooms = roomService.findAllRooms();
            model.addAttribute("rooms", rooms);
            model.addAttribute("room", roomOpt.get());
            model.addAttribute("editMode", true);
            return "rooms";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Room not found!");
            return "redirect:/rooms";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(id);
            redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting room: " + e.getMessage());
        }
        return "redirect:/rooms";
    }

    @PostMapping("/update")
    public String updateRoom(@Valid Room room, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            List<Room> rooms = roomService.findAllRooms();
            model.addAttribute("rooms", rooms);
            model.addAttribute("editMode", true);
            return "rooms";
        }
        
        try {
            roomService.updateRoom(room);
            redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating room: " + e.getMessage());
        }
        
        return "redirect:/rooms";
    }
}
