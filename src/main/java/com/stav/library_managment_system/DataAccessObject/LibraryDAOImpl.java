package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.LibraryDAO;
import com.stav.library_managment_system.Models.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @Override
    public Library getLibraryById(int libraryId)  throws DataAccessException {
        Library l = jdbcTemplate.queryForObject("SELECT * FROM libraries WHERE library_id=?",new BeanPropertyRowMapper<Library>(Library.class),libraryId);
        return l;
    }

    @Override
    public int save(Library library) {
        return jdbcTemplate.update("INSERT INTO libraries (name,adress,county) VALUES (?,?,?)",new Object[]{library.getName(),library.getAddress(),library.getCounty()});
    }

    @Override
    public void delete(int libraryId) {
        jdbcTemplate.update("DELETE FROM libraries WHERE library_id=?",libraryId);
    }

}
