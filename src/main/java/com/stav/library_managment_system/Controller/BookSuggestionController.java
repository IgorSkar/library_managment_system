package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.BookSuggestionDAO;
import com.stav.library_managment_system.Models.BookSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book_suggestions")
public class BookSuggestionController {

    @Autowired
    private BookSuggestionDAO bookSuggestionDAO;

    @GetMapping
    public List<BookSuggestion> getBookSuggestion() {
        return bookSuggestionDAO.getBookSuggestion();
    }

    @GetMapping("suggest")
    public boolean addBookSuggestion(@RequestParam String title, @RequestParam String authors, @RequestParam String isbn, @RequestParam String language) {
        return bookSuggestionDAO.add(title, authors, isbn, language);
    }


    @GetMapping("remove/{id}")
    public boolean deleteBookSuggestionById(@PathVariable("id") int bookSuggestionId) {
        return bookSuggestionDAO.deleteById(bookSuggestionId);
    }


}
