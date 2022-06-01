package com.stav.library_managment_system.Controller;


import com.stav.library_managment_system.DAO.CustomerDAO;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerDAO customerDAO;


    @GetMapping
    public List<Customer> findAll() {

        return customerDAO.findAll();
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Integer customerId){
        Customer customer = null;
        try {
            customer = customerDAO.getById(customerId);
        } catch (DataAccessException e){
            return  new ResponseEntity<String>(" id not found", HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
     @GetMapping("/firstName")
    public  ResponseEntity<?> getCustomerByFirstName(@RequestParam String firstName){
        Customer customer= null;
        try {
            customer = customerDAO.getByFirstName(firstName);
        } catch (DataAccessException e){
            return  new ResponseEntity<String>(" customer fist_name not found in the  database" ,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Customer>(customer,HttpStatus.OK);
    }



    @GetMapping("/create")
    public boolean createCustomer(@RequestParam String firstName, @RequestParam String lastName,
                                  @RequestParam String mail,
                                  @RequestParam String password){
        return customerDAO.createCustomer(firstName, lastName, mail,password);
    }



    @PutMapping("/{customerId}")
    public  ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable Integer customerId){
        int result = customerDAO.update(customer,customerId);
        return  new ResponseEntity<String>(" customer  updated successfully!" + result, HttpStatus.OK);
    }



   @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Integer customerId) {
        customerDAO.deleteById(customerId);

        return  new ResponseEntity<String>("customer deleted successfully!",HttpStatus.OK);
    }



    @GetMapping("/login")
    public Customer isValidCustomer(@RequestParam("email") String email,@RequestParam("password") String password){
        return customerDAO.isValidCustomer(email, password);
    }



   /* @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);

    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }


    */

}






