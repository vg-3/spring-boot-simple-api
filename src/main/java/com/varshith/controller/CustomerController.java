package com.varshith.controller;

import com.varshith.entities.Customer;
import com.varshith.exceptions.DuplicateResourceException;
import com.varshith.exceptions.ResourceNotFoundException;
import com.varshith.records.CustomerRegistrationRequest;
import com.varshith.services.CustomerServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {


    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/customer")
    public void saveCustomer(@RequestBody Customer customer){
        customerService.saveCustomer(customer);
    }
    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable("id") Integer id){
        return customerService.getCustomerById(id);
    }
}
