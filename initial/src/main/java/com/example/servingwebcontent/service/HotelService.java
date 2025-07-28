package com.example.servingwebcontent.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.model.Reservation;
import com.example.servingwebcontent.model.Room;

@Service
public class HotelService {
    private final List<Room> rooms;
    private final List<Customer> customers;
    private final List<Reservation> reservations;

    public HotelService() {
        rooms = new ArrayList<>();
        customers = new ArrayList<>();
        reservations = new ArrayList<>();
        initSampleData();
    }

    private void initSampleData() {
        // Add sample rooms
        addRoom(new Room("101", "Single", 100, true));
        addRoom(new Room("102", "Double", 150, false));
        addRoom(new Room("103", "Suite", 300, true));
        addRoom(new Room("104", "Double", 150, true));
        addRoom(new Room("105", "Single", 100, true));
        
        // Add sample customer
        addCustomer(new Customer("C001", "John Doe", "john@email.com", "123456789"));
        addCustomer(new Customer("C002", "Jane Smith", "jane@email.com", "987654321"));
        
        // Add sample reservation (ending in 2 hours - for auto checkout demo)
        LocalDateTime checkIn = LocalDateTime.now().minusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusHours(2);
        addReservation(new Reservation("R001", getRooms().get(1), getCustomers().get(0), checkIn, checkOut));
    }

    // Add methods 
    public void addRoom(Room room) { 
        rooms.add(room); 
    }
    
    public void addCustomer(Customer customer) { 
        customers.add(customer); 
    }
    
    public void addReservation(Reservation reservation) { 
        reservations.add(reservation); 
    }

    // Read methods
    public List<Room> getRooms() { 
        return new ArrayList<>(rooms); 
    }
    
    public List<Customer> getCustomers() { 
        return new ArrayList<>(customers); 
    }
    
    public List<Reservation> getReservations() { 
        return new ArrayList<>(reservations); 
    }

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
            reservationToUpdate.setCustomer(newCustomer);
            reservationToUpdate.setRoom(newRoom);
            reservationToUpdate.setCheckInDate(newCheckIn);
            reservationToUpdate.setCheckOutDate(newCheckOut);
            return true;
        }
        return false;
    }

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

    public boolean updateReservation(String resID, String newRoomNumber, 
                                   LocalDateTime newCheckIn, LocalDateTime newCheckOut) {
        for (Reservation reservation : reservations) {
            if (reservation.getResID().equals(resID)) {
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
    public Room findRoomByNumber(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    // Delete methods
    public boolean deleteRoom(String roomNumber) {
        return rooms.removeIf(room -> room.getRoomNumber().equals(roomNumber));
    }

    public boolean deleteCustomer(String customerID) {
        return customers.removeIf(customer -> customer.getCustomerID().equals(customerID));
    }

    public boolean deleteReservation(String resID) {
        return reservations.removeIf(reservation -> reservation.getResID().equals(resID));
    }

    // Public helper methods for finding entities
    public Customer findCustomerById(String customerID) {
        for (Customer customer : customers) {
            if (customer.getCustomerID().equals(customerID)) {
                return customer;
            }
        }
        return null;
    }

    public Reservation findReservationById(String resID) {
        for (Reservation reservation : reservations) {
            if (reservation.getResID().equals(resID)) {
                return reservation;
            }
        }
        return null;
    }
}
