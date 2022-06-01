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
        @GetMapping("/{id}")
        public Library getLibraryById(@PathVariable("id") int libraryId) {
            return libraryDAO.getLibraryById(libraryId);
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



