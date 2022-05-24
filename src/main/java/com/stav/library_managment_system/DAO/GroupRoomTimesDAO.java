package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.GroupRoomTime;
import org.json.JSONObject;
import com.stav.library_managment_system.Models.GroupRoomTime;

import java.util.List;

public interface GroupRoomTimesDAO {

    List<GroupRoomTime> groupRoomTimes();

    List<GroupRoomTime> getAvailableTimesById(int roomId);

    boolean book(int timeId, int customerId);

    void create(GroupRoomTime groupRoomTime);

    List<JSONObject> groupRoomTimesById(int customer_id);
}
