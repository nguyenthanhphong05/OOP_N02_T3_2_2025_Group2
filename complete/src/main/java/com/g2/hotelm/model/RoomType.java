package com.g2.hotelm.model;

public enum RoomType {
    SINGLE("Single Room"),
    DOUBLE("Double Room"),
    SUITE("Suite"),
    DELUXE("Deluxe Room");
    
    private final String displayName;
    
    RoomType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
