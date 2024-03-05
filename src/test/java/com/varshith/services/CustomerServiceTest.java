package com.varshith.services;

import com.varshith.dao.CustomerDao;
import com.varshith.entities.Customer;
import com.varshith.exceptions.DataNotChangedException;
import com.varshith.exceptions.DuplicateResourceException;
import com.varshith.exceptions.ResourceNotFoundException;
import com.varshith.records.CustomerRegistrationRequest;
import com.varshith.records.CustomerUpdateRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.C;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  private CustomerService underTest;
  @Mock
  private CustomerDao customerDao;

  @BeforeEach
  void setUp() {
    underTest = new CustomerService(customerDao);
  }

  @Test
  void getAllCustomers() {
    underTest.getAllCustomers();
    verify(customerDao).selectAllCustomers();
  }

  @Test
  void CanGetCustomerById() {
    //Given
    Long customerId = 10L;
    Customer customer = new Customer(
        customerId,"name","email@gmail.com",20
    );
    when(customerDao.selectCustomerById(customerId))
        .thenReturn(Optional.of(customer));
    //When
    Customer actual = underTest.getCustomerById(customerId);
    //Then
    assertThat(actual).isEqualTo(customer);
  }

  @Test
  void willThrowWhenGetCustomerByIdReturnOptional() {
    //Given
    Long customerId = 10L;

    when(customerDao.selectCustomerById(customerId))
        .thenReturn(Optional.empty());
    //When
    //Then
    assertThatThrownBy(()->underTest.getCustomerById(customerId))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage(
            "Customer with id [%s] not found".formatted(customerId)
        );
  }

  @Test
  void addCustomer() {
    String email = "test@gamil.com";
    when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
    CustomerRegistrationRequest request = new CustomerRegistrationRequest(
        "test",email,20
    );
    underTest.addCustomer(request);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

    Customer capturedCustomer = customerArgumentCaptor.getValue();
    assertThat(capturedCustomer.getId()).isNull();
    assertThat(capturedCustomer.getName()).isEqualTo(request.name());
    assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
    assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
  }

  @Test
  void willThrowWhenCustomerWithEmailAlreadyExists() {
    String email = "test@gamil.com";
    CustomerRegistrationRequest request = new CustomerRegistrationRequest(
        "test",email,20
    );
    when(customerDao.existsPersonWithEmail(email)).thenReturn(true);
    assertThatThrownBy(()->underTest.addCustomer(request))
        .isInstanceOf(DuplicateResourceException.class)
        .hasMessage("email already taken");
    verify(customerDao ,never()).insertCustomer(any());
  }

  @Test
  void deleteCustomer() {
    Long id = 1L;
    when(customerDao.existsPersonWithId(id)).thenReturn(true);
    underTest.deleteCustomer(id);
    verify(customerDao).deleteCustomer(id);
  }

  @Test
  void willThrowWhenCustomerDoesExistsToDelete() {
    Long id = 1L;
    when(customerDao.existsPersonWithId(id)).thenReturn(false);

    assertThatThrownBy(()->underTest.deleteCustomer(id))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("customer with id [%s] not found".formatted(id));
    verify(customerDao,never()).deleteCustomer(any());
  }

  @Test
  void canUpdateAllCustomerProperties() {
    Long customerId = 1L;
    String name = "alex";
    String email= "alex@gamil.com";
    Customer customer =  new Customer(
        customerId,name,email,20
    );

    when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(customer));
    when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
    "new name","newName@gamil.com",20
    );
    underTest.updateCustomer(customerId, customerUpdateRequest);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

    Customer capturedCustomer = customerArgumentCaptor.getValue();

    assertThat(capturedCustomer.getAge()).isEqualTo(customerUpdateRequest.age());
    assertThat(capturedCustomer.getName()).isEqualTo(customerUpdateRequest.name());
    assertThat(capturedCustomer.getEmail()).isEqualTo(customerUpdateRequest.email());
  }

  @Test
  void canUpdateCustomerEmail() {
    Long customerId = 1L;
    String name = "alex";
    String email= "alex@gamil.com";
    Customer customer =  new Customer(
        customerId,name,email,20
    );

    when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(customer));
    when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
        null , "newName@gamil.com",null
    );
    underTest.updateCustomer(customerId, customerUpdateRequest);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

    Customer capturedCustomer = customerArgumentCaptor.getValue();

    assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
    assertThat(capturedCustomer.getEmail()).isEqualTo(customerUpdateRequest.email());
  }

  @Test
  void canUpdateCustomerAge() {
    Long customerId = 1L;
    String name = "alex";
    String email= "alex@gamil.com";
    Customer customer =  new Customer(
        customerId,name,email,20
    );

    when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(customer));
    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
        null,null,20
    );
    underTest.updateCustomer(customerId, customerUpdateRequest);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

    Customer capturedCustomer = customerArgumentCaptor.getValue();

    assertThat(capturedCustomer.getAge()).isEqualTo(customerUpdateRequest.age());
    assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
    assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
  }

  @Test
  void canUpdateCustomerName() {
    Long customerId = 1L;
    String name = "alex";
    String email= "alex@gamil.com";
    Customer customer =  new Customer(
        customerId,name,email,20
    );

    when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(customer));
    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
        "new name",null,null
    );
    underTest.updateCustomer(customerId, customerUpdateRequest);

    ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
    verify(customerDao).updateCustomer(customerArgumentCaptor.capture());

    Customer capturedCustomer = customerArgumentCaptor.getValue();

    assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    assertThat(capturedCustomer.getName()).isEqualTo(customerUpdateRequest.name());
    assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
  }

  @Test
  void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
    Long customerId = 1L;
    String name = "alex";
    String email= "alex@gamil.com";
    Customer customer =  new Customer(
        customerId,name,email,20
    );

    String newEmail = "alex@gamil.com";
    when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(customer));
    when(customerDao.existsPersonWithEmail(email)).thenReturn(true);
    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
        null,email,null
    );
    assertThatThrownBy(()->underTest.updateCustomer(customerId,customerUpdateRequest))
        .isInstanceOf(DuplicateResourceException.class)
        .hasMessage("Email already taken");

    verify(customerDao,never()).updateCustomer(any());
  }

  @Test
  void willThrowWhenTryingToUpdateCustomerWithoutDataChanges() {
    Long customerId = 1L;
    String name = "alex";
    String email= "alex@gamil.com";
    Integer age = 20;
    Customer customer =  new Customer(
        customerId,name,email,age
    );
    when(customerDao.selectCustomerById(customerId)).thenReturn(Optional.of(customer));
    CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(
        name,email,age
    );
    assertThatThrownBy(()->underTest.updateCustomer(customerId,customerUpdateRequest))
        .isInstanceOf(DataNotChangedException.class)
        .hasMessage("Data changes not found");

    verify(customerDao,never()).updateCustomer(any());
  }
}