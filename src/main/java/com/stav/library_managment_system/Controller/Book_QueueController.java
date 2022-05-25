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


    @GetMapping("/customer/{customerId}")
    public List<Book_Queue> getReservedBooks(@PathVariable int customerId){
        return book_queueDAO.getReservedBooks(customerId);
    }


    @GetMapping("/customers/{customerId}")
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

    @GetMapping("/reserve")
    public  boolean reserveBook(@RequestParam String isbn, @RequestParam int customerId){
        return book_queueDAO.reserveBook(isbn,customerId);
    }

      @GetMapping("/ISBN")
      public int getAmountInQueue(@RequestParam String ISBN){
        return book_queueDAO.getAmountInQueue(ISBN);
      }


      @GetMapping("/in_queue")
      public boolean isInQueue(@RequestParam String isbn, @RequestParam int customerId){
        return book_queueDAO.isInQueue(isbn,customerId);
      }

      @PostMapping()

       ResponseEntity<?> createBook_Queue( @RequestBody Book_Queue book_queue){

         int result = book_queueDAO.create(book_queue);
         if (result == -1){
             return  new ResponseEntity<String>("oops something was wrong",HttpStatus.BAD_REQUEST);
         }
         return  new ResponseEntity<String>("Reservation added successfully!" + book_queue.getQueue_date(),HttpStatus.CREATED);
      }

      @GetMapping("/leave_queue")
      public  ResponseEntity<?> deleteBook_QueueByCustomerId(@RequestParam String isbn, @RequestParam int customerId){
          System.out.println(customerId);
         book_queueDAO.deleteBook_QueueByCustomerId(isbn, customerId);
         return  new ResponseEntity<String>("Reservation deleted successfully!", HttpStatus.OK);
      }
}
