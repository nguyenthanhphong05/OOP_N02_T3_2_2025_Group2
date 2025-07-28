package com.example.servingwebcontent.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.servingwebcontent.model.Reservation;

@Service
public class ReservationSearchService {
    
    @Autowired
    private HotelService hotelService;

    // Search by customer ID
    public List<Reservation> searchByCustomerID(String customerID) {
        return hotelService.getReservations().stream()
                .filter(res -> res.getCustomer().getCustomerID().equalsIgnoreCase(customerID))
                .collect(Collectors.toList());
    }

    // Search by customer name
    public List<Reservation> searchByCustomerName(String name) {
        return hotelService.getReservations().stream()
                .filter(res -> res.getCustomer().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Search by room ID
    public List<Reservation> searchByRoomID(String roomID) {
        return hotelService.getReservations().stream()
                .filter(res -> res.getRoom().getRoomNumber().equalsIgnoreCase(roomID))
                .collect(Collectors.toList());
    }
}
