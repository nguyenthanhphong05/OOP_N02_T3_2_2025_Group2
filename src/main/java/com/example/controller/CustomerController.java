package com.example.controller;

import com.example.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private List<Customer> customers = new ArrayList<>();

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer("", "", "", ""));
        return "customer-form";
    }

    @PostMapping
    public String addCustomer(@ModelAttribute Customer customer) {
        customers.add(customer);
        return "redirect:/customers";
    }
}