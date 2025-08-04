package com.example.servingwebcontent.model;
import java.time.LocalDateTime;

public class Reservation {
    private String resID;
    private Room room;
    private Customer customer;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    public Reservation(String resID, Room room, Customer customer, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.resID = resID;
        this.room = room;
        this.customer = customer;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters and Setters
    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getReservationInfo() {
        return "Reservation ID: " + resID + ", Room: " + room.getRoomInfo() + ", Customer: " + customer.getCustomerInfo() +
               ", Check-In: " + checkInDate + ", Check-Out: " + checkOutDate;
    }
}
