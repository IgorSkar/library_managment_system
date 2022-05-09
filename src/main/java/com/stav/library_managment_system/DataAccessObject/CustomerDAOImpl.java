package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.CustomerDAO;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean isValidCustomer(String email, String password) {
        String query = "call checkLogin(?,?))";

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("checkLogin")
                .declareParameters(new SqlOutParameter("userExists", Types.INTEGER));

        Map<String, String > inParams = new HashMap<>();
        inParams.put("email_p", email+"");
        inParams.put("password_p", password+"");

        SqlParameterSource in = new MapSqlParameterSource(inParams);

        return (int) jdbcCall.execute(in).get("userExists")>=1;
    }

    @Override
    public Customer getById(int customerId) {
        return null;
    }
}
