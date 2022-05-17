package com.stav.library_managment_system.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book_Queue {

    private int book_id;
    private int customer_id;
    private String queue_date;

}


