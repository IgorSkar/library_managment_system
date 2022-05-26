package com.stav.library_managment_system.DataAccessObject;

import com.stav.library_managment_system.DAO.GroupRoomTimesDAO;
import com.stav.library_managment_system.Models.GroupRoomTime;
import org.json.JSONObject;
import com.stav.library_managment_system.Models.GroupRoomTime;
import jdk.security.jarsigner.JarSigner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GroupRoomTimesDAOIMPL implements GroupRoomTimesDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupRoomTime> groupRoomTimes() {
        String result = jdbcTemplate.query("SELECT * FROM library_management_system.group_room_times;", new BeanPropertyRowMapper<GroupRoomTime>(GroupRoomTime.class)).toString();
        return jdbcTemplate.query("SELECT * FROM group_room_times", new BeanPropertyRowMapper<GroupRoomTime>(GroupRoomTime.class));

    }
    public List<GroupRoomTime> getAvailableTimesById(int roomId){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("get_available_group_room_times")
                .returningResultSet("return", new BeanPropertyRowMapper<>(GroupRoomTime.class));
        Map<String, String> inParams = new HashMap<>();
        inParams.put("room_id", roomId+"");
        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        return (ArrayList<GroupRoomTime>) m.get("return");
    }

    public boolean book(int timeId, int customerId) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("create_customers_with_group_rooms");
        Map<String, String> inParams = new HashMap<>();
        inParams.put("time_id", timeId + "");
        inParams.put("customer_id", customerId + "");
        SqlParameterSource in = new MapSqlParameterSource(inParams);
        Map m = jdbcCall.execute(in);
        return (int) m.get("succeed") >= 1;
    }
    @Override
    public void create(GroupRoomTime groupRoomTime) {
        jdbcTemplate.update("INSERT INTO group_room_times(room_id,time,date) VALUES(?,?,?)", new Object[] {groupRoomTime.getRoom_id(), groupRoomTime.getTime(), groupRoomTime.getDate()});
    }

    @Override
    public List<JSONObject> groupRoomTimesById(int customer_id) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_customer_room_bookings").returningResultSet("return", (rs,rn ) -> {
            JSONObject object = new JSONObject();
            object.put("time_id", rs.getInt("time_id"));
            object.put("room_id", rs.getInt("room_id"));
            object.put("time", rs.getString("time"));
            object.put("date", rs.getString("date"));
            object.put("name", rs.getString("name"));

            return object;
        });

        Map<String, Integer> inParams = new HashMap<>();
        inParams.put("customer_id_frontend", customer_id);

        SqlParameterSource in = new MapSqlParameterSource(inParams);
        return (ArrayList<JSONObject>) jdbcCall.execute(in).get("return"); // Let me be yellow
    }

    public boolean unbook(int timeId, int customerId){
        return jdbcTemplate.update("DELETE FROM customers_with_group_rooms WHERE time_id = ? AND customer_id = ?", timeId, customerId) >= 1;
    }

}
