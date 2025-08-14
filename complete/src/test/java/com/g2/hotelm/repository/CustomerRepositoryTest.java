package com.g2.hotelm.repository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.g2.hotelm.model.Customer;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testFindByEmail() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("john@example.com");
        customer.setPhone("123456789");
        customer.setIdNumber("ID123");
        
        entityManager.persist(customer);
        entityManager.flush();

        // When
        Optional<Customer> foundCustomer = customerRepository.findByEmail("john@example.com");

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("John Doe", foundCustomer.get().getFullName());
        assertEquals("john@example.com", foundCustomer.get().getEmail());
    }

    @Test
    void testFindByIdNumber() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("Jane Smith");
        customer.setEmail("jane@example.com");
        customer.setPhone("987654321");
        customer.setIdNumber("ID456");
        
        entityManager.persist(customer);
        entityManager.flush();

        // When
        Optional<Customer> foundCustomer = customerRepository.findByIdNumber("ID456");

        // Then
        assertTrue(foundCustomer.isPresent());
        assertEquals("Jane Smith", foundCustomer.get().getFullName());
        assertEquals("ID456", foundCustomer.get().getIdNumber());
    }

    @Test
    void testFindByFullNameContainingIgnoreCase() {
        // Given
        Customer customer1 = new Customer();
        customer1.setFullName("John Doe");
        customer1.setEmail("john@example.com");
        customer1.setPhone("123456789");
        customer1.setIdNumber("ID123");

        Customer customer2 = new Customer();
        customer2.setFullName("John Smith");
        customer2.setEmail("johnsmith@example.com");
        customer2.setPhone("111111111");
        customer2.setIdNumber("ID124");
        
        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush();

        // When
        List<Customer> customers = customerRepository.findByFullNameContainingIgnoreCase("john");

        // Then
        assertEquals(2, customers.size());
    }

    @Test
    void testSearchCustomers() {
        // Given
        Customer customer1 = new Customer();
        customer1.setFullName("Alice Johnson");
        customer1.setEmail("alice@example.com");
        customer1.setPhone("555123456");
        customer1.setIdNumber("ID789");

        Customer customer2 = new Customer();
        customer2.setFullName("Bob Wilson");
        customer2.setEmail("bob@example.com");
        customer2.setPhone("555987654");
        customer2.setIdNumber("ID790");
        
        entityManager.persist(customer1);
        entityManager.persist(customer2);
        entityManager.flush();

        // When
        List<Customer> customers = customerRepository.searchCustomers("alice");

        // Then
        assertEquals(1, customers.size());
        assertEquals("Alice Johnson", customers.get(0).getFullName());
    }

    @Test
    void testSaveCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setFullName("Test Customer");
        customer.setEmail("test@example.com");
        customer.setPhone("123123123");
        customer.setIdNumber("TEST123");

        // When
        Customer savedCustomer = customerRepository.save(customer);

        // Then
        assertNotNull(savedCustomer.getId());
        assertEquals("Test Customer", savedCustomer.getFullName());
        assertEquals("test@example.com", savedCustomer.getEmail());
    }

    @Test
    void testFindByEmailNotFound() {
        // When
        Optional<Customer> foundCustomer = customerRepository.findByEmail("notfound@example.com");

        // Then
        assertFalse(foundCustomer.isPresent());
    }

    @Test
    void testFindByIdNumberNotFound() {
        // When
        Optional<Customer> foundCustomer = customerRepository.findByIdNumber("NOTFOUND");

        // Then
        assertFalse(foundCustomer.isPresent());
    }
}
