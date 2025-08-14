package com.g2.hotelm.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.model.Reservation;
import com.g2.hotelm.model.Room;
import com.g2.hotelm.model.RoomType;
import com.g2.hotelm.service.CustomerService;
import com.g2.hotelm.service.ReservationService;
import com.g2.hotelm.service.RoomService;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private RoomService roomService;

    @MockBean
    private CustomerService customerService;

    private Reservation testReservation;
    private Room testRoom;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testRoom = new Room("101", RoomType.SINGLE, 100.0, true, "Test room");
        testCustomer = new Customer("John Doe", "john@example.com", "123456789", "ID123");
        testReservation = new Reservation(testRoom, testCustomer, LocalDate.now().plusDays(1), LocalDate.now().plusDays(3));
    }

    @Test
    void testListReservations() throws Exception {
        // Given
        when(reservationService.findAllReservations()).thenReturn(Arrays.asList(testReservation));
        when(roomService.findAllRooms()).thenReturn(Arrays.asList(testRoom));
        when(customerService.findAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        // When & Then
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservations"))
                .andExpect(model().attributeExists("reservations"))
                .andExpect(model().attributeExists("reservation"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    void testSaveReservationSuccess() throws Exception {
        // Given
        when(roomService.findRoomByRoomId("101")).thenReturn(Optional.of(testRoom));
        when(customerService.findCustomerById(1L)).thenReturn(Optional.of(testCustomer));
        when(reservationService.saveReservation(any())).thenReturn(testReservation);

        // When & Then
        mockMvc.perform(post("/reservations/save")
                .param("roomId", "101")
                .param("customerId", "1")
                .param("checkInDate", "2025-08-15")
                .param("checkOutDate", "2025-08-17"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations"));
    }

    @Test
    void testSaveReservationRoomNotFound() throws Exception {
        // Given
        when(roomService.findRoomByRoomId("999")).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(post("/reservations/save")
                .param("roomId", "999")
                .param("checkInDate", "2025-08-15")
                .param("checkOutDate", "2025-08-17"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations"));
    }

    @Test
    void testDeleteReservation() throws Exception {
        // Given
        doNothing().when(reservationService).deleteReservation(1L);

        // When & Then
        mockMvc.perform(post("/reservations/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations"));
    }
}
