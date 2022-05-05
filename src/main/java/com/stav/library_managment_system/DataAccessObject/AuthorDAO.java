package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.Models.Author;

import java.util.List;

public interface AuthorDAO {


   List<Author> getAllAuthors();


    int save(Author author);


    Author getAuthorById(int author_id);



    int update(Author author, int authorId);


    void delete(int authorId);


}
