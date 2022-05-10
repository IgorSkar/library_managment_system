package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.GenreDAO;
import com.stav.library_managment_system.Models.Genre;
import com.stav.library_managment_system.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genres")
public class GenreController {

    @Autowired
    private GenreDAO genreDAO;

    @GetMapping
    public List<Genre> getAllGenres(){
        return genreDAO.getAllGenres();
    }

    @GetMapping ("/{genreId}")
    public  ResponseEntity<?> getGenreById(@PathVariable int genreId) throws ResourceNotFoundException {
      Genre genre= null;
      try {
          genre = genreDAO.getById(genreId);
      } catch (DataAccessException e){
          e.printStackTrace();
          return  new ResponseEntity<String>("Id not found in the database", HttpStatus.BAD_REQUEST);
      }
        return new ResponseEntity<Genre>(genre,HttpStatus.OK);
    }

    @GetMapping("123")
      public ResponseEntity<?> getGenreByName(String ISBN){
        return null;
    }




      @PostMapping()
       public  ResponseEntity<?> createGenre(@RequestBody Genre genre){
        int result = genreDAO.createGenre(genre);
        if (result == -1){
            return  new ResponseEntity<String>(" Something was wrong ", HttpStatus.BAD_REQUEST);
        }
         return  new ResponseEntity<String>(" genre added successfully!",HttpStatus.CREATED);
      }


      @DeleteMapping ("/{genreId}")
      public  ResponseEntity<?> deleteGenreById(@PathVariable int genreId){
        int result = genreDAO.deleteGenre(genreId);
        return  new ResponseEntity<String>("genre deleted successfully!",HttpStatus.OK);
      }

 }




