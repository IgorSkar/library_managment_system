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
    public int add(BookSuggestion bookSuggestion) {
        return jdbcTemplate.update("INSERT INTO book_suggestion(isbn, title, author, language) VALUES(?,?,?,?)", new Object[]{bookSuggestion.getIsbn(), bookSuggestion.getTitle(), bookSuggestion.getAuthor(), bookSuggestion.getLanguage()});
    }

    @Override
    public void deleteById(int book_suggestion_id) {
        jdbcTemplate.update("DELETE FROM book_suggestion WHERE book_suggestion_id=?", book_suggestion_id);
    }

}
