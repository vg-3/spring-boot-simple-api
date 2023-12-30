package com.varshith.services;

import com.varshith.entities.Customer;
import com.varshith.exceptions.ResourceNotFoundException;
import com.varshith.records.CustomerRegistrationRequest;
import com.varshith.repositiories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer with id %s was not found".formatted(id)));
    }

    @Override
    public void saveCustomer( Customer customer) {
        customerRepository.save(customer);
    }
}
