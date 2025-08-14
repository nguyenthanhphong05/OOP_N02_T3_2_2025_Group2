package com.g2.hotelm.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bidirectional relationship with Room
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;


    // Constructors
    public Reservation() {
        // Default constructor for JPA
    }

    public Reservation(Room room, Customer customer, LocalDate checkInDate, LocalDate checkOutDate) {
        this.room = room;
        this.customer = customer;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        if (room != null && checkInDate != null && checkOutDate != null) {
            long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            this.totalPrice = room.getPrice() * nights;
        } else {
            this.totalPrice = 0.0;
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


    // Business logic methods from UML
    public Double calculateTotalPrice() {
        if (room != null && checkInDate != null && checkOutDate != null) {
            long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            return room.getPrice() * nights;
        }
        return 0.0;
    }

    public long getDurationInDays() {
        if (checkInDate != null && checkOutDate != null) {
            return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return id.equals(that.id) &&
                Objects.equals(customer, that.customer) &&
                Objects.equals(room, that.room) &&
                checkInDate.equals(that.checkInDate) &&
                checkOutDate.equals(that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, room, checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getFullName() : null) +
                ", room=" + (room != null ? room.getRoomId() : null) +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
