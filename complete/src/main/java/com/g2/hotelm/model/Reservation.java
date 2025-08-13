package com.g2.hotelm.model;

public class Reservation {
    private Long id;
    private String customerName;
    private String roomType;
    private String startDate;
    private String endDate;

    // Constructors, getters, and setters
    public Reservation(Long id, String customerName, String roomType, String startDate, String endDate) {
        this.id = id;
        this.customerName = customerName;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
