package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Customer;
import java.util.List;

public interface CustomerDAO  {

     List<Customer> findAll();

     boolean isValidCustomer(String email, String password);

     Customer getById (int customerId);

    int save (Customer customer);

    int update(Customer customer, int customerId);


    int deleteById(int customerId);

      Customer getByFirstName(String firstName);
}

