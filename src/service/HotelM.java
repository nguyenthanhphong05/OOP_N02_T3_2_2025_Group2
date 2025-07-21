package service;
import model.Customer;
import model.Reservation;   
import model.Room;

import java.time.LocalDateTime;
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

    // Update methods
      public boolean updateRoom(Room roomToUpdate, String newType, boolean newIsAvailable) { 
        if (rooms.contains(roomToUpdate)) {
            roomToUpdate.setRoomType(newType);
            roomToUpdate.setAvailable(newIsAvailable);
            return true;
        }
        return false;
    }

     public boolean updateReservation(Reservation reservationToUpdate, Customer newCustomer, Room newRoom, LocalDateTime newCheckIn, LocalDateTime newCheckOut) {
        if (reservations.contains(reservationToUpdate)) {
            // Cập nhật các thuộc tính của đối tượng Reservation đã tồn tại
            reservationToUpdate.setCustomer(newCustomer);
            reservationToUpdate.setRoom(newRoom);
            reservationToUpdate.setCheckInDate(newCheckIn);
            reservationToUpdate.setCheckOutDate(newCheckOut);
            return true;
        }
        return false;
    }

    public boolean updateCustomer(Customer customerToUpdate, String newName, String newPhone) {
        if (customers.contains(customerToUpdate)) {
            customerToUpdate.setName(newName);
            customerToUpdate.setPhoneNumber(newPhone);
            return true;
        }
        return false;
    }
    // Delete methods

    public boolean deleteRoom(Room roomToDelete) {
        return rooms.remove(roomToDelete);
    }

    public boolean deleteReservation(Reservation reservationToDelete) {
        return reservations.remove(reservationToDelete);
    }

    public boolean deleteCustomer(Customer customerToDelete) {
        return customers.remove(customerToDelete);
    }
}