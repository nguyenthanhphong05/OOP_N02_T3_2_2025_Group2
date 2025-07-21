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

    // Update methods
    public boolean updateRoom(String roomNumber, String newType, boolean newAvailability) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                room.setRoomType(newType);
                room.setRoomStatus(newAvailability ? "available" : "booked");
                return true;
            }
        }
        return false;
    }

    public boolean updateReservation(String reservationID, String newCustomerDetails, LocalDate newCheckIn,
            LocalDate newCheckOut) {
        Reservation toUpdate = null;

        // Tìm reservation cần update
        for (Reservation res : reservations) {
            if (res.getReservationID().equals(reservationID)) {
                toUpdate = res;
                break;
            }
        }

        if (toUpdate == null) {
            return false; // Không tìm thấy
        }

        // Kiểm tra trùng ngày với các Reservation khác
        for (Reservation other : reservations) {
            if (!other.getReservationID().equals(reservationID)
                    && other.getRoom().getRoomNumber().equals(toUpdate.getRoom().getRoomNumber())) {
                if (!(newCheckOut.isBefore(other.getCheckInDate()) || newCheckIn.isAfter(other.getCheckOutDate()))) {
                    return false; // Bị trùng lịch
                }
            }
        }

        // OK -> Update
        toUpdate.setCustomerDetails(newCustomerDetails);
        toUpdate.setCheckInDate(newCheckIn);
        toUpdate.setCheckOutDate(newCheckOut);

        return true;
    }

    public boolean updateCustomer(String customerID, String newName, String newPhone) {
        for (Customer cus : customers) {
            if (cus.getCustomerID().equals(customerID)) {
                cus.setName(newName);
                cus.setPhoneNumber(newPhone);
                return true;
            }
        }
        return false; // Không tìm thấy
    }

    // Delete methods

    public void deleteRoom(Room room) {
        rooms.remove(room);
    }

    public boolean deleteReservation(String reservationID) {
        return reservations.removeIf(res -> {
            if (res.getReservationID().equals(reservationID)) {
                res.getRoom().setRoomStatus("available");
                return true;
            }
            return false;
        });
    }

    public boolean deleteCustomer(String customerID) {
        return customers.removeIf(cus -> cus.getCustomerID().equals(customerID));
    }

}