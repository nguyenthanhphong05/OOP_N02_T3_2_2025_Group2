package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.Customer;
import com.example.servingwebcontent.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    
    // Initialize with some sample data if database is empty
    public void initializeData() {
        if (customerRepository.count() == 0) {
            customerRepository.save(new Customer("CUST001", "John Doe", "john.doe@email.com", "123-456-7890"));
            customerRepository.save(new Customer("CUST002", "Jane Smith", "jane.smith@email.com", "098-765-4321"));
            customerRepository.save(new Customer("CUST003", "Mike Johnson", "mike.johnson@email.com", "555-123-4567"));
        }
    }

    // Customer menu/dashboard
    @GetMapping
    public String customerMenu(Model model) {
        initializeData();
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        model.addAttribute("totalCustomers", customers.size());
        return "customers/menu";
    }

    // List all customers
    @GetMapping("/list")
    public String listCustomers(Model model) {
        initializeData();
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "customers/list";
    }

    // Show form for creating new customer
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/form";
    }

    // Show form for editing existing customer
    @GetMapping("/edit/{customerID}")
    public String showEditForm(@PathVariable String customerID, Model model) {
        Optional<Customer> customer = customerRepository.findByCustomerID(customerID);
        
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            model.addAttribute("isEdit", true);
            return "customers/form";
        } else {
            return "redirect:/customers";
        }
    }

    // Create or update customer
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        try {
            // Check if customer ID already exists (only for new customers)
            if (!customerRepository.existsByCustomerID(customer.getCustomerID())) {
                customerRepository.save(customer);
                redirectAttributes.addFlashAttribute("message", "Customer saved successfully!");
            } else {
                // For updates, save directly
                customerRepository.save(customer);
                redirectAttributes.addFlashAttribute("message", "Customer updated successfully!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to save customer: " + e.getMessage());
        }

        return "redirect:/customers";
    }

    // Delete customer
    @GetMapping("/delete/{customerID}")
    public String deleteCustomer(@PathVariable String customerID, RedirectAttributes redirectAttributes) {
        try {
            customerRepository.deleteById(customerID);
            redirectAttributes.addFlashAttribute("message", "Customer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete customer: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    // View customer details
    @GetMapping("/view/{customerID}")
    public String viewCustomer(@PathVariable String customerID, Model model) {
        Optional<Customer> customer = customerRepository.findByCustomerID(customerID);
        
        if (customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customers/view";
        } else {
            return "redirect:/customers";
        }
    }

    // Search customers
    @GetMapping("/search")
    public String searchCustomers(@RequestParam(required = false) String keyword, Model model) {
        List<Customer> customers;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            // Simple search implementation - you can enhance this
            customers = customerRepository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                           c.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                           c.getCustomerID().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
            model.addAttribute("keyword", keyword);
        } else {
            customers = customerRepository.findAll();
        }
        
        model.addAttribute("customers", customers);
        return "customers/search";
    }
}
