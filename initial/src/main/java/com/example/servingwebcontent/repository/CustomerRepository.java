package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByCustomerID(String customerID);
    Optional<Customer> findByEmail(String email);
    boolean existsByCustomerID(String customerID);
    boolean existsByEmail(String email);
    List<Customer> findByNameContainingIgnoreCase(String name);
}
