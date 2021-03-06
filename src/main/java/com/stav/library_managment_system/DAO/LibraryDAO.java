package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.Library;

import java.util.List;

public interface LibraryDAO {


    List<Library> getLibraries();

    Library getLibraryById(int libraryId);

    int save (Library library);

    void delete(int libraryId);

}
