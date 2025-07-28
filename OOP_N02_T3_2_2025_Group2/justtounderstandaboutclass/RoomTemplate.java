package justtounderstandaboutclass;

public abstract class RoomTemplate {
    private String roomNumber;
    private String roomType;
    private int pricePerNight;
    private boolean isAvailable;

    // Constructor
    public RoomTemplate(String roomNumber, String roomType, int pricePerNight, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    // Getters and setters 
    public String getRoomNumber() {
        return roomNumber;
    }   
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getRoomType() {
        return roomType;
    }
    
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    // Concrete method 
    public String getRoomInfo() {
        return "Room Number: " + roomNumber + ", Room Type: " + roomType + 
               ", Price per Night: " + pricePerNight + ", Available: " + isAvailable;
    }

    // Abstract methods
    public abstract String getAmenities();
    public abstract int getMaxOccupancy();
    public abstract double calculateTotalCost(int nights);
}

class StandardRoom extends RoomTemplate {
    private String bedType;

    public StandardRoom(String roomNumber, String roomType, int pricePerNight, boolean isAvailable, String bedType) {
        super(roomNumber, roomType, pricePerNight, isAvailable);
        this.bedType = bedType;
    }

    @Override
    public String getAmenities() {
        return "Standard amenities with " + bedType + " bed.";
    }

    @Override
    public int getMaxOccupancy() {
        return 1; 
    }

    @Override
    public double calculateTotalCost(int nights) {
        return getPricePerNight() * nights;
    }
}

class LuxuryRoom extends RoomTemplate {
    private String view;

    public LuxuryRoom(String roomNumber, String roomType, int pricePerNight, boolean isAvailable, String view) {
        super(roomNumber, roomType, pricePerNight, isAvailable);
        this.view = view;
    }

    @Override
    public String getAmenities() {
        return "Luxury amenities with a " + view + " view.";
    }

    @Override
    public int getMaxOccupancy() {
        return 2; 
    }

    @Override
    public double calculateTotalCost(int nights) {
        return getPricePerNight() * nights * 1.5; // Luxury rooms cost more
    }
}

class DemoRoomTemplate {
    public static void main(String[] args) {
        StandardRoom room = new StandardRoom("101", "Standard", 100, true, "Queen");
        System.out.println(room.getRoomInfo());
        System.out.println("Amenities: " + room.getAmenities());
        System.out.println("Max Occupancy: " + room.getMaxOccupancy());
        System.out.println("Total Cost for 3 nights: $" + room.calculateTotalCost(3));

        LuxuryRoom luxuryRoom = new LuxuryRoom("201", "Luxury", 200, true, "Ocean");
        System.out.println(luxuryRoom.getRoomInfo());
        System.out.println("Amenities: " + luxuryRoom.getAmenities());
        System.out.println("Max Occupancy: " + luxuryRoom.getMaxOccupancy());
        System.out.println("Total Cost for 3 nights: $" + luxuryRoom.calculateTotalCost(3));
    }
}