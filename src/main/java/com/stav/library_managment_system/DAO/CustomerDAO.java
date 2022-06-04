package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Customer;

import java.util.List;

public interface CustomerDAO  {

    List<Customer> findAll();

    Customer getByFirstName(String firstName);

    Customer getById (int customerId);

    Customer getByEmail (String email);

   // Customer isValidCustomer(String email, String password);

    boolean isValidCustomer(String email, String password);

    boolean createCustomer(String firstName, String lastName, String mail, String password);

   int save (Customer customer);

    int update(Customer customer, int customerId);

    void  deleteById(int customerId);




}

