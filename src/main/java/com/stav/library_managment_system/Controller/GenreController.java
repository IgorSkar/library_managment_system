package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.GenreDAO;
import com.stav.library_managment_system.Models.Genre;
import com.stav.library_managment_system.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

    @Autowired
    private GenreDAO genreDAO;

    @GetMapping("/all")
    public List<Genre> getAllGenres(){
        return genreDAO.getAllGenres();
    }

    @GetMapping ("/{genreId}")
    public  ResponseEntity<Genre> getGenreById(@PathVariable int genreId) throws ResourceNotFoundException {
      Genre genre = genreDAO.getById(genreId);
        return null;
    }
}


