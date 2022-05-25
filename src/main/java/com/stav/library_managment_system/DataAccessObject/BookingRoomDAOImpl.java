package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.BookingRoomDAO;
import com.stav.library_managment_system.Models.BookingRoom;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jmx.export.metadata.ManagedOperation;
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
    public List<JSONObject> get_customers_with_group_rooms() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_customers_with_group_rooms")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject o = new JSONObject();
                    o.put("FirstName", rs.getString("FirstName"));
                    o.put("LastName", rs.getString("LastName"));
                    o.put("Time", rs.getString("Time"));
                    o.put("Booking", rs.getString("Booking"));
                    o.put("Room", rs.getString("Room"));
                    o.put("Library", rs.getString("Library"));
                    return o;
                });
        Map m = jdbcCall.execute(new HashMap<String, String>(0));
        return (List<JSONObject>) m.get("return");
    }


    @Override
    public boolean createBooking(int time_id, int customer_id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_customers_with_group_rooms");
        Map<String, Integer> inParams = new HashMap<>();
        inParams.put("time_id", time_id);
        inParams.put("customer_id", customer_id);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (int) jdbcCall.execute(in).get("succeed") >= 1;
    }


    /*
    @Override
    public List<BookingRoom> get_available_group_rooms() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_available_group_rooms")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject o = new JSONObject();
                    o.put("Room", rs.getString("Room"));
                    o.put("Time", rs.getString("Time"));
                    o.put("Date", rs.getString("Date"));
                    o.put("Library", rs.getString("Library"));
                    return o;
                });
        Map m = jdbcCall.execute(new HashMap<String, Object>(0));
        return (List<BookingRoom>) m.get("return");
    }
    */

    @Override
    public List<BookingRoom> get_available_group_rooms() {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_available_group_rooms")
                .returningResultSet("return", (rs, rn) -> {
                    JSONObject o = new JSONObject();
                    o.put("room_id", rs.getInt("room_id"));
                    o.put("name", rs.getString("name"));
                    o.put("library_id", rs.getInt("library_id"));
                    return o;
                });
        Map m = jdbcCall.execute(new HashMap<String, Object>(0));

        System.out.println(m);

        return (List<BookingRoom>) m.get("return");
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
