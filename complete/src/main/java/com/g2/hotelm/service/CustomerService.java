package com.g2.hotelm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Find all customers
     */
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Find customer by ID
     */
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    /**
     * Find customer by email
     */
    public Optional<Customer> findCustomerByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        return customerRepository.findByEmail(email.trim());
    }

    /**
     * Find customer by ID number
     */
    public Optional<Customer> findCustomerByIdNumber(String idNumber) {
        if (idNumber == null || idNumber.trim().isEmpty()) {
            return Optional.empty();
        }
        return customerRepository.findByIdNumber(idNumber.trim());
    }

    /**
     * Search customers by name
     */
    public List<Customer> searchCustomersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return List.of();
        }
        return customerRepository.findByFullNameContainingIgnoreCase(name.trim());
    }

    /**
     * Search customers by any field
     */
    public List<Customer> searchCustomers(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return findAllCustomers();
        }
        return customerRepository.searchCustomers(searchTerm.trim());
    }

    /**
     * Save a new customer
     */
    public Customer saveCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        validateCustomer(customer);
        
        // Check for duplicate email
        if (customer.getEmail() != null) {
            Optional<Customer> existingByEmail = findCustomerByEmail(customer.getEmail());
            if (existingByEmail.isPresent() && !existingByEmail.get().getId().equals(customer.getId())) {
                throw new IllegalArgumentException("A customer with this email already exists");
            }
        }
        
        // Check for duplicate ID number
        if (customer.getIdNumber() != null && !customer.getIdNumber().trim().isEmpty()) {
            Optional<Customer> existingByIdNumber = findCustomerByIdNumber(customer.getIdNumber());
            if (existingByIdNumber.isPresent() && !existingByIdNumber.get().getId().equals(customer.getId())) {
                throw new IllegalArgumentException("A customer with this ID number already exists");
            }
        }
        
        return customerRepository.save(customer);
    }

    /**
     * Update an existing customer
     */
    public Customer updateCustomer(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Customer or ID cannot be null");
        }
        
        if (!customerRepository.existsById(customer.getId())) {
            throw new IllegalArgumentException("Customer with ID " + customer.getId() + " does not exist");
        }
        
        return saveCustomer(customer);
    }

    /**
     * Delete customer by ID
     */
    public void deleteCustomer(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Customer with ID " + id + " does not exist");
        }
        
        customerRepository.deleteById(id);
    }

    /**
     * Check if customer exists by ID
     */
    public boolean customerExists(Long id) {
        if (id == null) {
            return false;
        }
        return customerRepository.existsById(id);
    }

    /**
     * Count total customers
     */
    public long countCustomers() {
        return customerRepository.count();
    }

    /**
     * Validate customer data
     */
    private void validateCustomer(Customer customer) {
        if (customer.getFullName() == null || customer.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer full name is required");
        }
        
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer email is required");
        }
        
        // Basic email validation
        String email = customer.getEmail().trim();
        if (!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        // Trim whitespace from string fields
        customer.setFullName(customer.getFullName().trim());
        customer.setEmail(customer.getEmail().trim());
        
        if (customer.getPhone() != null) {
            customer.setPhone(customer.getPhone().trim());
        }
        
        if (customer.getIdNumber() != null) {
            customer.setIdNumber(customer.getIdNumber().trim());
        }
    }
}
