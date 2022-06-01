package com.stav.library_managment_system.google;

import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookDetails;
import com.stav.library_managment_system.utils.StringUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GoogleAPI {

    private static GoogleAPI instance = new GoogleAPI();

    private HttpURLConnection connection;

    private GoogleAPI() { }

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
        JSONArray array = new JSONArray(new JSONObject(data).getJSONArray("items"));
        JSONObject book = new JSONObject();
        book.put("title", array.getJSONObject(0).getJSONObject("volumeInfo").getString("title"));
        book.put("description", array.getJSONObject(0).getJSONObject("volumeInfo").getString("description"));
        book.put("authors", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("authors"));
        book.put("genres", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("categories"));
        book.put("isbn", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(1).getString("identifier"));
        book.put("published", array.getJSONObject(0).getJSONObject("volumeInfo").getString("publishedDate"));
        book.put("pages", array.getJSONObject(0).getJSONObject("volumeInfo").getInt("pageCount"));
        book.put("language", array.getJSONObject(0).getJSONObject("volumeInfo").getString("language"));
        book.put("image_source", array.getJSONObject(0).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail").replace("zoom=1", "zoom=10"));
        return book;
    }

}
