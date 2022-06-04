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
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<Customer>(Customer.class));
    }

    @Override
    public Customer getById(int customerId) throws DataAccessException {
        Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customers WHERE customer_id=? ", new BeanPropertyRowMapper<Customer>(Customer.class), customerId);
        return customer;
    }


    public boolean createCustomer(String first_name, String last_name, String email, String password){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_customer");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("first_name", first_name);
        inParams.put("last_name", last_name);
        inParams.put("username", email);
        inParams.put("password", password);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (int) jdbcCall.execute(in).get("succeed") >= 1;
    }


    public boolean isValidCustomer( String email, String password){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("authenticator");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("username", email);
        inParams.put("userpassword", password);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (int) jdbcCall.execute(in).get("succeed") >= 1;
    }


    @Override
    public int save(Customer customer) {
        return jdbcTemplate.update("INSERT INTO customers (first_name, last_name, email, password) VALUES (?, ?, ?, ?)", new Object[]{customer.getFirst_name() ,customer.getLast_name(), customer.getEmail(), customer.getPassword()});
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
        return c;
    }

}