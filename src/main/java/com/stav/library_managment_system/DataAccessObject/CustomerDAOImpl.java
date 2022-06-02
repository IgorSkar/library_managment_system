package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.CustomerDAO;;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public Customer isValidCustomer(String email, String password) {
        String query = "SELECT * FROM customers WHERE email =? AND password =?";
        try {
            Customer customer = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Customer.class), email, password);
            return customer;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<Customer>(Customer.class));
    }

    @Override
    public Customer getById(int customerId) throws DataAccessException {
        Customer c = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id=? ", new BeanPropertyRowMapper<Customer>(Customer.class), customerId);
        return c;
    }

    public boolean createCustomer(String firstName, String lastName, String mail, String password){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_customer");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("first_name", firstName);
        inParams.put("last_name", lastName);
        inParams.put("email", mail);
        inParams.put("password", password);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (int) jdbcCall.execute(in).get("succeed") >= 1;
    }

    @Override
    public int save(Customer customer) {
        return jdbcTemplate.update("INSERT INTO customers (first_name, last_name, email, password) VALUES (?, ?, ?, ?)", new Object[]{customer.getFirst_name(), customer.getLast_name(), customer.getEmail(), customer.getPassword()});
    }

    @Override
    public int update(Customer customer, int customerId) {

        return jdbcTemplate.update("UPDATE customers SET email=?, password=?  WHERE  customer_id=?", new Object[]{customer.getEmail(), customer.getPassword(), customerId});
    }

    @Override
    public void deleteById(int customerId) {
        jdbcTemplate.update("DELETE FROM customer WHERE customer_id=?", customerId);
    }

    @Override
    public Customer getByFirstName(String firstName) throws DataAccessException {
        Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE first_name=?", new BeanPropertyRowMapper<Customer>(Customer.class), firstName);
        return customer;
    }

    @Override
    public Customer getByEmail(String email){
        Customer c = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email=?", new BeanPropertyRowMapper<Customer>(Customer.class), email);
        System.out.println("Customer in backend = " + c);
        return c;
    }

}