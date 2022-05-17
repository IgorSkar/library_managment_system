package com.stav.library_managment_system;


import com.stav.library_managment_system.Controller.AuthorController;
import com.stav.library_managment_system.DataAccessObject.AuthorDAOIMLP;
import com.stav.library_managment_system.DataAccessObject.CustomerDAOImpl;
import com.stav.library_managment_system.Models.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LibraryManagmentSystemApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LibraryManagmentSystemApplication.class, args);
    }

}