package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.BookDAO;
import com.stav.library_managment_system.Models.Book;
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

    @GetMapping("/all")
    public List<Book> getAllBooks(){
        return bookDAO.findAll();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable int bookId){
        Book book = null;
        try {
            book  = bookDAO.findById(bookId);
        } catch (DataAccessException e){
            return  new ResponseEntity<String>("id not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Book>(book,HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> createBook(@RequestBody Book book){
        int result = bookDAO.save(book);
        if (result == -1){
            return  new ResponseEntity<String>("Something was wrong",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(" book added successfully!" + book.getTitle(),HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook( @RequestBody Book book, @PathVariable int bookId) {
        int result= bookDAO.update(book, bookId);
        return new ResponseEntity<String>("book updated successfully!",HttpStatus.OK);
    }



    @DeleteMapping("/{bookId}")

    public ResponseEntity<?> deleteBookById(@PathVariable int bookId){
        int result = bookDAO.deleteById(bookId);
        return new ResponseEntity<String>("book deleted successfully!",HttpStatus.OK);
    }




}
