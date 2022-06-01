package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Data
@Component
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

public class Customer {


    private int customer_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;



    }



