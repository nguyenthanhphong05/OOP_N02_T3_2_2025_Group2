package com.g2.hotelm.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity 
@Table(name = "rooms")
public class Room {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Room ID number la bat buoc!")
    private String roomId;

    @Column(nullable = false)
    @NotNull(message = "Room type la bat buoc!")
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Column(nullable = false)
    @NotNull(message = "Room price la bat buoc!")
    @Min(value = 0, message = "Room price can la so duong")
    private Double price;

    @Column(nullable = false)
    @NotNull(message = "Room availability status la bat buoc!")
    private Boolean isAvailable;

    @Column(nullable = false)
    @NotNull(message = "Room description la bat buoc!")
    private String description;

    // Bidirectional relationship with Reservation
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    // Constructors
    public Room() {
        this.type = RoomType.SINGLE;
        this.price = 0.0;
        this.isAvailable = true;
        this.description = "Standard room";
        this.reservations = new ArrayList<>();
    }

    public Room(String roomId, RoomType type, Double price, Boolean isAvailable, String description) {
        this.roomId = roomId;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.description = description;
        this.reservations = new ArrayList<>();
    }

    //Getter - Setter 
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }
    
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    
    public RoomType getType() {
        return type;
    }
    public void setType(RoomType type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
    
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    // Bidirectional relationship helper methods
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setRoom(this);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        reservation.setRoom(null);
    }

    // Business logic method from UML
    public boolean isAvailableForPeriod(LocalDate checkIn, LocalDate checkOut) {
        return reservations.stream()
                .noneMatch(reservation -> {
                    LocalDate resCheckIn = reservation.getCheckInDate();
                    LocalDate resCheckOut = reservation.getCheckOutDate();
                    return !(checkOut.isBefore(resCheckIn) || checkIn.isAfter(resCheckOut));
                });
    }

    public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room)) return false;
    Room room = (Room) o;
    return id.equals(room.id) && type.equals(room.type) && price.equals(room.price) && isAvailable.equals(room.isAvailable) && description.equals(room.description);
}

@Override
public int hashCode() {
    return java.util.Objects.hash(id, type, price, isAvailable, description);
}

@Override
public String toString() {
    return "Room{" +
            "id=" + id +
            ", roomId=" + roomId +
            ", type='" + type + '\'' +
            ", price=" + price +
            ", isAvailable=" + isAvailable +
            ", description='" + description + '\'' +
            '}';
}

}
