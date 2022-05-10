package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.GenreDAO;
import com.stav.library_managment_system.Models.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class GenreDAOImpl implements GenreDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * from genre",new BeanPropertyRowMapper<Genre>(Genre.class));
    }

    @Override
    public Genre getById(int genreId)   {
        Genre genre = jdbcTemplate.queryForObject("SELECT * FROM genre WHERE genre_id=?",new BeanPropertyRowMapper<Genre>(Genre.class),genreId);
        return genre;
    }

    @Override
    public int createGenre(Genre genre) {
       return  jdbcTemplate.update("INSERT INTO genre (name) VALUES (?)", new Object[] {genre});

    }
    @Override
    public int deleteGenre(int genreId) {
         return jdbcTemplate.update("DELETE genre WHERE genre_id=?",genreId);

    }
}
