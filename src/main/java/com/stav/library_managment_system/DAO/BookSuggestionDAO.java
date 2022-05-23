package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.BookSuggestion;

import java.util.List;

public interface BookSuggestionDAO {

    List<BookSuggestion> getBookSuggestion();

    //BookSuggestion getBookSuggestionByCustomerId(int customerId);

    int add(BookSuggestion bookSuggestion);

    void deleteById(int book_suggestion_id);

}
