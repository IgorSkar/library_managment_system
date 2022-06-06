package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.BookSuggestion;

import java.util.List;

public interface BookSuggestionDAO {

    List<BookSuggestion> getBookSuggestion();

    //BookSuggestion getBookSuggestionByCustomerId(int customerId);

    boolean add(String title, String authors, String isbn, String language);

    boolean deleteById(int book_suggestion_id);

}
