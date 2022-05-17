package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.LibraryDAO;
import com.stav.library_managment_system.Models.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibraryDAOImpl implements LibraryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Library> getLibraries(){
        return jdbcTemplate.query("SELECT * FROM libraries", new BeanPropertyRowMapper<>(Library.class));
    }

}
