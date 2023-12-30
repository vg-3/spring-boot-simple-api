package com.varshith.services;

import com.varshith.entities.Customer;
import com.varshith.records.CustomerRegistrationRequest;
import com.varshith.records.CustomerUpdateRequest;

import java.util.List;

public interface CustomerService {
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(Integer id);
    public  void saveCustomer( Customer customer);
    public  void updateCustomer(Integer id ,
                                CustomerUpdateRequest customerUpdateRequest);
    public  boolean existsPersonWithEmail(String email);
}
