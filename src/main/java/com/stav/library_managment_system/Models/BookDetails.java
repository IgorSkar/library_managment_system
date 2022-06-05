package com.stav.library_managment_system.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class BookDetails {

    private String isbn;
    private String title;
    private String Description;
    private String language;
    private String published;
    private String image_source;
    private  int pages;


    public BookDetails(String isbn, String title, String description, String language, String published, String image_source, int pages) {
        this.isbn = isbn;
        this.title = title;
        Description = description;
        this.language = language;
        this.published = published;
        this.image_source = image_source;
        this.pages = pages;
    }
}

