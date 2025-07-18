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

    // Add methods to manage rooms, customers, and reservations
    public void addRoom(Room room) { rooms.add(room); }
    public void addCustomer(Customer customer) { customers.add(customer); }
    public void addReservation(Reservation reservation) { reservations.add(reservation); }

    public List<Room> getRooms() { return rooms; }
    public List<Customer> getCustomers() { return customers; }
    public List<Reservation> getReservations() { return reservations; }


}