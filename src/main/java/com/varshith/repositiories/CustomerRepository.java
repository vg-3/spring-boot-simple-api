package com.varshith.repositiories;

import com.varshith.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer , Long> {
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long id);
}
