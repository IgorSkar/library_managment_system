package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.BookingRoom;
import org.json.JSONObject;

import java.util.List;

public interface BookingRoomDAO {

    List<BookingRoom> getAllGroupRooms();


    List<BookingRoom> getAllBookningForCustomer(int customer_id);


    List<JSONObject> get_customers_with_group_rooms();


    boolean createBooking(int time_id, int customer_id);

    List<BookingRoom> get_available_group_rooms();


    BookingRoom getGroupRoomByCustomerId(int customer_id);


    void delete(int customer_id, int room_id);
}
