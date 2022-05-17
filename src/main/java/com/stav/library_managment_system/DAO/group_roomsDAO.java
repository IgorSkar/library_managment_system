package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.group_rooms;

import java.util.List;

public interface group_roomsDAO {


    List<group_rooms> getAllGroupRooms();


    group_rooms getGroupRoomsById(int room_id);


    int save(group_rooms group_rooms);

    int  deleteGroupRoomsById (int room_id);

}
