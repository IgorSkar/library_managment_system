package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.LoanDAO;
import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.BookDetails;
import com.stav.library_managment_system.Models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public class LoanDAOImpl implements LoanDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @return  Lista av all loans
     */

    @Override
    public List<Loan> getAllLoanList() {
        return jdbcTemplate.query("SELECT * FROM loans ",new BeanPropertyRowMapper<Loan>(Loan.class));
    }

    /**
     *
     * @param customerId get loan by customerId and bookId
     * @param bookId  get loan by bookId and customerId
     * @return
     * @throws DataAccessException handling of SQL exception
     */

    @Override
    public Loan getById(int customerId, int bookId) throws DataAccessException {
        Loan loan=  jdbcTemplate.queryForObject("SELECT * FROM loans WHERE customer_id=? AND book_id=?", new BeanPropertyRowMapper<Loan>(Loan.class),new Object[]{customerId,bookId});
        return loan;
    }

    /**
     *
     * @param loan create loan
     * @return
     */

    @Override
    public int save(Loan loan) {
        return jdbcTemplate.update("INSERT INTO loans (book_id,customer_id,loan_date,return_date) VALUES (?,?,?,?)",new Object[]{loan.getBook_id(),loan.getCustomer_id(),loan.getLoan_date(),loan.getReturn_date()});
    }
     // behöver vi uppdatera ett befintligt loan iså fall behöver vi uppdatera allt eller räcker det om man vill förlänga return_date bara?
    @Override
    public int update(Loan loan,int customerId) {
        return jdbcTemplate.update("UPDATE loans SET  return_date",new Object[] {loan.getReturn_date()},customerId);
    }

    /**
     *
     * @param customerId delete loan by CustomerId
     */
    @Override
    public void delete( int customerId) {
        jdbcTemplate.update("DELETE FROM loans WHERE customer_id=?",customerId);

    }

    @Override
    public String saveWithISBN(String isbn, int customerId)  throws EmptyResultDataAccessException, DataIntegrityViolationException {
        Book book=  jdbcTemplate.queryForObject("SELECT * FROM books WHERE isbn=? limit 1", new BeanPropertyRowMapper<Book>(Book.class),isbn);
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE book_id=?", new BeanPropertyRowMapper<BookDetails>(BookDetails.class),book.getBook_id());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
         String loanDate = dateFormat.format(date);
        jdbcTemplate.update("INSERT INTO loans (book_id,customer_id,loan_date,return_date) VALUES (?,?,?,?)",new Object[]{book.getBook_id(),customerId,loanDate,loanDate});
          String result = "book" + bookDetails.toString() + "loans to " + customerId + "on loan " + loanDate;
        System.out.println(result);
        return result;

    }


}
