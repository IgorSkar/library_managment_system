package com.stav.library_managment_system.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSuggestion {

    @JsonProperty("book_suggestion_id")
    private int book_suggestion_id;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("title")
    private String title;
    @JsonProperty("authors")
    private String authors;
    @JsonProperty("language")
    private String language;
}
