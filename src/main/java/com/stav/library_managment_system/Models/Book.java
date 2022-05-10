package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONPropertyIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int book_id;
    private String isbn;
    private int library_id;

}

