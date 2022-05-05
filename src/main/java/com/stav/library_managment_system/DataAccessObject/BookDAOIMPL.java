package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDAO;
import com.stav.library_managment_system.Models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAOIMPL implements BookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<Book>(Book.class));
    }

    @Override
    public Book findById(int bookId) throws DataAccessException {
        Book book = jdbcTemplate.queryForObject("SELECT * FROM books WHERE book_id=?", new BeanPropertyRowMapper<Book>(Book.class), bookId);
        return book;
    }


    @Override
    public int save(Book book) {
        return jdbcTemplate.update("INSERT INTO books (title,description,author_id,genre,language, isbn,publication_date) VALUES  (?,?,?,?,?,?,?)", new Object[]{book.getTitle(), book.getDescription(), book.getAuthor_id(), book.getGenre(), book.getLanguage(), book.getIsbn(), book.getPublication_date()});

    }


    @Override
    public int update(Book book, int bookId) {
        return jdbcTemplate.update("UPDATE books SET title = ?, description = ?,language = ?, genre = ? WHERE book_id = ?", new Object[]{book.getTitle(), book.getDescription(), book.getGenre(), book.getLanguage(), bookId});
    }

    @Override
    public int deleteById(int bookId) {
        return jdbcTemplate.update("DELETE FROM books WHERE book_id=?", bookId);
    }
}
