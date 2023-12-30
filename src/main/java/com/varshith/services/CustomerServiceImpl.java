package com.varshith.services;

import com.varshith.entities.Customer;
import com.varshith.exceptions.DataNotChangedException;
import com.varshith.exceptions.DuplicateResourceException;
import com.varshith.exceptions.ResourceNotFoundException;
import com.varshith.records.CustomerRegistrationRequest;
import com.varshith.records.CustomerUpdateRequest;
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
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
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
        if(existsPersonWithEmail(customer.getEmail())){
            throw new DuplicateResourceException("user with this email already exists");
        }else {
            customerRepository.save(customer);
        }
    }

    @Override
    public void updateCustomer(Integer id, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = getCustomerById(id);

        boolean changes =  false;

        if(customerUpdateRequest.name() != null &&
                !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            changes= true;
        }


        if(customerUpdateRequest.age() != null &&
                !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            changes= true;
        }
        if(customerUpdateRequest.email() != null &&
                !customerUpdateRequest.email().equals(customer.getEmail())){
            if(existsPersonWithEmail(customerUpdateRequest.email())){
                throw new DuplicateResourceException("user with email already exists");
            }else{
                customer.setEmail(customerUpdateRequest.email());
                changes= true;
            }
        }

        if(!changes){
            throw new DataNotChangedException("data changes not found");
        }else{
            customerRepository.save(customer);
        }
    }

}
