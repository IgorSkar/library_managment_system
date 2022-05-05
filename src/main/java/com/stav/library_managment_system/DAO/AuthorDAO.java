package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AuthorDAO {


    List<Author> getAllAuthors();


    Author getAuthorByName(String name);

    int save(Author author);


    Author getAuthorById(int authorId);



    int update(Author author, int authorId);


    void delete(int authorId);


}
