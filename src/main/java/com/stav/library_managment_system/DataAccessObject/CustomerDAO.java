package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.Models.Customer;

import java.util.List;

public interface CustomerDAO {

    public List<Customer> findAll();

    public int deleteById(int id);

    public int insert(Customer customer);

    public int update(Customer customer, int id);
}