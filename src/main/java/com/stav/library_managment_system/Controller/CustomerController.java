package com.stav.library_managment_system.Controller;


import com.stav.library_managment_system.DataAccessObject.CustomerDAO;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerDAO customerDAO;

    @GetMapping("/all")
    public List<Customer> getCustomers() {
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
