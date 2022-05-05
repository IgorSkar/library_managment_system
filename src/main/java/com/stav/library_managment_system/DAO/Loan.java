package com.stav.library_managment_system.DAO;

import java.util.List;

public interface Loan {

    List<Loan> getAllLoanList();


    Loan getById(int bookId);


     int save (Loan loan);


}
