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
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    private BookDAO bookDAO;


    private BookDetails bookDetails;


    @GetMapping("/all")
    public List<Book> geAllBooks() {
        return bookDAO.getBookList();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable int bookId) {
        Book book = null;
        try {
            book = bookDAO.getBookById(bookId);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Book id not found in the database " + book.getBook_id(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping("/add/{bookId}")
    public String getBookByBookIdAndBookDetails(@PathVariable int bookId) {

        return bookDAO.getBookWithBookId(bookId);
    }

    @GetMapping()
    public ResponseEntity<?> getBookByISBN(@RequestParam String ISBN) throws ResourceNotFoundException {
        Book book = null;
        try {
            book = bookDAO.getBookByISBN(ISBN);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("book ISBN not found in the database" + book.getIsbn(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Book>(book, HttpStatus.OK);

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
        int result = bookDAO.save(object.getString("isbn"));
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
}


