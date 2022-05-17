package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.BookDetailsDAO;
import com.stav.library_managment_system.Models.BookDetails;
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
    public List<BookDetails> getAllBookDetails(){
        return bookDetailsDAO.findAll();
    }


     @GetMapping("123")
     public  ResponseEntity<?> getBookDetailsByTittle(@RequestParam String title){
         BookDetails bookDetails = null;
         try {
             bookDetails = bookDetailsDAO.getBookByTitle(title);
         }  catch (DataAccessException e){
             e.printStackTrace();
             return  new ResponseEntity<String>(" Book Title not found in database",HttpStatus.BAD_REQUEST);
         }
         return  new ResponseEntity<BookDetails>(bookDetails,HttpStatus.OK);
     }


       @GetMapping("/{ISBN}")
    public ResponseEntity<?> getBookDetailsByISBN(@PathVariable String ISBN){
        BookDetails bookDetails = null;
        try {
            bookDetails  = bookDetailsDAO.findByISBN(ISBN);
        } catch (DataAccessException e){
            e.printStackTrace();
            return  new ResponseEntity<String>("ISBN not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<BookDetails>(bookDetails,HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> createBookDetails(@RequestBody BookDetails bookDetails){
        int result = bookDetailsDAO.save(bookDetails);
        if (result == -1){
            return  new ResponseEntity<String>("Something was wrong",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(" book added successfully!",HttpStatus.CREATED);
    }
      // update funkar inte
    @PutMapping("/{ISBN}")
    public ResponseEntity<?> updateBookDetails( @RequestBody BookDetails bookDetails, @PathVariable String ISBN) {
        int result= bookDetailsDAO.update(bookDetails, ISBN);
        return new ResponseEntity<String>("book updated successfully!",HttpStatus.OK);
    }



   @DeleteMapping("/{ISBN}")

    public ResponseEntity<?> deleteBookDetailsByISBN(@PathVariable String ISBN){
        int result = bookDetailsDAO.deleteById(ISBN);
        return new ResponseEntity<String>("book deleted successfully!",HttpStatus.OK);
    }




}




