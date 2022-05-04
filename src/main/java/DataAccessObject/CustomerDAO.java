package DataAccessObject;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Data
@Repository
public class CustomerDAO  {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private String error;

    /**
     *Lägg till en ny kund i databasen
     * @param customerID
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */


    public  void insertNewCustomer(int customerID, String firstName, String lastName, String email, String password) {

        String query = "INSERT INTO customers VALUES (null, ?, ?,?,?);";

        int result = jdbcTemplate.update(query, firstName, lastName,email,password);

        if (result > 0) {
            System.out.println(result + "customer added  successful to database");
            this.error = "customer added successful to database";
        }
    }

    public void deleteCustomerByID(int customerID){
        String query = "DELETE FROM customers WHERE customer_ID = ?;";
        int result = jdbcTemplate.update(query, customerID);

        if (result > 0) {
            System.out.println(result + "customer deleted from database");
            this.error = "customer deleted successful from database";
        }
    }

}
