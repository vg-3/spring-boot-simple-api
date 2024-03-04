package com.varshith.services;

import com.varshith.dao.CustomerDao;
import com.varshith.entities.Customer;
import com.varshith.records.CustomerUpdateRequest;
import com.varshith.repositiories.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJpaDataAccessService implements CustomerDao {

    private  final CustomerRepository customerRepository;

    public CustomerJpaDataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {
            customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
            customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
            customerRepository.deleteById(id);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean existsPersonWithId(Long id) {
        return customerRepository.existsCustomerById(id);
    }
}
