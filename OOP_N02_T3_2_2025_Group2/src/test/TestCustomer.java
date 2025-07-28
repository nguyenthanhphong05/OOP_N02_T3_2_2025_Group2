package test;

import model.Customer;

public class TestCustomer {
    public static void testCustomer() {
        // Create a Customer object
        Customer customer = new Customer("C001", "John Doe", "john@example.com", "123-456-7890");

        // Display customer information
        System.out.println(customer.getCustomerInfo());
        // Update customer information
        customer.setName("Jane Doe");
        System.out.println(customer.getCustomerInfo());
        // Change email and phone number
        customer.setEmail("jane@example.com");
        customer.setPhoneNumber("987-654-3210");
        System.out.println(customer.getCustomerInfo());
        // Display updated customer information
        System.out.println("Updated Customer Info: " + customer.getCustomerInfo());}
    }