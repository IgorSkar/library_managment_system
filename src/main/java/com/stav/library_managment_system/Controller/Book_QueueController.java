package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.Book_QueueDAO;
import com.stav.library_managment_system.Models.Book_Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book_queue")
public class Book_QueueController {
    @Autowired
    private  Book_QueueDAO book_queueDAO;


    @GetMapping()
    public  List<Book_Queue>  getAllBook_Queues(){
        return book_queueDAO.getAllBook_Queue();
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<?> getBook_QueueByCustomerId(@PathVariable int customerId){
          Book_Queue book_queue = null;
        System.out.println("customer with id" + customerId);
          try {
            book_queue =  book_queueDAO.getBook_QueueById(customerId);
          }catch (DataAccessException e){
              e.printStackTrace();
              return  new ResponseEntity<String>("customer id not found in the database", HttpStatus.BAD_REQUEST);
          }
            return  new ResponseEntity<Book_Queue>(book_queue,HttpStatus.OK);
    }


      @PostMapping()

       ResponseEntity<?> createBook_Queue( @RequestBody Book_Queue book_queue){

         int result = book_queueDAO.create(book_queue);
         if (result == -1){
             return  new ResponseEntity<String>("oops something was wrong",HttpStatus.BAD_REQUEST);
         }
         return  new ResponseEntity<String>("Reservation added successfully!" + book_queue.getQueue_date(),HttpStatus.CREATED);
      }

      @DeleteMapping("/{customerId}")
    public  ResponseEntity<?> deleteBook_QueueByCustomerId(@PathVariable int customerId){
         book_queueDAO.deleteBook_QueueByCustomerId(customerId);
         return  new ResponseEntity<String>("Reservation deleted successfully!", HttpStatus.OK);
      }
}