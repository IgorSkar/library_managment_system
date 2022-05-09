package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.CustomerDAO;
import com.stav.library_managment_system.Exception.apiRequestException;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/customer")
@RestController
public class CustomerController {

    @Autowired
    private CustomerDAO customerDAO;


    @GetMapping("/all")
    public List<Customer> findAll() {
        throw new apiRequestException("Oops can not get all customer with custom exception ");
      // return customerDAO.findAll();

    }

    @GetMapping("/{customerId}")
    public  ResponseEntity<?> getCustomerById(@PathVariable int customerId){
        Customer customer = null;
        try {
            customer = customerDAO.getById(customerId);
        } catch (DataAccessException e){

            return  new ResponseEntity<String>(" id not found", HttpStatus.BAD_REQUEST);
        }
         return  new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

      @GetMapping()
      public  ResponseEntity<?> getCustomerByFirstName(@RequestParam String firstName){
         Customer customer= null;
         try {
             customer = customerDAO.getByFirstName(firstName);
         } catch (DataAccessException e){
             return  new ResponseEntity<String>(" customer fist_name not found in database" ,HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<Customer>(customer,HttpStatus.OK);
      }

    @PostMapping()
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
       int result = customerDAO.save(customer);
       if (result == -1){
           return new ResponseEntity<String>(" Something was wrong!", HttpStatus.BAD_REQUEST);
       }
        return  new ResponseEntity<String>(" Customer saved successfully!" + customer.getFirst_name(),HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    public  ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable int customerId){
        int result = customerDAO.update(customer,customerId);
        return  new ResponseEntity<String>("customer updated successfully!", HttpStatus.OK);
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable int customerId) {
        customerDAO.deleteById(customerId);

        return  new ResponseEntity<String>("customer deleted successfully!",HttpStatus.OK);
    }


}

