package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetails {

    private int book_id;
    private String title;
    private String Description;
    private String language;
    private String published;
    private String image_source;
    private  int pages;

    public BookDetails(int book_id, String title, String description, String language, String published, String image_source, String pages, String title1) {
    }
}

