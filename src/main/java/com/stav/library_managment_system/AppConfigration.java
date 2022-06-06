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
import java.util.*;

@Configuration
@EnableScheduling
public class AppConfigration {
    @Autowired
    private LoanDAO loanDAO;
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private BookDAO bookDAO;

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

        HashMap<Customer, String> toSend = new HashMap<>();

          // hämta alla låntagare
         List<Customer> customers = new ArrayList<>();
         loansDueTomorrow.forEach(loan -> {
             Customer customer = customerDAO.getById(loan.getCustomer_id());
           customers.add(customer);
           toSend.put(customer, "Hej " + customer.getFirst_name() + " " + customer.getLast_name() + "! Hoppas allt är bra med dig. " +
                   "Vi på Stav Biblioteket tar hand om våra låntagare och vill därför skicka en påminnelse till dig att snart är din låneperiod för " + bookDAO.getBookById(loan.getBook_id()).getString("title") + " med ISBN "+ bookDAO.getBookById(loan.getBook_id()).getString("isbn") + //bokens titel och kanske isbn
                   " som du lånaden den "+ loan.getLoan_date() + " börjar gå mot sitt slut. Lämna gärna tillbaka boken snarast, din låneperiod slutar den " + loan.getReturn_date() + //return_date
                   ". Vi är tacksamma att ni valde att låna böcker hos oss på Stav Bibliotek. Ni är alltid välkommna tillbaka!");
             System.out.println(customer.getEmail());
             System.out.println(loan.getReturn_date());
         });

        // skicka email dessa email
         toSend.forEach((customer, s) ->{
             emailSender.send(customer, s);
         });
    }

}
