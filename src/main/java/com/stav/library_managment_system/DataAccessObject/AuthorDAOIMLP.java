package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.Models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuthorDAOIMLP implements AuthorDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Author> getAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM authors", new BeanPropertyRowMapper<Author>(Author.class));
    }

    @Override
    public Author getAuthorById(int authorId) throws DataAccessException {
        Author author = jdbcTemplate.queryForObject("SELECT * FROM authors WHERE author_id =?", new BeanPropertyRowMapper<Author>(Author.class), authorId);
        return author;
    }


    @Override
    public int save(Author author) {
        return  jdbcTemplate.update("INSERT INTO authors (name) VALUES (?)",new Object[]{author.getName()});

    }



    @Override
    public int update(Author author, int authorId) {
        return jdbcTemplate.update("UPDATE authors SET name =?  Where author_id = ?",new Object[] {author.getName(),authorId});
    }

    @Override
    public void delete(int authorId) {
        jdbcTemplate.update("DELETE  FROM authors WHERE author_id=?",authorId);

    }
}
