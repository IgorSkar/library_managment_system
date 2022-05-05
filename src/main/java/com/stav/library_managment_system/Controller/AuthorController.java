package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.AuthorDAO;
import com.stav.library_managment_system.Models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorDAO authorDAO;


    @GetMapping("/all")
    public List<Author> geAllAuthors(){
        return authorDAO.getAllAuthors();
    }


    @GetMapping("/{authorId}")
    public ResponseEntity<?>  getAuthorById(@PathVariable int authorId){
        Author author = null;
        try {
            author = authorDAO.getAuthorById(authorId);
        } catch (DataAccessException e) {
            return new ResponseEntity<String>("id not found", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Author>(author, HttpStatus.OK);
    }

       @GetMapping()
      public  ResponseEntity<?> getAuthorByName(@RequestParam String name){
         Author  author = null;
         try {
             author  = authorDAO.getAuthorByName(name);
         } catch (DataAccessException e){
             return  new ResponseEntity<String>(" author name not found in database" ,HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<Author>(author,HttpStatus.OK);
      }

    @PostMapping()
    public ResponseEntity<?> createAuthor(@RequestBody Author author){
        int result = authorDAO.save(author);
        if(result == -1){
            return new ResponseEntity<String>(" something want wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String> ( "added Author " + author.getName(),HttpStatus.OK);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author, @PathVariable int authorId){
        int result = authorDAO.update(author,authorId);
        return new ResponseEntity<String>("Author updated successfully!", HttpStatus.OK);

    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<?> deleteAuthor(@PathVariable int authorId){
        authorDAO.delete(authorId);
        return  new ResponseEntity<String>("author deleted successfully!",HttpStatus.OK);
    }


}