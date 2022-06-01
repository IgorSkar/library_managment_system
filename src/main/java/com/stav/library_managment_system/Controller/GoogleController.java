package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.google.GoogleAPI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/google")
public class GoogleController {

    @GetMapping("isbn/{isbn}")
    public String getBook(@PathVariable("isbn") String isbn){
        return GoogleAPI.inst().getBook(isbn).toString();
    }

}
