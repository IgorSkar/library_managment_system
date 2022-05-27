package com.stav.library_managment_system;
import com.stav.library_managment_system.Email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class LibraryManagmentSystemApplication {
      @Autowired
      private EmailSenderService senderService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LibraryManagmentSystemApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void  sendMail(){
        senderService.sendSimpleEmail("spring.email.from@gmail.com",
              "This is body of email!" ,
                "Subject");
    }
}