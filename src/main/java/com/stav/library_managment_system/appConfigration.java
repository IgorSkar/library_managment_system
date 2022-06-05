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
         List<Customer> customers = new ArrayList<>();
         loansDueTomorrow.forEach(loan -> {
             Customer customer= customerDAO.getById(loan.getCustomer_id());
           customers.add(customer);
             System.out.println(customer.getEmail());

         } );

        // skicka email dessa email
         customers.forEach(to ->{
            emailSender.send(to);
             System.out.println("email send successfully!");

         });



    }

}
