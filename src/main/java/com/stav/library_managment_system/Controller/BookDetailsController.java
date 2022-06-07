package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.BookDetailsDAO;
import com.stav.library_managment_system.Models.BookDetails;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book_details")
public class BookDetailsController {
    @Autowired
    private BookDetailsDAO bookDetailsDAO;

    @GetMapping
    public String getAllBookDetails(@RequestParam String language, @RequestParam String releaseDate, @RequestParam String library, @RequestParam String searchType, @RequestParam String search, @RequestParam String popularSort) {
        return bookDetailsDAO.findAll(language, releaseDate, library, searchType, search, popularSort).toString();
    }

    @GetMapping("/genre/{genre}")
    public String getBooksByGenre(@PathVariable("genre") String genre){
        return bookDetailsDAO.getBooksByGenre(genre.split(",")).toString();
    }

    @GetMapping("/{isbn}")
    public String getBookDetailsByISBN(@PathVariable("isbn") String isbn) {
        JSONObject object = bookDetailsDAO.findByISBN(isbn);
        return object == null ? "" : object.toString();
    }

    @PostMapping()
    public ResponseEntity<?> createBookDetails(@RequestBody BookDetails bookDetails) {
        int result = bookDetailsDAO.save(bookDetails);
        if (result == -1) {
            return new ResponseEntity<String>("Something was wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(" book added successfully!", HttpStatus.CREATED);
    }

    // update funkar inte
    @PutMapping("/{ISBN}")
    public ResponseEntity<?> updateBookDetails(@RequestBody BookDetails bookDetails, @PathVariable String ISBN) {
        int result = bookDetailsDAO.update(bookDetails, ISBN);
        return new ResponseEntity<String>("book updated successfully!", HttpStatus.OK);
    }


    @DeleteMapping("/{ISBN}")
    public ResponseEntity<?> deleteBookDetailsByISBN(@PathVariable String ISBN) {
        int result = bookDetailsDAO.deleteById(ISBN);
        return new ResponseEntity<String>("book deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/add")
    public boolean addBook(@RequestParam String title, @RequestParam String description, @RequestParam String authors, @RequestParam String genres, @RequestParam String isbn, @RequestParam String published, @RequestParam int page_count, @RequestParam String language, @RequestParam String image){
        return bookDetailsDAO.addBook (title, description, authors, genres, isbn, published, page_count, language, image);
    }

    @PostMapping("/add")
    public boolean addBook(@RequestBody String data){
        return bookDetailsDAO.addBook(new JSONObject(data));
    }

}




