package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.BookDetails;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
BookDetailsDAO {
    List<JSONObject> findAll();

    BookDetails getBookByTitle(String title);

    BookDetails findByISBN(String ISBN);

    int save(BookDetails bookDetailsId);

    int update(BookDetails bookDetails, String ISBN);

    int deleteById(String  ISBN);

}
