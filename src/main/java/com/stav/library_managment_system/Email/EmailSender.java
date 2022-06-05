package com.stav.library_managment_system.Email;


import com.stav.library_managment_system.Models.Customer;

public interface EmailSender {
    void send(Customer customer);
}
