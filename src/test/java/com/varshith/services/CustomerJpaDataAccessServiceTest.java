package com.varshith.services;

import com.varshith.entities.Customer;
import com.varshith.repositiories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

class CustomerJpaDataAccessServiceTest {

  private  CustomerJpaDataAccessService underTest;
  private  AutoCloseable autoCloseable;
  @Mock
  private CustomerRepository customerRepository;

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    underTest = new CustomerJpaDataAccessService(customerRepository);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void selectAllCustomers() {
   underTest.selectAllCustomers();
    verify(customerRepository).findAll();
  }

  @Test
  void selectCustomerById() {
   Long id  = 1L;
   underTest.selectCustomerById(id);
   verify(customerRepository).findById(id);
  }

  @Test
  void insertCustomer() {
    Customer customer =  new Customer(
        "name","email@gmail.com",20
    );
    underTest.insertCustomer(customer);
    verify(customerRepository).save(customer);
  }

  @Test
  void updateCustomer() {
    Customer customer =  new Customer(
        "name","email@gmail.com",20
    );
    underTest.updateCustomer(customer);
    verify(customerRepository).save(customer);
  }

  @Test
  void deleteCustomer() {
   Long customerId  = 1L;
   underTest.deleteCustomer(customerId);
    verify(customerRepository).deleteById(customerId);
  }

  @Test
  void existsPersonWithEmail() {
   String email= "email@gmail.com";
   underTest.existsPersonWithEmail(email);
   verify(customerRepository).existsCustomerByEmail(email);
  }

  @Test
  void existsPersonWithId() {
    Long customerId  = 1L;
    underTest.existsPersonWithId(customerId);
    verify(customerRepository).existsCustomerById(customerId);
  }
}