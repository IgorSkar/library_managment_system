package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Loan;

import java.util.List;

public interface LoanDAO {

    List<Loan> getAllLoanList();

    List<Loan> getLoansByCustomerId(int customerId);

    Loan getById( int customerId, int bookId);

    int returnBook(int bookId);

    boolean loanBook(String isbn, int customerId, int libraryId);

     int save (Loan loan);

     int update( Loan loan, int customerId);

     void  delete ( int customerId);


    String saveWithISBN(String isbn, int customerId);
}
