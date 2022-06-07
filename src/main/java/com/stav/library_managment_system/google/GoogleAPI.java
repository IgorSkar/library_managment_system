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
            return null;
        }
    }
}
