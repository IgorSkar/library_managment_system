package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDAO;
import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookDetails;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDAOImpl implements BookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book> getBookList() {

        return jdbcTemplate.query("SELECT * FROM books",new BeanPropertyRowMapper<Book>(Book.class));
    }

    @Override
    public JSONObject getBookById(int bookId) throws DataAccessException {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_book_by_id")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject o = new JSONObject();
                    o.put("book_id", rs.getInt("book_id"));
                    o.put("isbn", rs.getString("isbn"));
                    o.put("library_id", rs.getInt("library_id"));
                    o.put("title", rs.getString("title"));
                    o.put("description", rs.getString("description"));
                    o.put("language", rs.getString("language"));
                    o.put("published", rs.getString("published"));
                    o.put("image_source", rs.getString("image_source"));
                    o.put("pages", rs.getInt("pages"));
                    o.put("authors", rs.getString("authors") == null ? new String[]{} : rs.getString("authors").split(","));
                    o.put("genres", rs.getString("genres") == null ? new String[]{} : rs.getString("genres").split(","));
                    return o;
                });
        Map<String, String> inParams = new HashMap<>();
        inParams.put("bookId", bookId+"");

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        System.out.println(123123);
        System.out.println(((List<JSONObject>) m.get("return")).get(0) + "12312123");
        return ((List<JSONObject>) m.get("return")).get(0);
    }

    @Override
    public Book getBookByISBN(String ISBN)  throws  DataAccessException{

        Book book= jdbcTemplate.queryForObject("select * from books WHERE isbn=?",new BeanPropertyRowMapper<Book>(Book.class),ISBN);
        return book;
    }

    @Override
    public int save(String ISBN, int libraryId) {
        System.out.println(ISBN);
        return  jdbcTemplate.update("INSERT INTO books (isbn, library_id) VALUES (?,?)",ISBN, libraryId);
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

    public List<JSONObject> getAmountOfBookInLibraries(String isbn){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("available_amount_of_book_in_libraries")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject object = new JSONObject();
                    object.put("library_id", rs.getString("library_id"));
                    object.put("name", rs.getString("name"));
                    object.put("amount", rs.getInt("amount"));
                    return object;
                });
        Map<String, String> inParams = new HashMap<>();
        inParams.put("isbn", isbn);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        return (List<JSONObject>) m.get("return");
    }
}
