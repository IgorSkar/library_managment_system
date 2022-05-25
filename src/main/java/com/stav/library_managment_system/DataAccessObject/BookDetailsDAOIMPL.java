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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class BookDetailsDAOIMPL implements BookDetailsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<JSONObject> findAll(String language, String releaseDate, String library, String searchType, String search) {
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
                    o.put("available_libraries", rs.getString("available_libraries") == null ? new String[]{} : rs.getString("available_libraries").split(","));
                    return o;
                });
        Map<String, String> inParams = new HashMap<>();
        inParams.put("language", language);
        inParams.put("releaseDate", releaseDate);
        inParams.put("library", library);
        inParams.put("searchType", searchType);
        inParams.put("search", search);
        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        //filtering by search terms
        List<JSONObject> books = ((List<JSONObject>) m.get("return")).stream().filter(o -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return
                        (!language.equalsIgnoreCase("") ? o.getString("language").equalsIgnoreCase(language) : true) &&
                        (!releaseDate.equalsIgnoreCase("") ? !sdf.parse(o.getString("published")).before(sdf.parse(releaseDate)) : true) &&
                        (!library.equalsIgnoreCase("") ? Arrays.stream((String[]) o.get("available_libraries")).anyMatch(s -> s.toLowerCase().contains(library.toLowerCase())) : true) &&
                        (searchType.equalsIgnoreCase("titel") ? o.getString("title").toLowerCase().contains(search.toLowerCase()) : true) &&
                        (searchType.equalsIgnoreCase("författare") ? Arrays.stream((String[]) o.get("authors")).anyMatch(s -> s.toLowerCase().contains(search.toLowerCase())) : true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).toList();
        //
        return books;
    }

    @Override
    public BookDetails getBookByTitle(String title)  throws  DataAccessException{
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE title=?",  new BeanPropertyRowMapper<BookDetails>(BookDetails.class),title);
        return  bookDetails;
    }

    @Override
    public JSONObject findByISBN(String isbn) throws DataAccessException {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_book_by_isbn")
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
                    return o;
                });
        Map<String, String> inParams = new HashMap<>();
        inParams.put("isbn", isbn);
        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = simpleJdbcCall.execute(in);
        System.out.println(((ArrayList<JSONObject>) m.get("return")).get(0));
        return ((ArrayList<JSONObject>) m.get("return")).get(0);
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
