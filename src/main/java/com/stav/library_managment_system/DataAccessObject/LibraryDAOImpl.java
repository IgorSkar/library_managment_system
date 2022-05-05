package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.Models.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LibraryDAOImpl implements  LibraryDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Library findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM library WHERE name=?",new BeanPropertyRowMapper<Library>(Library.class), name);
    }

    @Override
    public int deleteById(int library_id) {
        return  jdbcTemplate.update("DELEETE FROM library  WHERE  library_id=?", library_id);
    }

    @Override
    public int update(Library library, int library_id) {
        return jdbcTemplate.update("INSERT INTO library (name,adress,county) VALUES (?,?,?)",new Object[] {library.getName(), library.getAddress(), library.getCounty()});
    }
}
