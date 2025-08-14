package com.g2.hotelm.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("John Doe", "john@example.com", "123456789", "ID123");
        testCustomer.setId(1L);
    }

    @Test
    void testFindAllCustomers() {
        // Given
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<Customer> result = customerService.findAllCustomers();

        // Then
        assertEquals(1, result.size());
        assertEquals(testCustomer, result.get(0));
    }

    @Test
    void testFindCustomerById() {
        // Given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        // When
        Optional<Customer> result = customerService.findCustomerById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(testCustomer, result.get());
    }

    @Test
    void testFindCustomerByEmail() {
        // Given
        when(customerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(testCustomer));

        // When
        Optional<Customer> result = customerService.findCustomerByEmail("john@example.com");

        // Then
        assertTrue(result.isPresent());
        assertEquals(testCustomer, result.get());
    }

    @Test
    void testSaveCustomer() {
        // Given
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(customerRepository.findByIdNumber(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(testCustomer);

        // When
        Customer result = customerService.saveCustomer(testCustomer);

        // Then
        assertNotNull(result);
        verify(customerRepository).save(testCustomer);
    }

    @Test
    void testSaveCustomerWithNullCustomer() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            customerService.saveCustomer(null)
        );
        assertEquals("Customer cannot be null", exception.getMessage());
    }

    @Test
    void testSaveCustomerWithDuplicateEmail() {
        // Given
        Customer existingCustomer = new Customer("Jane Doe", "john@example.com", "987654321", "ID456");
        existingCustomer.setId(2L);
        when(customerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(existingCustomer));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            customerService.saveCustomer(testCustomer)
        );
        assertEquals("A customer with this email already exists", exception.getMessage());
    }

    @Test
    void testDeleteCustomer() {
        // Given
        when(customerRepository.existsById(1L)).thenReturn(true);

        // When
        customerService.deleteCustomer(1L);

        // Then
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void testDeleteCustomerWithNullId() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            customerService.deleteCustomer(null)
        );
        assertEquals("Customer ID cannot be null", exception.getMessage());
    }

    @Test
    void testSearchCustomersByName() {
        // Given
        List<Customer> customers = Arrays.asList(testCustomer);
        when(customerRepository.findByFullNameContainingIgnoreCase("John")).thenReturn(customers);

        // When
        List<Customer> result = customerService.searchCustomersByName("John");

        // Then
        assertEquals(1, result.size());
        assertEquals(testCustomer, result.get(0));
    }

    @Test
    void testCustomerExists() {
        // Given
        when(customerRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = customerService.customerExists(1L);

        // Then
        assertTrue(result);
    }

    @Test
    void testCountCustomers() {
        // Given
        when(customerRepository.count()).thenReturn(3L);

        // When
        long result = customerService.countCustomers();

        // Then
        assertEquals(3L, result);
    }
}
