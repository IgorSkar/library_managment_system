package com.stav.library_managment_system.Controller;


import com.stav.library_managment_system.DataAccessObject.CustomerDAO;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/customer")
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

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable int id) {
        return eDAO.deleteById(id)+" Customer(s) delete from the database";
    }

}