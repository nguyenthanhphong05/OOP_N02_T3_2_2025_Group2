package com.g2.hotelm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Min(value = 1, message = "Room ID number can la so duong")
    private int roomId;

    @Column(nullable = false)
    @NotNull(message = "Room type la bat buoc!")
    private String type;

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

    // Constructors, getters, and setters
    // Def Cons
    public Room() {
        this.id = null;
        this.type = "standard";
        this.price = 0.0;
        this.isAvailable = true;
        this.description = "Standard room";
    }

    // Cons with Param
    public Room(Long id, String type, Double price, Boolean isAvailable, String description) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
        this.description = description;
    }

    //Getter - Setter 
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
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

@Override

//     public void getRoomDetails() {
//         System.out.println("Room ID: " + id);
//         System.out.println("Type: " + type);
//         System.out.println("Price: $" + price);
//         System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
//         System.out.println("Description: " + description);
//     }

// }

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
