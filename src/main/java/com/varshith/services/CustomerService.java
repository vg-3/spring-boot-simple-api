package com.varshith.services;

import com.varshith.dao.CustomerDao;
import com.varshith.entities.Customer;
import com.varshith.exceptions.DataNotChangedException;
import com.varshith.exceptions.DuplicateResourceException;
import com.varshith.exceptions.ResourceNotFoundException;
import com.varshith.records.CustomerRegistrationRequest;
import com.varshith.records.CustomerUpdateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService  {

    private final  CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

//    FOR JPA IMPLEMENTATION
//    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
//        this.customerDao = customerDao;
//    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }
    public Customer getCustomerById(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException
                                ("Customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        if(customerDao.existsPersonWithEmail(customerRegistrationRequest.email()))
        {
            throw new DuplicateResourceException("email already taken");
        }
        Customer customer = new Customer(customerRegistrationRequest.name() ,
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomer(Integer id ){
        if(!customerDao.existsPersonWithId(id)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(id));
        }
        customerDao.deleteCustomer(id);
    }
    public void updateCustomer(Integer id , CustomerUpdateRequest customerUpdateRequest){
        Customer customer = getCustomerById(id);
        boolean changes =  false;

        if(customerUpdateRequest.email()!= null &&
                !customerUpdateRequest.email().equals(customer.getEmail())){
            if(customerDao.existsPersonWithEmail(customerUpdateRequest.email())){
                throw  new DuplicateResourceException("Email already taken");
            }
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }
        if(customerUpdateRequest.name()!= null &&
                !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }
        if(customerUpdateRequest.age()!= null &&
                !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }
        if(!changes){
            throw  new DataNotChangedException("Data changes not found");
        }else{
            customerDao.updateCustomer(customer);
        }
    }
}
