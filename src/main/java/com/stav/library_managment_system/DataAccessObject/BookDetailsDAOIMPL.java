package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDetailsDAO;
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
public class BookDetailsDAOIMPL implements BookDetailsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

  // funkar inte
    @Override
    public List<BookDetails> findAll() {
        return jdbcTemplate.query("SELECT * FROM book_details", new BeanPropertyRowMapper<BookDetails>(BookDetails.class));
    }

    @Override
    public BookDetails getBookByTitle(String title)  throws  DataAccessException{
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE title=?",  new BeanPropertyRowMapper<BookDetails>(BookDetails.class),title);
        return  bookDetails;
    }

    @Override
    public BookDetails findByISBN(String ISBN) throws DataAccessException {
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE isbn=?", new BeanPropertyRowMapper<BookDetails>(BookDetails.class),ISBN);
        return bookDetails;
    }


    @Override
    public int save(BookDetails bookDetails) {
        return jdbcTemplate.update("INSERT INTO book_details (isbn,title,description,language, published,image_source,pages) VALUES  (?,?,?,?,?,?,?)",new Object[]{bookDetails.getIsbn(),bookDetails.getTitle(),bookDetails.getDescription(),bookDetails.getLanguage(),bookDetails.getPublished(),bookDetails.getImage_source(),bookDetails.getPages()});
    }


    @Override
    public int update(BookDetails bookDetails, String ISBN) {
        return jdbcTemplate.update("UPDATE book_details SET  description = ?,language = ? WHERE isbn = ?", new Object[]{bookDetails.getDescription(),bookDetails.getLanguage()},ISBN);
    }

    @Override
    public int deleteById(String ISBN) {
        return jdbcTemplate.update("DELETE FROM book_details WHERE isbn=?", ISBN);
    }
}
