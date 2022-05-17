package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDetailsDAO;
import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookDetails;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDetailsDAOIMPL implements BookDetailsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

  // funkar inte
    @Override
    public List<JSONObject> findAll() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_books")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject o = new JSONObject();
                    o.put("isbn", rs.getString("isbn"));
                    o.put("title", rs.getString("title"));
                    o.put("description", rs.getString("description"));
                    o.put("language", rs.getString("language"));
                    o.put("published", rs.getString("published"));
                    o.put("image_source", rs.getString("image_source"));
                    o.put("pages", rs.getInt("pages"));
                    o.put("authors", rs.getString("authors") == null ? new String[]{} : rs.getString("authors").split(","));
                    o.put("genres", rs.getString("genres") == null ? new String[]{} : rs.getString("genres").split(","));
                    System.out.println(o.toString());
                    return o;
                });
        Map m = jdbcCall.execute();
        return ((List<JSONObject>) m.get("return"));
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
