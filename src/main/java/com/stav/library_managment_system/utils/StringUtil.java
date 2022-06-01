package com.stav.library_managment_system.utils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    public static String[] convertJSONArrayToStringArray(JSONArray array){
        List<String> output = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            output.add(array.getString(i));
            System.out.println(array.getString(i));
            System.out.println(array);
        }
        return output.toArray(new String[output.size()]);
    }

}
