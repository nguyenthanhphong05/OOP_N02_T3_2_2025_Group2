package service;

import model.Reservation;

import java.util.List;
import java.util.stream.Collectors;

public class ReservationSearchService {
    private List<Reservation> reservations;

    public ReservationSearchService(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    // Tìm kiếm theo mã khách hàng
    public List<Reservation> searchByCustomerID(String customerID) {
        return reservations.stream()
                .filter(res -> res.getCustomer().getCustomerID().equalsIgnoreCase(customerID))
                .collect(Collectors.toList());
    }

    // Tìm kiếm theo tên khách hàng
    public List<Reservation> searchByCustomerName(String name) {
        return reservations.stream()
                .filter(res -> res.getCustomer().getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Tìm kiếm theo mã phòng
    public List<Reservation> searchByRoomID(String roomID) {
        return reservations.stream()
                .filter(res -> res.getRoom().getRoomNumber().equalsIgnoreCase(roomID))
                .collect(Collectors.toList());
    }
}