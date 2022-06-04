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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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

    public List<Loan> getLoansByCustomerId(int customerId){
        String query = "SELECT * FROM loans WHERE customer_id=?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, customerId);
        List<Loan> output = new ArrayList<>();
        while(rowSet.next()){
            output.add(new Loan(
                    rowSet.getInt("book_id"),
                    rowSet.getInt("customer_id"),
                    rowSet.getString("loan_date"),
                    rowSet.getString("return_date")
            ));
        }
        return output;}
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

    public boolean returnBook(int bookId){
        String query = "DELETE FROM loans WHERE book_id=?";
        return jdbcTemplate.update(query, bookId) >= 1;
    }

    public boolean loanBook(String isbn, int customerId, int libraryId){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("loan_book");
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Map<String, String> inParams = new HashMap<>();
        inParams.put("library_id", libraryId+"");
        inParams.put("ISBN", isbn);
        inParams.put("customer_id", customerId+"");
        inParams.put("loan_date", date.format(c.getTime()));
        c.add(Calendar.MONTH, 1);
        inParams.put("return_date", date.format(c.getTime()));

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map map = jdbcCall.execute(in);
        return (int) map.get("succeed") >= 1;
    }

    public List<Loan> getLoanedBooksWithIsbn(String isbn){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_loaned_books_with_isbn")
                .returningResultSet("return", (rs, rn) -> {
                    return new Loan(rs.getInt("book_id"),
                            rs.getInt("customer_id"),
                            rs.getString("loan_date"),
                            rs.getString("return_date")
                    );
                });
        Map<String, String> inParams = new HashMap<>();
        inParams.put("isbn", isbn);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        return (List<Loan>) m.get("return");
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
        BookDetails bookDetails = jdbcTemplate.queryForObject("SELECT * FROM book_details WHERE isbn=?", new BeanPropertyRowMapper<BookDetails>(BookDetails.class),book.getIsbn());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
         String loanDate = dateFormat.format(date);
        jdbcTemplate.update("INSERT INTO loans (book_id,customer_id,loan_date,return_date) VALUES (?,?,?,?)",new Object[]{book.getBook_id(),customerId,loanDate,loanDate});
          String result = "book" + bookDetails.toString() + "loans to " + customerId + "on loan " + loanDate;
        System.out.println(result);
        return result;
    }
    @Override
    public List<Loan> getLoansDueWithinDate(String date) {
        String query = "SELECT * FROM loans WHERE return_date=?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, date);
        List<Loan> output = new ArrayList<>();
        while(rowSet.next()){
            output.add(new Loan(
                    rowSet.getInt("book_id"),
                    rowSet.getInt("customer_id"),
                    rowSet.getString("loan_date"),
                    rowSet.getString("return_date")
            ));
        }
        return output;
    }
}

