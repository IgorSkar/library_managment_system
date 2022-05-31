package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

  public class Employee {
    private int employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String role;
}
