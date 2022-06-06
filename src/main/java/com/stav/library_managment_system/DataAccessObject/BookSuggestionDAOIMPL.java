package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookSuggestionDAO;
import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookSuggestionDAOIMPL implements BookSuggestionDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BookSuggestion> getBookSuggestion() {
        return jdbcTemplate.query("SELECT * FROM book_suggestion",new BeanPropertyRowMapper<>(BookSuggestion.class));
    }

    @Override
    public boolean add(String title, String authors, String isbn, String language) {
        return jdbcTemplate.update("INSERT INTO book_suggestion(title, authors, isbn, language) VALUES(?,?,?,?)", title, authors, isbn, language) >= 1;
    }

    @Override
    public boolean deleteById(int book_suggestion_id) {
        return jdbcTemplate.update("DELETE FROM book_suggestion WHERE book_suggestion_id=?", book_suggestion_id) >= 1;
    }

}
