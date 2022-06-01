package com.stav.library_managment_system;
import com.stav.library_managment_system.DAO.BookDAO;
import com.stav.library_managment_system.DAO.Book_QueueDAO;
import com.stav.library_managment_system.DAO.CustomerDAO;
import com.stav.library_managment_system.DAO.LoanDAO;
import com.stav.library_managment_system.Email.EmailSender;
import com.stav.library_managment_system.Models.Book;
import com.stav.library_managment_system.Models.Book_Queue;
import com.stav.library_managment_system.Models.Customer;
import com.stav.library_managment_system.Models.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling

public class appConfigration {
  @Autowired
    private LoanDAO loanDAO;
  @Autowired
  private CustomerDAO customerDAO;
  @Autowired
  private EmailSender emailSender;
  @Autowired
  private BookDAO bookDAO;
  @Autowired
  private Book_QueueDAO book_queueDAO;

    @Scheduled(fixedRateString = "${email.schedule.time}")
    public void sendSimpleEmail(){
      // System.out.println(" Send email to User ");
    //   kolla dagens datum och imorgons datum.
      Date dt = new Date();
      Calendar c = Calendar.getInstance();
      c.setTime(dt);
      c.add(Calendar.DATE, 1);
      dt = c.getTime();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String tomorrow =  sdf.format(dt);
      // hämta alla loan som ska lämnas imorgon
      List<Loan> loansDueTomorrow = loanDAO.getLoansDueWithinDate(tomorrow);
        System.out.println(loansDueTomorrow.size());

          // hämta alla emails  för  låntagare
         List<String> emails = new ArrayList<>();
         loansDueTomorrow.forEach(loan -> {
             Customer customer= customerDAO.getById(loan.getCustomer_id());
           emails.add(customer.getEmail());
             System.out.println(customer.getEmail());

         } );

        // skicka email dessa email
         emails.forEach(to ->{
            emailSender.send(to,"Hej det är dags att lämna boken du har!" );
             System.out.println("email send successfully!");

         });



    }

      /* @Scheduled(cron = "0/15 *  * * * *")
       public  void  fetchDBJob(){

           // kolla om isbn boken finns i databasen i books tabelen

           List<Book>  getBooksWithISBN = bookDAO.getBookByISBN();
           System.out.println(" retrieve books by ISBN from database at:" + getBooksWithISBN);

           // kolla om det finns  flera customer som står kö för boken

           List<Book_Queue>  getReservationWithAllCustomer = book_queueDAO.isInQueue();
           System.out.println("fetched reservations with customer from database at: " + new Date().toString() + getReservationWithAllCustomer);
           System.out.println("number of queues:" + getReservationWithAllCustomer.size());



           // ge boken den som har reserverat först genom att kolla tiden
              // hur ska jag dubbelkolla tiden den som har reserverat först

           List<String> emails = new ArrayList<>();
           getReservationWithAllCustomer.forEach(book_Queue -> {
               Customer customer= customerDAO.getById(book_Queue.getCustomer_id());
               emails.add(customer.getEmail());
               System.out.println(customer.getEmail());

           } );

           // maila den customer som ska få boken
           emails.forEach(to ->{
               emailSender.send(to,"Hej boken du reserverade  är nu tillgänglig att hämta!" );
               System.out.println("email send successfully!");

           });
       }

       */


}
