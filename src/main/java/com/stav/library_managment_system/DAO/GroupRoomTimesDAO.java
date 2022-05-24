package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.GroupRoomTimes;

import java.util.List;

public interface GroupRoomTimesDAO {

    List<GroupRoomTimes> groupRoomTimes();

    void create(GroupRoomTimes groupRoomTimes);

}
