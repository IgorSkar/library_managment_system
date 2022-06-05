package com.stav.library_managment_system.Controller;
import com.stav.library_managment_system.DAO.Book_QueueDAO;
import com.stav.library_managment_system.DAO.CustomerDAO;
import com.stav.library_managment_system.DAO.LoanDAO;
import com.stav.library_managment_system.Models.Loan;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanDAO loanDAO;
    @Autowired
    private Book_QueueDAO book_queueDAO;
    @Autowired
    private CustomerDAO customerDAO;


    @GetMapping
     public List<Loan> getAllLoan(){
        return loanDAO.getAllLoanList();
    }

    @GetMapping("/{customer_id}")
    List<Loan> getLoansByCustomerId(@PathVariable("customer_id") int customerId){
        return loanDAO.getLoansByCustomerId(customerId);
    }



    @GetMapping("/{customerId}/{bookId}")
    public  ResponseEntity<?> getLoanByCustomerId(@PathVariable int customerId,@PathVariable int bookId){
         Loan loan = null;
        System.out.println("customer with id" + customerId);
        System.out.println("book with id" + bookId);
         try {
              loan = loanDAO.getById(customerId,bookId);
         } catch (DataAccessException e){
             e.printStackTrace();
             return new ResponseEntity<String>("This bookId and customerId is not found in database",HttpStatus.BAD_REQUEST);
         }
         return new ResponseEntity<Loan>(loan,HttpStatus.OK);
    }


    @GetMapping("return_book/{book_id}")
    public boolean returnBook(@PathVariable("book_id") int bookId) {

        boolean result = loanDAO.returnBook(bookId);
        return result;
    }

    @PostMapping("loan")
    public boolean loanBook(@RequestBody String data){
        JSONObject object = new JSONObject(data);
        return loanDAO.loanBook(object.getString("isbn"), object.getInt("customer_id"), object.getInt("library_id"));
    }

    @GetMapping("isbn/{isbn}")
    public List<Loan> getLoanedBooksWithIsbn(@PathVariable("isbn") String isbn){
        return loanDAO.getLoanedBooksWithIsbn(isbn);
    }

    @PostMapping()
    public  ResponseEntity<?> createLoan(@RequestBody Loan loan){
        int result = loanDAO.save(loan);
        if (result == -1){
            return  new ResponseEntity<String>("Something was wrong",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Loan added successfully!" +loan.getLoan_date(), HttpStatus.OK);

    }
     @PostMapping("/add")
     public ResponseEntity<?> createLoanWithISBN(@RequestParam String ISBN,@RequestParam int customerId){
         String result = "";
        try {
             result = loanDAO.saveWithISBN(ISBN,customerId);
        } catch (DataIntegrityViolationException exception){
            exception.printStackTrace();
             return  new ResponseEntity<String>("oops wrong customerId",HttpStatus.BAD_REQUEST);
        }catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new ResponseEntity<String>("oops wrong ISBN",HttpStatus.BAD_REQUEST);
        }

         return  new ResponseEntity<String>("Loan added" + result,HttpStatus.OK);
     }


    @PutMapping("/{customerId}")
    public  ResponseEntity<?> updateLoan(@RequestBody Loan loan,@PathVariable int customerId){
        int result = loanDAO.update(loan,customerId);

        return  new ResponseEntity<String>(" Loan updated successfully!",HttpStatus.OK) ;
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> deleteLoanByCustomerId(@PathVariable int customerId){
         loanDAO.delete(customerId);
        return  new ResponseEntity<String>("Loan deleted successfully",HttpStatus.OK);
    }
}



