package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDAO;
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
public class BookDAOImpl implements BookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book> getBookList() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
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
        inParams.put("bookId", bookId + "");

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);

        return ((List<JSONObject>) m.get("return")).get(0);
    }

    @Override
    public Book getBookByISBN(String ISBN) throws DataAccessException {

        Book book = jdbcTemplate.queryForObject("select * from books WHERE isbn=?", new BeanPropertyRowMapper<Book>(Book.class), ISBN);
        return book;
    }

    @Override
    public int save(String ISBN, int libraryId) {
        return jdbcTemplate.update("INSERT INTO books (isbn, library_id) VALUES (?,?)", ISBN, libraryId);
    }

    @Override
    public boolean deleteBook(int bookId) {
        return jdbcTemplate.update("DELETE FROM books WHERE book_id=?", bookId) >= 1;
    }

    @Override
    public String getBookWithBookId(int bookId) {
        Book book = jdbcTemplate.queryForObject("SELECT * FROM books WHERE book_id=?", new BeanPropertyRowMapper<Book>(Book.class), bookId);
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE isbn=?", new BeanPropertyRowMapper<BookDetails>(BookDetails.class), book.getIsbn());
        String result = "book" + bookDetails.toString() + "BookId " + bookId;

        return result;
    }

    @Override
    public int getAmountOfBooks(String ISBN) {
        String sql = "SELECT COUNT(*) FROM books WHERE isbn=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, ISBN);
    }

    @Override
    public int getAmountOfBooksInStock(String isbn) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_amount_of_books_in_stock")
                .returningResultSet("return", (rs, rn) -> rs.getInt("amount"));
        Map<String, String> inParams = new HashMap<>();
        inParams.put("isbn", isbn);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map map = jdbcCall.execute(in);
        return ((List<Integer>) map.get("return")).get(0);
    }

    public List<JSONObject> getAmountOfBookInLibraries(String isbn) {
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

    public List<JSONObject> getCopiesInLibrary(int libraryId, String isbn){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_copies_in_library")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject o = new JSONObject();
                    o.put("book_id", rs.getInt("book_id"));
                    o.put("available", rs.getInt("available"));
                    return o;
                });
        Map<String, String> inParams = new HashMap<>();
        inParams.put("library_id", libraryId+"");
        inParams.put("isbn", isbn);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (List<JSONObject>) jdbcCall.execute(in).get("return");
    }

    @Override
    public String getBookByTitleAndISBN(String title, String ISBN) {
        Book book = jdbcTemplate.queryForObject("SELECT * FROM books WHERE isbn=?", new BeanPropertyRowMapper<Book>(Book.class), ISBN, title);
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE title=?", new BeanPropertyRowMapper<BookDetails>(BookDetails.class), book.getIsbn());
        String result = "book" + bookDetails.toString() + "title" + title + "book" + book.toString() + "ISBN" + ISBN;
        return result;

    }

    public boolean addCopies(String isbn, int libraryId, int amount){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_copies");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("isbn", isbn);
        inParams.put("library_id", libraryId+"");
        inParams.put("amount", amount+"");

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (int) jdbcCall.execute(in).get("succeed") >= 1;
    }

}

