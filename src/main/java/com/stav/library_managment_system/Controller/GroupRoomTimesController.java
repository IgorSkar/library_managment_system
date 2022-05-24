package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.GroupRoomTimesDAO;
import com.stav.library_managment_system.Models.GroupRoomTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groupRoomTimes")
public class GroupRoomTimesController {
    @Autowired
    GroupRoomTimesDAO groupRoomTimesDAO;

    @GetMapping("/getTimes")
    public List<GroupRoomTime> groupRoomTimes(){
        return groupRoomTimesDAO.groupRoomTimes();
    }

    //Does not work, will fix later
    @PostMapping()
    public void create(GroupRoomTime groupRoomTime){
        groupRoomTimesDAO.create(groupRoomTime);
    }

    @GetMapping("/get_times_by_id/{customerId}")
    public String groupRoomTimesById(@PathVariable int customerId){
        return groupRoomTimesDAO.groupRoomTimesById(customerId).toString();
    }
}
