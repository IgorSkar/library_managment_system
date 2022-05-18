package com.stav.library_managment_system.DAO;

import com.stav.library_managment_system.Models.BookingRoom;

import java.util.List;

public interface BookingRoomDAO {

    List<BookingRoom> getAllGroupRooms();

    List<BookingRoom> getAllBookningForCustomer(int customer_id);


    int create(BookingRoom bookingRoom);


    BookingRoom getGroupRoomByCustomerId(int customer_id);

    void delete(int customer_id, int room_id);
}
