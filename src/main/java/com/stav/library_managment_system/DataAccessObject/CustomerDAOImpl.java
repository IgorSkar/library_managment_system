package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.CustomerDAO;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerDAOImpl implements CustomerDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCall;

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<Customer>(Customer.class));
    }
/*
    @Override
    public Customer Customer(int id) {
        return null;
    }
 */
    @Override
    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM customers WHERE customer_id=?", id);
    }

    @Override
    public Customer getByFirstName(String firstName) {
        return null;
    }

    @Override
    public int save(Customer customer) {
        return jdbcTemplate.update("INSERT INTO customers (first_name, last_name, email, password) VALUES (?, ?, ?, ?)", new Object[] {customer.getFirst_name(), customer.getLast_name(), customer.getEmail(), customer.getPassword()});
    }

    @Override
    public int update(Customer customer, int id) {
        return 0;
    }

    @Override
    public Customer isValidCustomer(String email, String password) {
        String query = "SELECT * FROM customers WHERE email =? AND password =?";
        try {
            Customer customer = jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            });
            return customer;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public Customer getById(int customerId) {
        return null;
    }
}
