package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.BookDetails;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
BookDetailsDAO {
    List<JSONObject> findAll(String language, String releaseDate, String library, String searchType, String search, String popularSort);

    List<JSONObject> getBooksByGenre(String[] genre);

    BookDetails getBookByTitle(String title);

    JSONObject findByISBN(String ISBN);

    int save(BookDetails bookDetailsId);

    int update(BookDetails bookDetails, String ISBN);

    int deleteById(String  ISBN);

    boolean addBook(JSONObject object);

    boolean addBook (String title, String description, String authors, String genres, String isbn, String published, int page_count, String language, String image);

}
