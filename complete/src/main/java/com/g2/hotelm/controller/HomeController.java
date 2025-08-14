package com.g2.hotelm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.Room;
import com.g2.hotelm.repository.CustomerRepository;
import com.g2.hotelm.repository.ReservationRepository;
import com.g2.hotelm.repository.RoomRepository;

@Controller
public class HomeController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Load all data for dashboard
        List<Customer> customers = customerRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        List<Reservation> reservations = reservationRepository.findAll();
        
        // Calculate statistics
        long availableRooms = rooms.stream().filter(room -> room.getIsAvailable()).count();
        long occupiedRooms = rooms.size() - availableRooms;
        
        // Add data to model
        model.addAttribute("customers", customers);
        model.addAttribute("rooms", rooms);
        model.addAttribute("reservations", reservations);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("occupiedRooms", occupiedRooms);
        
        return "index";
    }
}
