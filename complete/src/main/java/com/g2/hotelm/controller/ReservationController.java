package com.g2.hotelm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.ReservationStatus;
import com.g2.hotelm.model.Room;
import com.g2.hotelm.service.ReservationService;
import com.g2.hotelm.service.RoomService;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private RoomService roomService;

    /**
     * Display reservations list page
     */
    @GetMapping
    public String listReservations(Model model) {
        List<Reservation> reservations = reservationService.findAllReservations();
        model.addAttribute("reservations", reservations);
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("rooms", roomService.findAllRooms());
        model.addAttribute("statuses", ReservationStatus.values());
        return "reservations";
    }

    /**
     * Save new reservation
     */
    @PostMapping("/save")
    public String saveReservation(@ModelAttribute Reservation reservation, 
                                @RequestParam String roomId,
                                RedirectAttributes redirectAttributes) {
        try {
            // Get room by roomId (String)
            Optional<Room> roomOpt = roomService.findRoomByRoomId(roomId);
            if (!roomOpt.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Room not found!");
                return "redirect:/reservations";
            }
            
            reservation.setRoom(roomOpt.get());
            
            // Calculate total price
            if (reservation.getCheckInDate() != null && reservation.getCheckOutDate() != null) {
                reservation.setTotalPrice(reservation.calculateTotalPrice());
            } else {
                reservation.setTotalPrice(0.0);
            }
            
            reservationService.saveReservation(reservation);
            redirectAttributes.addFlashAttribute("successMessage", "Reservation created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating reservation: " + e.getMessage());
        }
        return "redirect:/reservations";
    }

    /**
     * Delete reservation
     */
    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservationService.deleteReservation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Reservation deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting reservation: " + e.getMessage());
        }
        return "redirect:/reservations";
    }
}
