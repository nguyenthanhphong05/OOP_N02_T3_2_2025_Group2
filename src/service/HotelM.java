package service;
import model.Customer;
import model.Reservation;   
import model.Room;

import java.util.ArrayList;
import java.util.List;


public class HotelM {
    private List<Room> rooms;
    private List<Customer> customers;
    private List<Reservation> reservations;

    public HotelM() {
        rooms = new ArrayList<>();
        customers = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    //Just Basic CRUD Operations 

    // Add methods 
    public void addRoom(Room room) { rooms.add(room); }
    public void addCustomer(Customer customer) { customers.add(customer); }
    public void addReservation(Reservation reservation) { reservations.add(reservation); }

    // Read methods
    public List<Room> getRooms() { return rooms; }
    public List<Customer> getCustomers() { return customers; }
    public List<Reservation> getReservations() { return reservations; }

    //Update methods
    public boolean updateRoom(String roomNumber, String newType, boolean newAvailability) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                room.setRoomType(newType);
                room.setAvailable(newAvailability);
                return true;
            }
        }
        return false; 
    }
       //later: Update Customers, Reservations

    // Delete methods
    public boolean deleteRoom(String roomNumber) {
        return rooms.removeIf(room -> room.getRoomNumber().equals(roomNumber));
    }   

     //later: Delete Customers, Reservations

}