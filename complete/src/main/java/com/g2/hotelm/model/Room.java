package com.g2.hotelm.model;

public class Room {
    private Long id;
    private String type;
    private Double price;
    private Boolean isAvailable;
    private String description;

    // Constructors, getters, and setters
    // Def Cons
    public Room() {
        this.id = null;
        this.type = "standard";
        this.price = 0.0;
        this.isAvailable = true;
        this.description = "Standard room";
    }

    // Cons with Param
    public Room(Long id, String type, Double price, Boolean isAvailable, String description) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.description = description;
    }

    //Getter - Setter 
    public Long getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void getRoomDetails() {
        System.out.println("Room ID: " + id);
        System.out.println("Type: " + type);
        System.out.println("Price: $" + price);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("Description: " + description);
    }

}