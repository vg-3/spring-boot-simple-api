package com.varshith.repositiories;

import com.varshith.AbstractTestContainer;
import com.varshith.TestContainerTest;
import com.varshith.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainer {

  private CustomerRepository underTest;

  @BeforeEach
  void setUp() {
  }

  @Test
  void existsCustomerByEmail() {
    String name = faker.name().fullName();
    String email = faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();
    Customer customer =  new Customer(
        name,
        email,
        10
    );
    underTest.save(customer);

    var actual  = underTest.existsCustomerByEmail(email);
    assertThat(actual).isTrue();
  }

  void existsPersonWithEmailReturnsFalseWhenDoesNotExists() {
    String email = faker.internet().safeEmailAddress()+"-"+ UUID.randomUUID();

    var actual  = underTest.existsCustomerByEmail(email);
    assertThat(actual).isFalse();
  }

  @Test
  void existsCustomerById() {
    String name = faker.name().fullName();
    String email = faker.internet().safeEmailAddress();
    Customer customer =  new Customer(
        name,
        email,
        10
    );
    underTest.save(customer);
    Long customerId  = underTest
        .findAll()
        .stream()
        .filter(c->c.getEmail().equals(email))
        .map(Customer::getId)
        .findFirst()
        .orElseThrow();

    var actual = underTest.existsCustomerById(customerId);
    assertThat(actual).isTrue();
  }

  void existsPersonWithIdReturnsFalseWhenDoesNotExists() {
    Long customerId = -1L;
    var actual  = underTest.existsCustomerById(customerId);
    assertThat(actual).isFalse();
  }
}