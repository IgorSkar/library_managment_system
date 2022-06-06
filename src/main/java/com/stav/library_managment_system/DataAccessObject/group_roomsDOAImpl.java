package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.group_roomsDAO;
import com.stav.library_managment_system.Models.group_rooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class group_roomsDOAImpl implements group_roomsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<group_rooms> getAllGroupRooms() {
        List<group_rooms> l = jdbcTemplate.query("SELECT * FROM group_rooms",new BeanPropertyRowMapper<group_rooms>(group_rooms.class));
        return l;
    }

    @Override
    public group_rooms getGroupRoomsById(int room_id) throws DataAccessException {
        group_rooms group_rooms = jdbcTemplate.queryForObject("SELECT * FROM group_rooms WHERE room_id=?", new BeanPropertyRowMapper<group_rooms>(group_rooms.class), room_id);
        return group_rooms;
    }

    @Override
    public int save(group_rooms group_rooms) {
        return jdbcTemplate.update("INSERT INTO group_rooms (name,library_id,description) VALUES (?,?,?)", new Object[] {group_rooms.getName(),group_rooms.getLibrary_id(),group_rooms.getDescription()});
    }

    @Override
    public int deleteGroupRoomsById(int room_id) {
        return jdbcTemplate.update("DELETE FROM group_rooms WHERE room_id=?",room_id);
    }
}
