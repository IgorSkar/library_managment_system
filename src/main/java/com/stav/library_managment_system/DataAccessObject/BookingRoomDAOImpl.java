package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookingRoomDAO;
import com.stav.library_managment_system.Models.BookingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.in;

@Repository
public class BookingRoomDAOImpl implements  BookingRoomDAO {

    @Autowired
     private JdbcTemplate jdbcTemplate;


    @Override
    public List<BookingRoom> getAllGroupRooms() {
        return jdbcTemplate.query("SELECT * FROM customers_with_group_rooms", new BeanPropertyRowMapper<BookingRoom>(BookingRoom.class));

    }

    @Override
    public List<BookingRoom> getAllBookningForCustomer(int customer_id) {
        return  jdbcTemplate.query("SELECT * FROM customers_with_group_rooms WHERE customer_id=? ", new BeanPropertyRowMapper<BookingRoom>(BookingRoom.class), customer_id);
    }

    @Override
    public boolean create_customers_with_group_rooms(int room_id, int customer_id, String time) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_customers_with_group_rooms");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("room_id", String.valueOf(room_id));
        inParams.put("customer_id", String.valueOf(customer_id));
        inParams.put("time",String.valueOf(time));
        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (int) jdbcCall.execute(in).get("succeed") >= 1;

    }


    @Override
    public int create(BookingRoom bookingRoom) {
        return jdbcTemplate.update("INSERT INTO customers_with_group_rooms (room_id,customer_id ,time)VALUES (?,?;?)",new Object[]{bookingRoom.getRoom_id(),bookingRoom.getCustomer_id(),bookingRoom.getTime()});
    }

    @Override
    public BookingRoom getGroupRoomByCustomerId(int customer_id) throws DataAccessException {
        BookingRoom bookingRoom = jdbcTemplate.queryForObject("SELECT * FROM customers_with_group_rooms WHERE customer_id=?",new BeanPropertyRowMapper<BookingRoom>(BookingRoom.class),customer_id);
        return bookingRoom;
    }

    @Override
    public void delete(int customer_id, int room_id) {
        jdbcTemplate.update("DELETE FROM customers_with_group_rooms WHERE customer_id=? AND room_id=?", customer_id,room_id);

    }
}
