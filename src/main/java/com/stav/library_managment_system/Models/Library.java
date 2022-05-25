package com.stav.library_managment_system.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {

    @JsonProperty("library_id")
    private int libraryId;
    @JsonProperty("name")
    private String name;
    private String address;
    private String county;

}
