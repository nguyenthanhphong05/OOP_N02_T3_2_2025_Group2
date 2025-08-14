package com.g2.hotelm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.g2.hotelm.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByIdNumber(String idNumber);
    List<Customer> findByFullNameContainingIgnoreCase(String fullNamePart);
}
