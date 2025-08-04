package com.example.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Customer;
import com.example.model.Reservation;
import com.example.model.Room;


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

    // Update Customer
    public boolean updateCustomer(String customerID, String newName, String newEmail, String newPhone) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                customer.setName(newName);
                customer.setEmail(newEmail);
                customer.setPhoneNumber(newPhone);
                return true;
            }
        }
        return false;
    }

    // Update Reservation
    public boolean updateReservation(String resID, String newRoomNumber, 
                                   java.time.LocalDateTime newCheckIn, java.time.LocalDateTime newCheckOut) {
        for (Reservation reservation : reservations) {
            if (reservation.getResID().equals(resID)) {
                // Find the new room
                Room newRoom = findRoomByNumber(newRoomNumber);
                if (newRoom != null) {
                    reservation.setRoom(newRoom);
                    reservation.setCheckInDate(newCheckIn);
                    reservation.setCheckOutDate(newCheckOut);
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method to find room by number
    private Room findRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    // Delete methods
    public boolean deleteRoom(Room roomNumber) {
        return rooms.removeIf(room -> room.getRoomNumber().equals(roomNumber));
    }

    public boolean deleteCustomer(String customerID) {
        return customers.removeIf(customer -> customer.getCustomerID().equals(customerID));
    }

    public boolean deleteReservation(String resID) {
        return reservations.removeIf(reservation -> reservation.getResID().equals(resID));
    }

    public boolean deleteCustomer(Customer customerToDelete) {
        return customers.remove(customerToDelete);
    }


}