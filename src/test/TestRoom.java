package test;

import model.Room;

public class TestRoom {
    // This class can be used for unit testing the Room class
    public static void testRoom() {    
        Room room = new Room("101", "Deluxe", true);
        System.out.println(room.getRoomInfo());
    }
}
