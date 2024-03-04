package com.varshith.services;

import com.varshith.dao.CustomerDao;
import com.varshith.entities.Customer;
import com.varshith.rowmapper.CustomerRowMappper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{
    private  final JdbcTemplate jdbcTemplate;
    private  final CustomerRowMappper customerRowMappper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMappper customerRowMappper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMappper = customerRowMappper;
    }

        @Override
        public List<Customer> selectAllCustomers() {

            var sql = """
                    SELECT id,name,email,age
                    FROM CUSTOMER
                    """;

            return jdbcTemplate.query(
                    sql, customerRowMappper
            );
        }

        @Override
        public Optional<Customer> selectCustomerById(Long id) {
            var sql ="""
                    SELECT id,name,email,age
                    FROM customer
                    WHERE id=?
                    """;
            return jdbcTemplate.query(sql,customerRowMappper,id).stream().findFirst();
        }

        @Override
        public void insertCustomer(Customer customer) {
            var sql = """
                    INSERT INTO customer(name,email,age) VALUES (?,?,?);
                    """;
            jdbcTemplate.update(sql , customer.getName(),customer.getEmail(),customer.getAge());
        }

        @Override
        public void updateCustomer(Customer customer) {
            if(customer.getName() != null){
                var sql= """
                        UPDATE customer SET name = ? WHERE id = ?
                        """;
                jdbcTemplate.update(sql,customer.getName(),customer.getId());
            }
            if(customer.getEmail() != null){
                var sql= """
                        UPDATE customer SET email = ? WHERE id = ?
                        """;
                jdbcTemplate.update(sql,customer.getEmail(),customer.getId());
            }
            if(customer.getAge() != null){
                var sql= """
                        UPDATE customer SET age = ? WHERE id = ?
                        """;
                jdbcTemplate.update(sql,customer.getAge(),customer.getId());
            }

        }

        @Override
        public void deleteCustomer(Long id) {

            var sql = """
                    DELETE
                    FROM customer
                    WHERE id = ?
                    """;
            jdbcTemplate.update(sql ,id);
        }

        @Override
        public boolean existsPersonWithEmail(String email) {

            var sql = """
                    SELECT count(id)
                    FROM customer
                    where email = ?
                    """;

           Integer count =  jdbcTemplate.queryForObject(sql,Integer.class, email);

            return count!=null && count>0;
        }

        @Override
        public boolean existsPersonWithId(Long id) {
            var sql = """
                    SELECT count(id)
                    FROM customer
                    where id = ?
                    """;

            Integer count =  jdbcTemplate.queryForObject(sql,Integer.class, id);

            return count!=null && count>0;
        }


}