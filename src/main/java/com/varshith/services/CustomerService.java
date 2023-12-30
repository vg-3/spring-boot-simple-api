package com.varshith.services;

import com.varshith.entities.Customer;
import com.varshith.records.CustomerRegistrationRequest;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(Integer id);
    void saveCustomer( Customer customer);
}
