package com.gogaworm.easyjlpt.db;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class StringListConverter {
    @TypeConverter
    public String fromStringList(List<String> list) {
        String result = "";
        for (String value : list) {
            result = result.concat(value).concat("@");
        }
        return result;
    }

    @TypeConverter
    public List<String> toStringList(String data) {
        return Arrays.asList(data.split("@"));
    }
}
