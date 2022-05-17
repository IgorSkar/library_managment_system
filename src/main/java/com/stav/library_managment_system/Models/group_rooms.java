package com.stav.library_managment_system.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class group_rooms {
    private int room_id;
    private  int library_id;
    private String name;
}
