package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Customer;
import java.util.List;

public interface CustomerDAO  {

     List<Customer> findAll();

     Customer getById (int customerId);

    int save (Customer customer);

    int update(Customer customer, int customerId);


    void  deleteById(int customerId);

      Customer getByFirstName(String firstName);
}

