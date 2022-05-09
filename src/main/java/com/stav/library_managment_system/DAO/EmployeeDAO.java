package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Employee;

import java.util.List;

public interface EmployeeDAO {


    List<Employee> getAllEmployees();


     Employee getEmployeeId (int employeeId);

     Employee isValidEmployee(String user_name, String password);


    int save (Employee employee);


    int  update(Employee employee, int employeeId);

    void delete(int employeeId);

    Employee getByFirstName(String firstName) ;


   // Employee isValidUserAndPassword(String userName, String password);
}
