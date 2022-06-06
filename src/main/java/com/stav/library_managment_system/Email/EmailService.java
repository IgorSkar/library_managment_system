package com.stav.library_managment_system.Email;


import com.stav.library_managment_system.DAO.LoanDAO;
import com.stav.library_managment_system.Models.Customer;
import com.stav.library_managment_system.AppConfigration;
import com.stav.library_managment_system.Models.Loan;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    @Autowired
    private Loan loan;
    @Autowired
    private LoanDAO loanDAO;

    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(Customer customer) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText("Hej " + customer.getFirst_name() + "! Hoppas allt är bra med dig," + loanDAO.getLoansByCustomerId(loan.getCustomer_id()) +
                    "vi på Stav Biblioteket ville meddela dig om att din låneperiod för " +
                    "börjar gå mot sitt slut. Lämna gärna tillbaka boken senast ",true);
            helper.setTo(customer.getEmail());
            helper.setSubject("Loan period is over!");
            helper.setFrom("librarystav.22@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
