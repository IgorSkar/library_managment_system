package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.Models.Customer;
import java.util.List;
import java.util.Map;

public interface CustomerDAO  {

    public List<Customer> findAll();
    //public Customer Customer(int id);
    public boolean isValidCustomer(String email, String password);
    public int deleteById(int id);
    public int insert(Customer customer);
    public int update(Customer customer, int id);
}

