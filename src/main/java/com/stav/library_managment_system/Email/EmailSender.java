package com.stav.library_managment_system.Email;


import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.Customer;
import com.stav.library_managment_system.Models.Loan;

public interface EmailSender {
    void send(Customer customer);
}
