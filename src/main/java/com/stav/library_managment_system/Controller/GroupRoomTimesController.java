package com.stav.library_managment_system.Controller;

import com.stav.library_managment_system.DAO.GroupRoomTimesDAO;
import com.stav.library_managment_system.Models.GroupRoomTime;
import com.stav.library_managment_system.Models.GroupRoomTime;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group_room_times")
public class GroupRoomTimesController {
    @Autowired
    GroupRoomTimesDAO groupRoomTimesDAO;

    @GetMapping("/get_times")
    public List<GroupRoomTime> groupRoomTimes(){
        return groupRoomTimesDAO.groupRoomTimes();
    }

    @GetMapping("available_times/{id}")
    public List<GroupRoomTime> getAvailableTimesById(@PathVariable("id") int roomId){
        return groupRoomTimesDAO.getAvailableTimesById(roomId);
    }

    @GetMapping("book")
    public boolean book(@RequestParam("timeId") int timeId, @RequestParam("customerId") int customerId){
        return groupRoomTimesDAO.book(timeId, customerId);
    }

    //Does not work, will fix later
    @PostMapping()
    public void create(GroupRoomTime groupRoomTime){
        groupRoomTimesDAO.create(groupRoomTime);
    }

    @GetMapping("/get_times_by_id/{customerId}")
    public String groupRoomTimesById(@PathVariable int customerId) {
        return groupRoomTimesDAO.groupRoomTimesById(customerId).toString();
    }

    @GetMapping("/unbook")
    public boolean unbook(@RequestParam("timeId") int timeId, @RequestParam("customerId") int customerId){
        return groupRoomTimesDAO.unbook(timeId, customerId);
    }
}
