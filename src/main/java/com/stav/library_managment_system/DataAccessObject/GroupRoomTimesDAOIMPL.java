package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.GroupRoomTimesDAO;
import com.stav.library_managment_system.Models.GroupRoomTimes;
import com.stav.library_managment_system.Models.group_rooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupRoomTimesDAOIMPL implements GroupRoomTimesDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupRoomTimes> groupRoomTimes() {
        String result = jdbcTemplate.query("SELECT * FROM library_management_system.group_room_times;", new BeanPropertyRowMapper<GroupRoomTimes>(GroupRoomTimes.class)).toString();
        System.out.println("Result schoo: " + result); // We can conclude that 'time_id' doesn't appear... WTF??
        return jdbcTemplate.query("SELECT * FROM group_room_times", new BeanPropertyRowMapper<GroupRoomTimes>(GroupRoomTimes.class));

    }

    @Override
    public void create(GroupRoomTimes groupRoomTimes) {
        jdbcTemplate.update("INSERT INTO group_room_times(room_id,time,date) VALUES(?,?,?)", new Object[] {groupRoomTimes.getRoom_id(), groupRoomTimes.getTime(), groupRoomTimes.getDate()});
    }
}
