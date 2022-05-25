package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.Book_QueueDAO;
import com.stav.library_managment_system.Models.Book_Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Repository
public class Book_QueueDAOImpl implements Book_QueueDAO {

     @Autowired
     private JdbcTemplate jdbcTemplate;


    @Override
    public List<Book_Queue> getAllBook_Queue() {
        return jdbcTemplate.query("SELECT * FROM book_queue",new BeanPropertyRowMapper<Book_Queue>(Book_Queue.class));
    }



     @Override
    public Book_Queue getBook_QueueById(int customerId) throws DataAccessException {
          Book_Queue book_queue =  jdbcTemplate.queryForObject("SELECT * FROM book_queue WHERE customer_id=?", new BeanPropertyRowMapper<Book_Queue>(Book_Queue.class),new Object[]{customerId});
        return book_queue;
    }


    @Override
    public int create(Book_Queue book_queue) {
        return jdbcTemplate.update("INSERT INTO book_queue (queue_date,isbn,customer_id)VALUES (?,?,?)", new Object []{book_queue.getQueue_date(),book_queue.getIsbn(),book_queue.getCustomer_id()});
    }

    @Override
    public int deleteBook_QueueByCustomerId(String isbn, int customerId) {
        jdbcTemplate.update("DELETE FROM book_queue WHERE isbn = ? AND customer_id=?",isbn,customerId);
        return customerId;
    }

    @Override
    public boolean isInQueue(String isbn, int customerId) {
      String query = "SELECT COUNT(*) FROM book_queue WHERE isbn = ? AND customer_id = ?";
      return jdbcTemplate.queryForObject(query, Integer.class, isbn, customerId) >= 1;
    }

    @Override
    public boolean reserveBook(String isbn, int customerId) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(c.getTime());
        int book_queue = jdbcTemplate.update("INSERT INTO book_queue (queue_date,isbn,customer_id)VALUES (?,?,?)", time, isbn, customerId);
        return book_queue >= 1;
    }

    @Override
    public int getAmountInQueue(String ISBN) {
     return jdbcTemplate.queryForObject("SELECT COUNT(isbn) From book_queue WHERE isbn=?" , Integer.class,ISBN);

    }

    @Override
    public List<Book_Queue> getReservedBooks(int customerId) {
        return jdbcTemplate.query("SELECT * FROM book_queue WHERE customer_id=? ",new BeanPropertyRowMapper<Book_Queue>(Book_Queue.class), customerId);
    }
}


