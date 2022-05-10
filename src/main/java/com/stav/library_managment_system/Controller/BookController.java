package com.stav.library_managment_system.Controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.stav.library_managment_system.DAO.BookDAO;
import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookDetails;
import com.stav.library_managment_system.Exception.ResourceNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/books")
public class BookController {
    @Autowired
    private BookDAO bookDAO;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookDAO.getBookList();
    }

    @GetMapping("/id/{bookId}")
    public String getBookById(@PathVariable int bookId) {
        return bookDAO.getBookById(bookId).toString();
    }

    @GetMapping("/add/{bookId}")
    public String getBookByBookIdAndBookDetails(@PathVariable int bookId) {

        return bookDAO.getBookWithBookId(bookId);
    }

      @GetMapping("/ISBN")
      public ResponseEntity<?> booksAmountWithISBN(@RequestParam String ISBN){
      int book = 0;
      try {
          bookDAO.ISBNCount(ISBN);
      } catch (DataAccessException e){
          e.printStackTrace();
          return  new ResponseEntity<String>(" ISBN not found in the database", HttpStatus.BAD_REQUEST);
      }
      return  new ResponseEntity<String>("it was" ,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createBook(@RequestBody String data) {
        JSONObject object = new JSONObject(data);
        int result = bookDAO.save(object.getString("isbn"), object.getInt("library_id"));
        if (result == -1) {
            return new ResponseEntity<String>("OBS:Something was wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("book added successfully!", HttpStatus.OK);
    }
    @DeleteMapping("/{bookId}")

    public ResponseEntity<?> deleteBookById(@PathVariable int bookId){
        int result = bookDAO.deleteBook(bookId);
        return new ResponseEntity<String>("book deleted successfully!",HttpStatus.OK);
    }

    @GetMapping("amount_with_isbn/{isbn}")
    public int getAmountOfBooks(@PathVariable("isbn") String isbn){
        return 0;
    }

    @GetMapping("amount_in_stock/{isbn}")
    public int getAmountOfBooksInStock(@PathVariable("isbn") String isbn){
        return 0;
    }

    @GetMapping("amount_in_libraries/{isbn}")
    public String getAmountOfBookInLibraries(@PathVariable("isbn") String isbn){
        return bookDAO.getAmountOfBookInLibraries(isbn).toString();
    }
}


