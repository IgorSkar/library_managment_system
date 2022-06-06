package com.stav.library_managment_system.google;

import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookDetails;
import com.stav.library_managment_system.utils.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Repository
public class GoogleAPI {

    private static GoogleAPI instance;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private HttpURLConnection connection;

    public GoogleAPI() {
        instance = this;
    }

    public static GoogleAPI inst(){
        return instance;
    }

    private String request(String path){
        String output = "";

        try {
            URL url = new URL("https://www.googleapis.com/" + path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if(status < 300){
                Scanner scanner = new Scanner(connection.getInputStream());
                while(scanner.hasNextLine()){
                    output += scanner.nextLine();
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public JSONObject getBook(String isbn){
        String data = request("books/v1/volumes?q=isbn:" + isbn);
        try {
            JSONArray array = new JSONArray(new JSONObject(data).getJSONArray("items"));
            JSONObject book = new JSONObject();
            try{
                book.put("title", array.getJSONObject(0).getJSONObject("volumeInfo").getString("title"));
            }catch (Exception e){ }
            try{
                book.put("description", array.getJSONObject(0).getJSONObject("volumeInfo").getString("description"));
            }catch (Exception e){ }
            try{
                book.put("authors", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("authors"));
            }catch (Exception e){ }
            try{
                book.put("genres", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("categories"));
            }catch (Exception e){ }
            try{
                book.put("isbn", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(1).getString("identifier"));
            }catch (Exception e){ }
            try{
                book.put("published", array.getJSONObject(0).getJSONObject("volumeInfo").getString("publishedDate"));
            }catch (Exception e){ }
            try{
                book.put("pages", array.getJSONObject(0).getJSONObject("volumeInfo").getInt("pageCount"));
            }catch (Exception e){ }
            try{
                book.put("language", array.getJSONObject(0).getJSONObject("volumeInfo").getString("language"));
            }catch (Exception e){ }
            try{
                book.put("image_source", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail").replace("zoom=1", "zoom=10"));
            }catch (Exception e){ }
            return book;
        }catch (Exception e){
            return getBookByIsbn(isbn);
        }
    }

    private JSONObject getBookByIsbn(String isbn){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_book_by_isbn")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject o = new JSONObject();
                    o.put("title", rs.getString("title"));
                    o.put("description", rs.getString("description"));
                    JSONArray authorsArray = new JSONArray();
                    if(rs.getString("authors") != null) {
                        authorsArray.put(rs.getString("authors").split(","));
                    }
                    o.put("authors", authorsArray);
                    JSONArray genresArray = new JSONArray();
                    if(rs.getString("genres") != null) {
                        authorsArray.put(rs.getString("genres").split(","));
                    }
                    o.put("genres", genresArray);
                    o.put("isbn", rs.getString("isbn"));
                    o.put("published", rs.getString("published"));
                    o.put("pages", rs.getInt("pages"));
                    o.put("language", rs.getString("language"));
                    o.put("image_source", rs.getString("image_source"));
                    return o;
                });
        Map<String, String> inParams = new HashMap<>();
        inParams.put("isbn", isbn);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        if(((ArrayList<JSONObject>) m.get("return")).isEmpty()){
            return new JSONObject();
        }
        return ((ArrayList<JSONObject>) m.get("return")).get(0);
    }

}
