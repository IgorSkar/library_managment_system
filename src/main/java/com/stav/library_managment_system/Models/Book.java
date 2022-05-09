package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private int book_id;
    private String title;
    private String Description;
    private String author_id;
    private String genre;
    private String language;
    private int isbn;
    private String publication_date;

}

