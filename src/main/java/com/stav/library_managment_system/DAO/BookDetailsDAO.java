package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.BookDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface
BookDetailsDAO {
    List<BookDetails> findAll();

    BookDetails getBookByTitle(String title);

    BookDetails findById(int bookDetailsId);

    int save(BookDetails bookDetailsId);

    int update(BookDetails bookDetails, int bookDetailsId);

    int deleteById(int bookDetailsId);

}
