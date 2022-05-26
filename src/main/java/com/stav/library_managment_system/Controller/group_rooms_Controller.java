package com.stav.library_managment_system.Controller;
import com.stav.library_managment_system.DAO.group_roomsDAO;
import com.stav.library_managment_system.Models.group_rooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class group_rooms_Controller {
    @Autowired
    private group_roomsDAO group_roomsDAO;

    @GetMapping("/all")
    public List<group_rooms> getAllLocals(){
        return group_roomsDAO.getAllGroupRooms();
    }

    @GetMapping("/{room_id}")
    public ResponseEntity<?> getById(@PathVariable int room_id){

        group_rooms group_rooms = null;
        try {
          group_rooms=  group_roomsDAO.getGroupRoomsById(room_id);
        }catch (DataAccessException e){
            e.printStackTrace();
            return  new ResponseEntity<String>("oops Id not found", HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<group_rooms>(group_rooms,HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<?> createGroupRooms(@RequestBody group_rooms group_rooms){
        group_roomsDAO.save(group_rooms);
        return new ResponseEntity<String>("added successfully!", HttpStatus.OK);
    }


     @DeleteMapping("/{room_id}")
    public  ResponseEntity<?> deleteGroup_roomsById(@PathVariable int room_id){
        group_roomsDAO.deleteGroupRoomsById(room_id);
       return new ResponseEntity<String>("Local deleted successfully!", HttpStatus.OK);
            }
}
