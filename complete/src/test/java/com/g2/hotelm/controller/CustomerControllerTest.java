package com.g2.hotelm.controller;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer("John Doe", "john@example.com", "123456789", "ID123");
        testCustomer.setId(1L);
    }

    @Test
    void testListCustomers() throws Exception {
        // Given
        when(customerService.findAllCustomers()).thenReturn(Arrays.asList(testCustomer));

        // When & Then
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void testListCustomersWithSearch() throws Exception {
        // Given
        when(customerService.searchCustomers("John")).thenReturn(Arrays.asList(testCustomer));

        // When & Then
        mockMvc.perform(get("/customers").param("search", "John"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("search", "John"));
    }

    @Test
    void testSaveCustomer() throws Exception {
        // Given
        when(customerService.saveCustomer(any())).thenReturn(testCustomer);

        // When & Then
        mockMvc.perform(post("/customers/save")
                .param("fullName", "John Doe")
                .param("email", "john@example.com")
                .param("phone", "123456789")
                .param("idNumber", "ID123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void testEditCustomer() throws Exception {
        // Given
        when(customerService.findCustomerById(1L)).thenReturn(Optional.of(testCustomer));

        // When & Then
        mockMvc.perform(get("/customers/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-form"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("isEdit", true));
    }

    @Test
    void testEditCustomerNotFound() throws Exception {
        // Given
        when(customerService.findCustomerById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/customers/edit/999"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Given
        when(customerService.updateCustomer(any())).thenReturn(testCustomer);

        // When & Then
        mockMvc.perform(post("/customers/update/1")
                .param("fullName", "John Doe Updated")
                .param("email", "john.updated@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        // Given
        doNothing().when(customerService).deleteCustomer(1L);

        // When & Then
        mockMvc.perform(post("/customers/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers"));
    }

    @Test
    void testNewCustomerForm() throws Exception {
        // When & Then
        mockMvc.perform(get("/customers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-form"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("isEdit", false));
    }
}
