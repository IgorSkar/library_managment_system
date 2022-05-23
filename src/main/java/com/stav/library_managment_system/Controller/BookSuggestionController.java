package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.BookSuggestionDAO;
import com.stav.library_managment_system.Models.BookSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booksuggestion")
public class BookSuggestionController {

    @Autowired
    private BookSuggestionDAO bookSuggestionDAO;

    @GetMapping("/all")
    public List<BookSuggestion> getBookSuggestion() {
        return bookSuggestionDAO.getBookSuggestion();
    }

    @PostMapping()
    public ResponseEntity<?> addBookSuggestion(@RequestBody BookSuggestion bookSuggestion) {
        int result = bookSuggestionDAO.add(bookSuggestion);
        if (result == -1) {
            return new ResponseEntity<String>("Something was wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(" BookSuggestion added successfully!", HttpStatus.CREATED);
    }


    @DeleteMapping("/{book_suggestion_id}")
    public ResponseEntity<?> deleteBookSuggestionById(@PathVariable int book_suggestion_id) {
        bookSuggestionDAO.deleteById(book_suggestion_id);
        return new ResponseEntity<String>("Book suggestion deleted successfully!", HttpStatus.OK);
    }


}
