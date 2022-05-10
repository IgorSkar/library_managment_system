package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Book;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface BookDAO {


    List<Book> getBookList();


    JSONObject getBookById(int bookId);


    Book getBookByISBN(String ISBN);


    int save(String ISBN, int libraryId);


    int  deleteBook(int bookId);

    String getBookWithBookId(int bookId);

     int ISBNCount(String ISBN);

    List<JSONObject> getAmountOfBookInLibraries(String isbn);

}


