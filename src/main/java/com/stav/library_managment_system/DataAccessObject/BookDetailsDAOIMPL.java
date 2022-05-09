package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDetailsDAO;
import com.stav.library_managment_system.Models.BookDetails;
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
    public BookDetails findById(int bookDetailsId) throws DataAccessException {
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE book_id=?", new BeanPropertyRowMapper<BookDetails>(BookDetails.class),bookDetailsId);
        return bookDetails;
    }


    @Override
    public int save(BookDetails bookDetails) {
        return jdbcTemplate.update("INSERT INTO book_details (book_id,title,description,language, published,image_source,pages) VALUES  (?,?,?,?,?,?,?)",new Object[]{bookDetails.getBook_id(),bookDetails.getTitle(),bookDetails.getDescription(),bookDetails.getLanguage(),bookDetails.getPublished(),bookDetails.getImage_source(),bookDetails.getPages()});
    }


    @Override
    public int update(BookDetails bookDetails, int bookDetailsId) {
        return jdbcTemplate.update("UPDATE book_details SET  description = ?,language = ? WHERE book_id = ?", new Object[]{bookDetails.getDescription(),bookDetails.getLanguage()},bookDetailsId);
    }

    @Override
    public int deleteById(int bookDetailsId) {
        return jdbcTemplate.update("DELETE FROM book_details WHERE book_id=?", bookDetailsId);
    }
}
