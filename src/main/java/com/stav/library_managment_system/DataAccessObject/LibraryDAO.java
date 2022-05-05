package com.stav.library_managment_system.DataAccessObject;


import com.stav.library_managment_system.Models.Library;

public interface LibraryDAO {


   public Library findByName(String name );


    public int deleteById(int library_id);

    public int update(Library library, int library_id);
}
