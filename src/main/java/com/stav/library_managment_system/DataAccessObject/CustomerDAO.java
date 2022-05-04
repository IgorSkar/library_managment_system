package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.Models.Customer;

import java.util.List;

public interface CustomerDAO  {


    int save(Customer customer);


    int  update(Customer customer, int id);

    int   delete(int id);

    List<Customer> getAll();

    Customer getById(int id);

}