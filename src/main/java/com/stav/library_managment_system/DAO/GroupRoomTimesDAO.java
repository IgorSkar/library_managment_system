package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.GroupRoomTime;
import org.json.JSONObject;

import java.util.List;

public interface GroupRoomTimesDAO {

    List<GroupRoomTime> groupRoomTimes();

    void create(GroupRoomTime groupRoomTime);

    List<JSONObject> groupRoomTimesById(int customer_id);
}
