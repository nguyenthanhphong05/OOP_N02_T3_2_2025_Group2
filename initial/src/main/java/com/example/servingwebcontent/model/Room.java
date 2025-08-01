package com.example.servingwebcontent.model;

public class Room {
    private String roomNumber;
    private String roomType;
    private int pricePerNight;
    private boolean isAvailable;
   
    // Default constructor for Spring form binding
    public Room() {
        this.pricePerNight = 100;
        this.isAvailable = true;
    }

    // Constructor with 3 parameters (without price for backward compatibility)
    public Room(String roomNumber, String roomType, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
        this.pricePerNight = 100; 
    }

    public Room(String roomNumber, String roomType, int pricePerNight, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    // Getters and setters 
    public String getRoomNumber() {
        return roomNumber;
    }   
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomType() {
        return roomType;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getRoomInfo() {
        return "Room Number: " + roomNumber + ", Room Type: " + roomType + ", Price per Night: " + pricePerNight + ", Available: " + isAvailable;
    }
}
