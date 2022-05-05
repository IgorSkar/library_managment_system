package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DataAccessObject.LibraryDAO;
import com.stav.library_managment_system.Models.Library;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
public class LibraryController {
    private LibraryDAO eDAO;

    @GetMapping("/{name}")
     public Library findByName( @PathVariable String name) {
        return eDAO.findByName(name);
    }

        @DeleteMapping("/{library_id}")
         public String deleteById(@PathVariable int library_id){
        return eDAO.deleteById(library_id) +"Library(l) delete from the database";


    }
   /*@PutMapping("/{library_id}")
    public int update(@ResponseBody Library library,@PathVariable int library_id){
       return eDAO.update(library,library_id);
    }


    */


}
