package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomenDAOImpl implements CustomerDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<Customer>(Customer.class));
    }

    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM customers WHERE customer_id=?", id);
    }

    @Override
    public int insert(Customer customer) {
        return jdbcTemplate.update("INSERT INTO customers (first_name, last_name, email, password) VALUES (?, ?, ?, ?)", new Object[] {customer.getFirst_name(), customer.getLast_name(), customer.getEmail(), customer.getPassword()});
    }

    @Override
    public int update(Customer customer, int id) {
        return 0;
    }
}