package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSuggestion {

    private int book_suggestion_id;
    private String isbn;
    private String title;
    private String author;
    private String language;
}
