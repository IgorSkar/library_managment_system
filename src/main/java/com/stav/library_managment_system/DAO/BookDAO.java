package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDAO {

    List<Book> findAll();

    Book findById(int book_d);

    int  save(Book book);

    int update(Book book, int book_id);

    int deleteById(int book_id);

}
