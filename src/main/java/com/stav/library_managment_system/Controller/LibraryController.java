package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.LibraryDAO;
import com.stav.library_managment_system.Models.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libraries")
public class LibraryController {

    @Autowired
    private LibraryDAO libraryDAO;

    @GetMapping
    public List<Library> getLibraries() {
        return libraryDAO.getLibraries();

    }
        @GetMapping("/{LibraryId}")
        public ResponseEntity<?> getLibraryById(@PathVariable int LibraryId) {
            Library library = null;
            try {
                library = libraryDAO.getLibraryById(LibraryId);
            } catch (DataAccessException e) {
                e.printStackTrace();
                return new ResponseEntity<String>("Something was wrong", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(library, HttpStatus.BAD_REQUEST);
        }


        @PostMapping()
    public ResponseEntity<?> createLibrary(@RequestBody Library library){
        libraryDAO.save(library);
        return  new ResponseEntity<String>("Added successfully!",HttpStatus.CREATED);
        }


        @DeleteMapping("/{libraryId}")
         public ResponseEntity<?> deleteLibraryById(@PathVariable int libraryId){
           libraryDAO.delete(libraryId);
           return new ResponseEntity<String>("Deleted successfully!",HttpStatus.OK);
        }
    }



