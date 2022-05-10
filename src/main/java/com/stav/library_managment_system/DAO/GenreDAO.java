package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Genre;

import java.util.List;

public interface GenreDAO {


    List<Genre> getAllGenres();

     Genre getById(int genreId);

     int createGenre(Genre genre);

     int deleteGenre(int genreId);
}
