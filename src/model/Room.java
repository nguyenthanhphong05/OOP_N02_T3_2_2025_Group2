package model;

public class Room {
    private String roomNumber;
    private String roomType;
    private boolean isAvailable;

    public Room(String roomNumber, String roomType, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.isAvailable = isAvailable;
    }
    // Getters and setters can be added here for roomNumber, roomType, and isAvailable
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

    public String getRoomInfo() {
        return "Room Number: " + roomNumber + ", Room Type: " + roomType + ", Available: " + isAvailable;
    }

}

