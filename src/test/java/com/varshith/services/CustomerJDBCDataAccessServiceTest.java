package com.varshith.services;

import com.varshith.AbstractTestContainer;
import com.varshith.entities.Customer;
import com.varshith.rowmapper.CustomerRowMappper;
import org.hibernate.Interceptor;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainer {

  private  CustomerJDBCDataAccessService underTest;
  private  final CustomerRowMappper customerRowMappper =  new CustomerRowMappper();

  @BeforeEach
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

    List<Customer> actual = underTest.selectAllCustomers();

  assertThat(actual).isNotEmpty();
  }

  @Test
  void selectCustomerById() {

    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();

    Customer customer = new Customer(
        faker.name().fullName(),
        email,
        20
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();
   Optional<Customer> actual =  underTest.selectCustomerById(customerId);
   assertThat(actual).isPresent().hasValueSatisfying(c->{
     assertThat(c.getId()).isEqualTo(customerId);
     assertThat(c.getName()).isEqualTo(customer.getName());
     assertThat(c.getAge()).isEqualTo(customer.getAge());
     assertThat(c.getEmail()).isEqualTo(email);
       }
   );
  }
  @Test
  void selectCustomerByIdWillReturnEmptyWhenIdNotPresent() {
    Long customerId = -1L;
    Optional<Customer> actual =  underTest.selectCustomerById(customerId);
    assertThat(actual).isEmpty();
  }
  @Test
  void insertCustomer() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();

    Customer customer = new Customer(
        faker.name().fullName(),
        email,
        20
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    Optional<Customer> actual =  underTest.selectCustomerById(customerId);
    assertThat(actual).isNotEmpty();
  }

  @Test
  void updateCustomerName() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Integer age = 20;
    String name  =   faker.name().fullName();
    Customer customer = new Customer(
        name,
        email,
        age
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    String newName = "test name";
    Customer update  = new Customer();
    update.setId(customerId);
    update.setName(newName);
    underTest.updateCustomer(update);

    Optional<Customer> actual = underTest.selectCustomerById(customerId);
    assertThat(actual).isPresent().hasValueSatisfying(c->{
      assertThat(c.getName()).isEqualTo(newName);
      assertThat(c.getId()).isEqualTo(customerId);
      assertThat(c.getAge()).isEqualTo(customer.getAge());
      assertThat(c.getEmail()).isEqualTo(customer.getEmail());
    });
  }

  @Test
  void updateCustomerAge() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Integer age = 20;
    String name  =   faker.name().fullName();
    Customer customer = new Customer(
        name,
        email,
        age
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    Integer newAge = 40;
    Customer update  = new Customer();
    update.setId(customerId);
    update.setAge(newAge);
    underTest.updateCustomer(update);

    Optional<Customer> actual = underTest.selectCustomerById(customerId);
    assertThat(actual).isPresent().hasValueSatisfying(c->{
      assertThat(c.getName()).isEqualTo(customer.getName());
      assertThat(c.getId()).isEqualTo(customerId);
      assertThat(c.getAge()).isEqualTo(newAge);
      assertThat(c.getEmail()).isEqualTo(customer.getEmail());
    });
  }

  @Test
  void updateCustomerEmail() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Integer age = 20;
    String name  =   faker.name().fullName();
    Customer customer = new Customer(
        name,
        email,
        age
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    String newEmail = "test@gmail.com";
    Customer update  = new Customer();
    update.setId(customerId);
    update.setEmail(newEmail);
    underTest.updateCustomer(update);

    Optional<Customer> actual = underTest.selectCustomerById(customerId);
    assertThat(actual).isPresent().hasValueSatisfying(c->{
      assertThat(c.getName()).isEqualTo(customer.getName());
      assertThat(c.getId()).isEqualTo(customerId);
      assertThat(c.getAge()).isEqualTo(customer.getAge());
      assertThat(c.getEmail()).isEqualTo(newEmail);
    });
  }

  @Test
  void updateCustomerAllFields() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Integer age = 20;
    String name  =   faker.name().fullName();
    Customer customer = new Customer(
        name,
        email,
        age
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    String newEmail = "test@gmail.com";
    Integer newAge = 30;
    String newName = "test name";
    Customer update  = new Customer();
    update.setId(customerId);
    update.setEmail(newEmail);
    update.setName(newName);
    update.setAge(newAge);
    underTest.updateCustomer(update);

    Optional<Customer> actual = underTest.selectCustomerById(customerId);
    assertThat(actual).isPresent().hasValueSatisfying(c->{
      assertThat(c.getName()).isEqualTo(newName);
      assertThat(c.getId()).isEqualTo(customerId);
      assertThat(c.getAge()).isEqualTo(newAge);
      assertThat(c.getEmail()).isEqualTo(newEmail);
    });
  }

  @Test
  void doNotUpdateCustomer() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Integer age = 20;
    String name  =   faker.name().fullName();
    Customer customer = new Customer(
        name,
        email,
        age
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    Customer update  = new Customer();
    update.setId(customerId);
    underTest.updateCustomer(update);

    Optional<Customer> actual = underTest.selectCustomerById(customerId);
    assertThat(actual).isPresent().hasValueSatisfying(c->{
      assertThat(c.getName()).isEqualTo(customer.getName());
      assertThat(c.getId()).isEqualTo(customerId);
      assertThat(c.getAge()).isEqualTo(customer.getAge());
      assertThat(c.getEmail()).isEqualTo(customer.getEmail());
    });
  }
  @Test
  void deleteCustomer() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Integer age = 20;
    String name  =   faker.name().fullName();
    Customer customer = new Customer(
        name,
        email,
        age
    );
    underTest.insertCustomer(customer);
    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    underTest.deleteCustomer(customerId);
    Optional<Customer> actual =  underTest.selectCustomerById(customerId);
    assertThat(actual).isNotPresent();
  }

  @Test
  void existsPersonWithEmail() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Customer customer = new Customer(
        faker.name().fullName(),
        email,
        20
    );
    underTest.insertCustomer(customer);

    boolean actual = underTest.existsPersonWithEmail(email);
    assertThat(actual).isTrue();
  }

  @Test
  void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
    String email = faker.internet().safeEmailAddress()+"-"+UUID.randomUUID();
    boolean actual = underTest.existsPersonWithEmail(email);
    assertThat(actual).isFalse();
  }

  @Test
  void existsPersonWithId() {
    String email  =  faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Customer customer = new Customer(
        faker.name().fullName(),
        email,
        20
    );
    underTest.insertCustomer(customer);

    Long customerId  = underTest
        .selectAllCustomers()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();
    boolean actual = underTest.existsPersonWithId(customerId);
    assertThat(actual).isTrue();
  }

  @Test
  void existsPersonWithIdReturnsFalseWhenDoesNotExists() {
    Long customerId = -1L;
    boolean actual = underTest.existsPersonWithId(customerId);
    assertThat(actual).isFalse();
  }
}