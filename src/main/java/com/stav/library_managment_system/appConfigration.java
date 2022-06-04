package com.stav.library_managment_system;
import com.stav.library_managment_system.DAO.CustomerDAO;
import com.stav.library_managment_system.DAO.LoanDAO;
import com.stav.library_managment_system.Email.EmailSender;
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

   // @Scheduled(cron = "0/1440 *  * * * *")
    @Scheduled(fixedRateString = "${email.schedule.time}")
    public void sendSimpleEmail(){
      Date dt = new Date();
      Calendar c = Calendar.getInstance();
      c.setTime(dt);
      c.add(Calendar.DATE, 1);
      dt = c.getTime();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String tomorrow =  sdf.format(dt);

      List<Loan> loansDueTomorrow = loanDAO.getLoansDueWithinDate(tomorrow);
        System.out.println(loansDueTomorrow.size());

         List<String> emails = new ArrayList<>();
         loansDueTomorrow.forEach(loan -> {
             Customer customer= customerDAO.getById(loan.getCustomer_id());
           emails.add(customer.getEmail());
             System.out.println(customer.getEmail());

         } );
         emails.forEach(to -> {
            emailSender.send(to,  "Snart går lånetiden ut på följande böcker:Return material on loan before the period expires:");
             System.out.println("email send successfully!");

         });



    }




}
