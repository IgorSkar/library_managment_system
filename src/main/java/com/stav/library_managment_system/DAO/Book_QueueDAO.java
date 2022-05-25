package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Book_Queue;

import java.util.List;

public interface Book_QueueDAO {


        List<Book_Queue> getAllBook_Queue();


      Book_Queue getBook_QueueById(int customerId);


       int create (Book_Queue book_queue);


      int deleteBook_QueueByCustomerId(int customerId);



     int isInQueue(String ISBN, int customerId);



    int  reserveBook(String ISBN, int customerId);


     int getAmountInQueue(String ISBN);

     List<Book_Queue> getReservedBoks(int customerId);
}
