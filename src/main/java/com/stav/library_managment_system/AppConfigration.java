package com.stav.library_managment_system;
import com.stav.library_managment_system.DAO.*;
import com.stav.library_managment_system.Email.EmailSender;
import com.stav.library_managment_system.Models.*;
import org.json.JSONObject;
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
public class AppConfigration {
    @Autowired
    private LoanDAO loanDAO;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private EmailSender emailSender;

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

          // hämta alla låntagare
         List<Customer> customers = new ArrayList<>();
         loansDueTomorrow.forEach(loan -> {
             Customer customer = customerDAO.getById(loan.getCustomer_id());
           customers.add(customer);
             System.out.println(customer.getEmail());
             System.out.println(loan.getReturn_date());
         });

        // skicka email dessa email
         customers.forEach(to->{
            emailSender.send(to);
             System.out.println("email send successfully!");

         });



    }

}
