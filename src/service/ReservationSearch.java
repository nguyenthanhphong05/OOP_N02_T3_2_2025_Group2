package service;

import model.Reservation;

import java.util.List;
import java.util.stream.Collectors;
//Phuong thức tìm kiếm các đặt phòng theo các tiêu chí khác nhau như mã khách hàng, tên khách hàng, mã phòng
public class ReservationSearch {
    private List<Reservation> reservations;

    public ReservationSearch(List<Reservation> reservations) {
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