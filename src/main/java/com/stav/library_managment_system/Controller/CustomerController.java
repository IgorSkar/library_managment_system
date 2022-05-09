package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DataAccessObject.CustomerDAO;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/customer")
@RestController
public class CustomerController {

    @Autowired
    private CustomerDAO eDAO;

    @GetMapping("/all")
    public List<Customer> findAll() {
        return eDAO.findAll();
    }

    @PostMapping("/insert")
    public String insert(@RequestBody Customer customer) {
        return eDAO.insert(customer)+" Customer saved successfully";
    }

    @DeleteMapping("/employees/{id}")
    public String deleteById(@PathVariable int id) {
        return eDAO.deleteById(id)+" Customer(s) delete from the database";
    }

    @GetMapping("/login")
    public boolean isValidCustomer(@RequestParam() String email,@RequestParam() String password){
        System.out.println(email);
        System.out.println(password);
        return eDAO.isValidCustomer(email, password);
    }

}
