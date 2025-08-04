package ui;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.*;
import model.Customer;
import model.Reservation;
import model.Room;
import service.AutoCheckout;
import service.AvailableRoomsDisplay;
import service.HotelM;

public class SmallHotelUI extends JFrame {
    private HotelM hotel;
    private AvailableRoomsDisplay roomsDisplay;
    private AutoCheckout autoCheckout;
    private JTextArea outputArea;
    
    public SmallHotelUI() {
        // Initialize services
        hotel = new HotelM();
        roomsDisplay = new AvailableRoomsDisplay();
        autoCheckout = new AutoCheckout(hotel);
        
        // Add sample data
        initSampleData();
        
        // Setup UI
        setupUI();
    }
    
    private void initSampleData() {
        // Add sample rooms
        hotel.addRoom(new Room("101", "Single", 100, true));
        hotel.addRoom(new Room("102", "Double", 150, false));
        hotel.addRoom(new Room("103", "Suite", 300, true));
        
        // Add sample customer
        hotel.addCustomer(new Customer("C001", "John Doe", "john@email.com", "123456789"));
        
        // Add sample reservation (ending in 2 hours - for auto checkout demo)
        LocalDateTime checkIn = LocalDateTime.now().minusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusHours(2);
        hotel.addReservation(new Reservation("R001", hotel.getRooms().get(1), hotel.getCustomers().get(0), checkIn, checkOut));
    }
    
    private void setupUI() {
        setTitle("Hotel Management Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Top panel with buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton showRoomsBtn = new JButton("Show Available Rooms");
        JButton autoCheckoutBtn = new JButton("Run Auto Checkout");
        JButton showDataBtn = new JButton("Show All Data");
        JButton clearBtn = new JButton("Clear");
        
        // Add action listeners
        showRoomsBtn.addActionListener(e -> showAvailableRooms());
        autoCheckoutBtn.addActionListener(e -> runAutoCheckout());
        showDataBtn.addActionListener(e -> showAllData());
        clearBtn.addActionListener(e -> outputArea.setText(""));
        
        buttonPanel.add(showRoomsBtn);
        buttonPanel.add(autoCheckoutBtn);
        buttonPanel.add(showDataBtn);
        buttonPanel.add(clearBtn);
        
        // Output area
        outputArea = new JTextArea(20, 60);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        
        // Add components
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // Initial display
        outputArea.setText("Hotel Management System Ready!\n");
        outputArea.append("Click buttons above to test functionality.\n\n");
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void showAvailableRooms() {
        outputArea.append("=== AVAILABLE ROOMS ===\n");
        List<Room> availableRooms = roomsDisplay.getAvailableRoomsNow(hotel.getRooms(), hotel.getReservations());
        
        if (availableRooms.isEmpty()) {
            outputArea.append("No rooms available right now.\n");
        } else {
            outputArea.append("Room | Type   | Price | Status\n");
            outputArea.append("-----|--------|-------|----------\n");
            for (Room room : availableRooms) {
                outputArea.append(String.format("%-4s | %-6s | $%-3d | Available\n",
                    room.getRoomNumber(), room.getRoomType(), room.getPricePerNight()));
            }
        }
        outputArea.append("\n");
    }
    
    private void runAutoCheckout() {
        outputArea.append("=== AUTO CHECKOUT ===\n");
        int checkedOut = autoCheckout.processAutoCheckout();
        outputArea.append("Completed " + checkedOut + " automatic checkouts.\n\n");
    }
    
    private void showAllData() {
        outputArea.append("=== HOTEL STATUS ===\n");
        outputArea.append("Total Rooms: " + hotel.getRooms().size() + "\n");
        outputArea.append("Total Customers: " + hotel.getCustomers().size() + "\n");
        outputArea.append("Total Reservations: " + hotel.getReservations().size() + "\n\n");
        
        outputArea.append("ROOMS:\n");
        for (Room room : hotel.getRooms()) {
            outputArea.append("  " + room.getRoomNumber() + " - " + room.getRoomType() + 
                            " ($" + room.getPricePerNight() + ") - " + 
                            (room.isAvailable() ? "Available" : "Occupied") + "\n");
        }
        
        outputArea.append("\nCUSTOMERS:\n");
        for (Customer customer : hotel.getCustomers()) {
            outputArea.append("  " + customer.getCustomerID() + " - " + customer.getName() + "\n");
        }
        
        outputArea.append("\nRESERVATIONS:\n");
        for (Reservation res : hotel.getReservations()) {
            outputArea.append("  " + res.getResID() + " - " + res.getCustomer().getName() + 
                            " - Room " + res.getRoom().getRoomNumber() + "\n");
        }
        outputArea.append("\n");
    }
    
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new SmallHotelUI().setVisible(true);
    });
}
}