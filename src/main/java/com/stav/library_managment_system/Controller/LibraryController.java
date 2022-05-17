package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.LibraryDAO;
import com.stav.library_managment_system.Models.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/libraries")
public class LibraryController {

    @Autowired
    private LibraryDAO libraryDAO;

    @GetMapping
    public List<Library> getLibraries(){
        return libraryDAO.getLibraries();
    }

}
