package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Book;
import org.json.JSONObject;

import java.util.List;

public interface BookDAO {

    List<Book> getBookList();

    JSONObject getBookById(int bookId);

    Book getBookByISBN(String ISBN);

    int save(String ISBN, int libraryId);

    boolean deleteBook(int bookId);

    String getBookWithBookId(int bookId);

     int getAmountOfBooks(String ISBN);

    int getAmountOfBooksInStock(String isbn);

    List<JSONObject> getCopiesInLibrary(int libraryId, String isbn);

    List<JSONObject> getAmountOfBookInLibraries(String isbn);

    String getBookByTitleAndISBN(String title, String ISBN);

    boolean addCopies(String isbn, int libraryId, int amount);

}


