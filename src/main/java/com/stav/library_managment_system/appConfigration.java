package com.stav.library_managment_system;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class appConfigration {

    @Scheduled(fixedDelay = 1440)
    public void sendSimpleEmail(){
        System.out.println("Jag är din sharmuto");
    }
}
