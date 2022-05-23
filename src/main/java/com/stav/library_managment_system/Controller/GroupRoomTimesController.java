package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.GroupRoomTimesDAO;
import com.stav.library_managment_system.DataAccessObject.GroupRoomTimesDAOIMPL;
import com.stav.library_managment_system.Models.GroupRoomTimes;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void create(GroupRoomTimes groupRoomTimes){
        groupRoomTimesDAO.create(groupRoomTimes);
    }
}
