package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Loan;

import java.util.List;

public interface LoanDAO {

    List<Loan> getAllLoanList();

    Loan getById( int customerId, int bookId);


     int save (Loan loan);


     int update( Loan loan, int customerId);

     void  delete ( int customerId);


    String saveWithISBN(String isbn, int customerId);
}
