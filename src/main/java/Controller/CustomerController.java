package Controller;

import DataAccessObject.CustomerDAO;
import Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/customer")
@RestController
public class CustomerController {

    @Autowired
    private CustomerDAO customerDAO;

    @GetMapping("/all")
    public List<Customer> getEmployees() {
        return customerDAO.getAll();
    }

    @GetMapping("/{customerId}")
    public void getCustomerById(@PathVariable int customerId){
        customerDAO.getById(customerId);

    }

    @PostMapping()
    public int createCustomer(@RequestBody Customer customer){
        return customerDAO.save(customer);
    }

    @PutMapping("/{customerId}")
    public int updateCustomer(@PathVariable int customerId,@RequestBody Customer customer){
        return  customerDAO.update(customer,customerId);

    }

    @DeleteMapping("/{customerId}")
    public void deleteCustomerById(@PathVariable int customerId){
        customerDAO.delete(customerId);
    }
}
