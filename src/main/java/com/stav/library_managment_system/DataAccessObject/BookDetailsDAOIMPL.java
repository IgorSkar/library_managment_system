package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookDetailsDAO;
import com.stav.library_managment_system.Models.BookDetails;
import com.stav.library_managment_system.google.GoogleAPI;
import com.stav.library_managment_system.utils.StringUtil;
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

    public List<JSONObject> getBooks(){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_books")
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
                    o.put("popular_all_time", rs.getInt("popular_all_time"));
                    o.put("popular_year", rs.getInt("popular_year"));
                    o.put("popular_month", rs.getInt("popular_month"));
                    o.put("popular_week", rs.getInt("popular_week"));
                    return o;
                });
        Map m = jdbcCall.execute();
        return (List<JSONObject>) m.get("return");
    }

    @Override
    public List<JSONObject> findAll(String language, String releaseDate, String library, String searchType, String search, String popularSort) {
        //filtering by search terms
        List<JSONObject> books = getBooks().stream().filter(o -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return
                        (!language.equalsIgnoreCase("") ? o.getString("language").equalsIgnoreCase(language) : true) &&
                        (!releaseDate.equalsIgnoreCase("") ? !sdf.parse(o.getString("published")).before(sdf.parse(releaseDate)) : true) &&
                        (!library.equalsIgnoreCase("") ? Arrays.stream((String[]) o.get("available_libraries")).anyMatch(s -> s.equalsIgnoreCase(library)) : true) &&
                        (searchType.equalsIgnoreCase("titel") ||searchType.equalsIgnoreCase("") ? o.getString("title").toLowerCase().contains(search.toLowerCase()) : true) &&
                        (searchType.equalsIgnoreCase("författare") ? Arrays.asList((String[]) o.get("authors")).isEmpty() ? search.equals("") ? true : false : Arrays.stream((String[]) o.get("authors")).anyMatch(s -> s.toLowerCase().contains(search.toLowerCase())) : true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
        //
        switch(popularSort.toLowerCase()){
            case "":
                break;
            case "all_time":
                books.sort(Comparator.comparing(o -> ((JSONObject)o).getInt("popular_all_time")).reversed());
                break;
            case "year":
                books.sort(Comparator.comparing(o -> ((JSONObject)o).getInt("popular_year")).reversed());
                break;
            case "month":
                books.sort(Comparator.comparing(o -> ((JSONObject)o).getInt("popular_month")).reversed());
                break;
            case "week":
                books.sort(Comparator.comparing(o -> ((JSONObject)o).getInt("popular_week")).reversed());
                break;
        }
        return books;
    }

    public List<JSONObject> getBooksByGenre(String[] genres){
        for (String genre : genres) {
            System.out.println(genre);
        }
        List<JSONObject> books = getBooks().stream().filter(o -> Arrays.stream((String[]) o.get("genres")).anyMatch(s -> Arrays.stream(genres).anyMatch(g -> s.equalsIgnoreCase(g)))).collect(Collectors.toList());
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

    public boolean addBook(JSONObject o){

        JSONObject output = new JSONObject();

        String isbn = o.getString("isbn");
        JSONObject object = GoogleAPI.inst().getBook(isbn);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_book");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("title", object.getString("title"));
        inParams.put("description", object.getString("description"));
        inParams.put("authors", String.join(",", StringUtil.convertJSONArrayToStringArray(object.getJSONArray("authors"))));
        inParams.put("genres", String.join(",", StringUtil.convertJSONArrayToStringArray(object.getJSONArray("genres"))));
        inParams.put("isbn", object.getString("isbn"));
        inParams.put("published", object.getString("published"));
        inParams.put("page_count", object.getInt("pages")+"");
        inParams.put("language", object.getString("language"));
        inParams.put("image_source", object.getString("image_source"));

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);

        return (int) m.get("succeed") >= 1;
    }
    public boolean addBook (String title, String description, String authors, String genres, String isbn, String published, int page_count, String language, String image) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("add_book");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("title", title);
        inParams.put("description", description);
        inParams.put("authors", authors);
        inParams.put("genres", genres);
        inParams.put("isbn", isbn);
        inParams.put("published", published);
        inParams.put("page_count", page_count +"");
        inParams.put("language", language);
        inParams.put("image_source", image);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (int) jdbcCall.execute(in).get("succeed") >= 1;
    }
}
