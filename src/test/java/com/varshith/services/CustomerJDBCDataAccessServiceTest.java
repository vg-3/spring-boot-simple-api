package com.varshith.services;

import com.varshith.AbstractTestContainer;
import com.varshith.entities.Customer;
import com.varshith.rowmapper.CustomerRowMappper;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainer {

  private  CustomerJDBCDataAccessService underTest;
  private final CustomerRowMappper customerRowMappper =  new CustomerRowMappper();

  @BeforeAll
  void setUp() {
    underTest = new CustomerJDBCDataAccessService(
      getJdbcTemplate(),
      customerRowMappper
    );
  }

  @Test
  void selectAllCustomers() {
    Customer customer = new Customer(
        faker.name().fullName(),
        faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID(),
        20
    );
    underTest.insertCustomer(customer);

    List<Customer> expected = underTest.selectAllCustomers();

  assertThat(expected).isNotEmpty();
  }

  @Test
  void selectCustomerById() {
    //Given

    //When

    //Then
  }

  @Test
  void insertCustomer() {
    //Given

    //When

    //Then
  }

  @Test
  void updateCustomer() {
    //Given

    //When

    //Then
  }

  @Test
  void deleteCustomer() {
    //Given

    //When

    //Then
  }

  @Test
  void existsPersonWithEmail() {
    //Given

    //When

    //Then
  }

  @Test
  void existsPersonWithId() {
    //Given

    //When

    //Then
  }
}