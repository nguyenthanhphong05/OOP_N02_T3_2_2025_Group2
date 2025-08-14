package com.g2.hotelm.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.g2.hotelm.model.Customer;
import com.g2.hotelm.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * Display customers list page
     */
    @GetMapping
    public String listCustomers(@RequestParam(required = false) String search, Model model) {
        List<Customer> customers;
        
        if (search != null && !search.trim().isEmpty()) {
            customers = customerService.searchCustomers(search);
            model.addAttribute("search", search);
        } else {
            customers = customerService.findAllCustomers();
        }
        
        model.addAttribute("customers", customers);
        model.addAttribute("customer", new Customer());
        return "customers";
    }

    /**
     * Save new customer
     */
    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        try {
            customerService.saveCustomer(customer);
            redirectAttributes.addFlashAttribute("successMessage", "Customer saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving customer: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    /**
     * Show edit customer form
     */
    @GetMapping("/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Customer> customerOpt = customerService.findCustomerById(id);
        if (!customerOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Customer not found!");
            return "redirect:/customers";
        }
        
        model.addAttribute("customer", customerOpt.get());
        model.addAttribute("isEdit", true);
        return "customer-form";
    }

    /**
     * Update existing customer
     */
    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer, 
                               RedirectAttributes redirectAttributes) {
        try {
            customer.setId(id);
            customerService.updateCustomer(customer);
            redirectAttributes.addFlashAttribute("successMessage", "Customer updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating customer: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    /**
     * Delete customer
     */
    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting customer: " + e.getMessage());
        }
        return "redirect:/customers";
    }

    /**
     * Show new customer form
     */
    @GetMapping("/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("isEdit", false);
        return "customer-form";
    }
}
