package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.BookingRoomDAO;
import com.stav.library_managment_system.Models.BookingRoom;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking_room")
public class BookingRoomController {

    @Autowired
    private BookingRoomDAO bookingRoomDAO;

    @GetMapping("/all")
    public List<BookingRoom> getAllGroup_rooms(){
        return bookingRoomDAO.getAllGroupRooms();
    }
/*
    @GetMapping("/{customer_id}")
    public ResponseEntity<?> getGroupRoomsByCustomerId(@PathVariable int customer_id) {
        BookingRoom bookingRoom = null;
        try {
            bookingRoom = bookingRoomDAO.getGroupRoomByCustomerId(customer_id);
        }catch (DataAccessException e){
            e.printStackTrace();
            return new ResponseEntity<String>("oops: customer id not found in the database", HttpStatus.BAD_REQUEST);
        } return  new ResponseEntity<BookingRoom>(bookingRoom,HttpStatus.OK);
    }

 */

       @GetMapping("/{customer_id}")
       public List<BookingRoom> getAllBookingByCustomerId(@PathVariable int customer_id){
        return  bookingRoomDAO.getAllBookningForCustomer(customer_id);
       }



    @PostMapping("/{time_id}/{customer_id}")
    public boolean createBookingRooms(@PathVariable int time_id, @PathVariable int customer_id) {
         return bookingRoomDAO.createBooking(time_id, customer_id);
    }

     @GetMapping("/getBookings")
    public String get_customers_with_group_rooms(){
        return bookingRoomDAO.get_customers_with_group_rooms().toString();
    }

    @GetMapping("/availableGroupRooms")
    public String get_available_group_rooms(){
           return bookingRoomDAO.get_available_group_rooms().toString();
    }

    @DeleteMapping("/{customer_id}/{room_id}")
    public ResponseEntity<?> deleteBookingRoomByCustomerIdAndRoomId(@PathVariable int customer_id, @PathVariable int room_id){
        bookingRoomDAO.delete(customer_id,room_id);
        return  new ResponseEntity<String>("Booking  deleted successfully!",HttpStatus.OK);
    }
}
