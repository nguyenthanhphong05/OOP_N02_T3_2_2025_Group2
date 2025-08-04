package com.example.servingwebcontent.tosubmit;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Reservation;
import com.example.servingwebcontent.model.Room;

@Controller
public class SubmitHotelController {

    @GetMapping("/tosubmit")
    public String hotel(Model model) {
        // keep for demo for Student

        Customer myCustomer = new Customer("CUST001", "John Doe", "john.doe@email.com", "123-456-7890");
        Room myRoom = new Room("101", "Deluxe Suite", 250, true);
        Reservation myReservation = new Reservation("RES001", myRoom, myCustomer, 
                LocalDateTime.now(), LocalDateTime.now().plusDays(3));

        model.addAttribute("customer", myCustomer);
        model.addAttribute("room", myRoom.getRoomType());
        model.addAttribute("roomNumber", myRoom.getRoomNumber());
        model.addAttribute("reservation", myReservation);

        return "tosubmit/hotel";
    }

}
// Biến chứa dữ liệu là customer, room, reservation
