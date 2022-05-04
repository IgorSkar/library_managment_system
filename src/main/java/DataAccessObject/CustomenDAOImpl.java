package DataAccessObject;

import Models.Customer;
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
    public int save(Customer customer) {
        return jdbcTemplate.update("INSERT INTO customers (first_name, last_name, email, password) VALUES (null, ?, ?, ?, ?)", new Object[] {customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword()});

    }

    @Override
    public int update(Customer customer, int id) {

        return jdbcTemplate.update("UPDATE customers SET first_name = ?, last_name = ?, email = ?, password = ?  WHERE customer_id = ?", new Object[] {customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword(), id});
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM customers WHERE id=?", id);
    }

    @Override
    public List<Customer> getAll() {
        return jdbcTemplate.query("SELECT * FROM customers", new BeanPropertyRowMapper<Customer>(Customer.class));
    }

    @Override
    public Customer getById(int id) {

        return jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id=?", new BeanPropertyRowMapper<Customer>(Customer.class), id);
    }
}
