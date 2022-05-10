package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDAO;
import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookDetails;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAOImpl implements BookDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book> getBookList() {

        return jdbcTemplate.query("SELECT * FROM books",new BeanPropertyRowMapper<Book>(Book.class));
    }

    @Override
    public Book getBookById(int bookId) throws DataAccessException {
        Book book= jdbcTemplate.queryForObject("SELECT * FROM books WHERE book_id=?",new BeanPropertyRowMapper<Book>(Book.class),bookId);
        return book;
    }

    @Override
    public Book getBookByISBN(String ISBN)  throws  DataAccessException{

        Book book= jdbcTemplate.queryForObject("select * from books WHERE isbn=?",new BeanPropertyRowMapper<Book>(Book.class),ISBN);
        return book;
    }

    @Override
    public int save(String ISBN) {
        System.out.println(ISBN);
        return  jdbcTemplate.update("INSERT INTO books (isbn) VALUES (?)",ISBN);
    }



    @Override
    public int deleteBook(int bookId) {
        return jdbcTemplate.update("DELETE from books WHERE book_id=?", bookId);

    }

    @Override
    public String getBookWithBookId(int bookId) {
        Book book = jdbcTemplate.queryForObject("SELECT * FROM books WHERE book_id=?",new BeanPropertyRowMapper<Book>(Book.class),bookId);
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE isbn=?", new BeanPropertyRowMapper<BookDetails>(BookDetails.class),book.getIsbn());
        String result = "book" + bookDetails.toString() +  "BookId " + bookId;

        return result;
    }

    @Override
    public int ISBNCount(String ISBN) {
        String sql = "SELECT COUNT(*) FROM books WHERE isbn=?";
        return jdbcTemplate.queryForObject(sql, Integer.class,ISBN);
    }
}
