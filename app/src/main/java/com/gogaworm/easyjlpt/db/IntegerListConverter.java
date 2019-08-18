package com.gogaworm.easyjlpt.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class IntegerListConverter {
    @TypeConverter
    public String fromIntegerList(List<Integer> list) {
        String result = "";
        for (Integer value : list) {
            result = result.concat(Integer.toString(value)).concat(",");
        }
        return result;
    }

    @TypeConverter
    public List<Integer> toStringList(String data) {
        String[] values = data.split(",");
        List<Integer> results = new ArrayList<>(values.length);
        for (String value : values) {
            results.add(Integer.parseInt(value));
        }
        return results;
    }
}
