package com.varshith.dao;

import com.varshith.entities.Customer;
import com.varshith.records.CustomerUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
     List<Customer> selectAllCustomers();
     Optional<Customer> selectCustomerById(Integer id);
     void insertCustomer(Customer customer);
     void updateCustomer(Customer customer);
     void deleteCustomer(Integer id);
     boolean existsPersonWithEmail(String email);
     boolean existsPersonWithId(Integer id);

}
