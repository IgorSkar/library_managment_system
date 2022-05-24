package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRoomTime {
    private int time_id;
    private int room_id;
    private String time;
    private String date;
}
