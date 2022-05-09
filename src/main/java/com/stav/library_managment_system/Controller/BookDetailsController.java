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
@RequestMapping("/api/bookDetails")
public class BookDetailsController {
    @Autowired
    private BookDetailsDAO bookDetailsDAO;

    @GetMapping("/all")
    public List<BookDetails> getAllBooks(){
        return bookDetailsDAO.findAll();
    }



     @GetMapping()
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


       @GetMapping("/{bookDetailsId}")
    public ResponseEntity<?> getBookById(@PathVariable int bookDetailsId){
        BookDetails bookDetails = null;
        try {
            bookDetails  = bookDetailsDAO.findById(bookDetailsId);
        } catch (DataAccessException e){
            e.printStackTrace();
            return  new ResponseEntity<String>("id not found", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<BookDetails>(bookDetails,HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> createBook(@RequestBody BookDetails bookDetails){
        int result = bookDetailsDAO.save(bookDetails);
        if (result == -1){
            return  new ResponseEntity<String>("Something was wrong",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>(" book added successfully!",HttpStatus.CREATED);
    }
      // update funkar inte
    @PutMapping("/{bookDetailsId}")
    public ResponseEntity<?> updateBook( @RequestBody BookDetails bookDetails, @PathVariable int bookDetailsId) {
        int result= bookDetailsDAO.update(bookDetails, bookDetailsId);
        return new ResponseEntity<String>("book updated successfully!",HttpStatus.OK);
    }



   @DeleteMapping("/{bookDetailsId}")

    public ResponseEntity<?> deleteBookById(@PathVariable int bookDetailsId){
        int result = bookDetailsDAO.deleteById(bookDetailsId);
        return new ResponseEntity<String>("book deleted successfully!",HttpStatus.OK);
    }




}



