package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Book;

import java.util.List;

public interface BookDAO {


    List<Book> getBookList();


    Book getBookById(int bookId);


    Book getBookByISBN(String ISBN);


    int save(String ISBN);


    int  deleteBook(int bookId);

    String getBookWithBookId(int bookId);

     int ISBNCount();

}


