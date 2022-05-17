package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.BookDAO;
import com.stav.library_managment_system.Models.Book;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookDAO bookDAO;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookDAO.getBookList();
    }

    @GetMapping("/{bookId}")
    public String getBookById(@PathVariable int bookId) {
        return bookDAO.getBookById(bookId).toString();
    }

    @GetMapping("/add/{bookId}")
    public String getBookByBookIdAndBookDetails(@PathVariable int bookId) {

        return bookDAO.getBookWithBookId(bookId);
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
        return bookDAO.getAmountOfBooks(isbn);
    }

    @GetMapping("amount_in_stock/{isbn}")
    public int getAmountOfBooksInStock(@PathVariable("isbn") String isbn){
        return bookDAO.getAmountOfBooksInStock(isbn);
    }

    @GetMapping("amount_in_libraries/{isbn}")
    public String getAmountOfBookInLibraries(@PathVariable("isbn") String isbn){
        return bookDAO.getAmountOfBookInLibraries(isbn).toString();
    }

    @GetMapping("/title/ISBN")
     public ResponseEntity<?> getBookByTitleAndISBN(@RequestParam String title, @RequestParam String ISBN){
        Book book = null;
        try {
            bookDAO.getBookByTitleAndISBN(title, ISBN);
        } catch (DataAccessException e){
            e.printStackTrace();
            return  new ResponseEntity<String>(" ISBN  and  title not found in the database", HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<Book>( book,HttpStatus.OK);

    }
}


