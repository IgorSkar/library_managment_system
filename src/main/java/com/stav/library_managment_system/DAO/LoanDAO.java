package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Loan;

import java.util.List;

public interface LoanDAO {

    List<Loan> getAllLoanList();

    List<Loan> getLoansByCustomerId(int customerId);

    Loan getById( int customerId, int bookId);

    boolean returnBook(int bookId);

    boolean loanBook(String isbn, int customerId, int libraryId);

    List<Loan> getLoanedBooksWithIsbn(String isbn);

     int save (Loan loan);

     int update( Loan loan, int customerId);

     void  delete ( int customerId);


    String saveWithISBN(String isbn, int customerId);


    List<Loan> getLoansDueWithinDate(String date);
}
