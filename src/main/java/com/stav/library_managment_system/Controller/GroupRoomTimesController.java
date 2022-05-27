package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.GroupRoomTimesDAO;
import com.stav.library_managment_system.DataAccessObject.GroupRoomTimesDAOIMPL;
import com.stav.library_managment_system.Models.GroupRoomTimes;
import com.stav.library_managment_system.Models.group_rooms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groupRoomTimes")
public class GroupRoomTimesController {
    @Autowired
    GroupRoomTimesDAO groupRoomTimesDAO;

    @GetMapping("/getTimes")
    public List<GroupRoomTimes> groupRoomTimes(){
        return groupRoomTimesDAO.groupRoomTimes();
    }
    //Does not work, will fix later
    @PostMapping()
    public ResponseEntity<?> create(GroupRoomTimes groupRoomTimes){
        int result = groupRoomTimesDAO.createGroupRoomTimes(groupRoomTimes);
        if (result == -1){
            return  new ResponseEntity<String>("oops could not add", HttpStatus.BAD_REQUEST);
        }
        return  new ResponseEntity<String>("added group_rooms", HttpStatus.OK);

    }
}
