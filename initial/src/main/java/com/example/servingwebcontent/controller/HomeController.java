package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Room;
import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.repository.RoomRepository;
import com.example.servingwebcontent.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/")
    public String home(Model model) {
        // Get room statistics
        List<Room> allRooms = roomRepository.findAll();
        long totalRooms = allRooms.size();
        long availableRooms = allRooms.stream().filter(Room::isAvailable).count();
        long occupiedRooms = totalRooms - availableRooms;
        
        // Calculate occupancy rate
        double occupancyRate = totalRooms > 0 ? (occupiedRooms * 100.0 / totalRooms) : 0;
        
        // Get customer statistics
        List<Customer> allCustomers = customerRepository.findAll();
        long totalCustomers = allCustomers.size();
        
        // Room type distribution
        Map<String, Long> roomTypeDistribution = allRooms.stream()
            .collect(Collectors.groupingBy(Room::getRoomType, Collectors.counting()));
        
        // Calculate average price
        double averagePrice = allRooms.stream()
            .mapToInt(Room::getPricePerNight)
            .average()
            .orElse(0.0);
        
        // Add data to model
        model.addAttribute("totalRooms", totalRooms);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("occupiedRooms", occupiedRooms);
        model.addAttribute("occupancyRate", Math.round(occupancyRate));
        model.addAttribute("totalCustomers", totalCustomers);
        model.addAttribute("roomTypeDistribution", roomTypeDistribution);
        model.addAttribute("averagePrice", Math.round(averagePrice));
        
        // Recent rooms and customers (limit to 5 each)
        model.addAttribute("recentRooms", allRooms.stream().limit(5).collect(Collectors.toList()));
        model.addAttribute("recentCustomers", allCustomers.stream().limit(5).collect(Collectors.toList()));
        
        return "index";
    }
}
