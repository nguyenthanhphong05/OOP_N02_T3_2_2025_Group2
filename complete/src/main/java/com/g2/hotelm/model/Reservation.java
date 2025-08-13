package com.g2.hotelm.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;    

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    // Constructors, getters, and setters
    public Reservation() {
        // Default constructor for JPA
    }

    public Reservation(Long id, String customerName, String roomType, String startDate, String endDate) {
        this.id = id;
        this.customerName = customerName;
        this.roomType = roomType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return id.equals(that.id) &&
                customerName.equals(that.customerName) &&
                roomType.equals(that.roomType) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, roomType, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
