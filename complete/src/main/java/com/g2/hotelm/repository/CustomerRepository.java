package com.g2.hotelm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.g2.hotelm.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Find by unique fields
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByIdNumber(String idNumber);
    
    // Search by name patterns
    List<Customer> findByFullNameContainingIgnoreCase(String name);
    
    // Find by phone
    List<Customer> findByPhone(String phone);
    
    // Custom query for full text search
    @Query("SELECT c FROM Customer c WHERE " +
           "LOWER(c.fullName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "c.phone LIKE CONCAT('%', :searchTerm, '%')")
    List<Customer> searchCustomers(@Param("searchTerm") String searchTerm);
}
