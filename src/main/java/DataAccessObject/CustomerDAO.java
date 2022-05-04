package DataAccessObject;

import Models.Customer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerDAO  {


    int save(Customer customer);


    int  update(Customer customer, int id);

    int   delete(int id);

    List<Customer> getAll();

    Customer getById(int id);

}
