package model;

public class Customer {
    private String customerID;
    private String name;
    private String email;
    private String phoneNumber;

    public Customer(String customerID, String name, String email, String phoneNumber) {
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters - Setters
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    } 

    public String getCustomerInfo() {
        return "Customer ID: " + customerID + ", Name: " + name + ", Email: " + email + ", Phone: " + phoneNumber;
    }
    
}
