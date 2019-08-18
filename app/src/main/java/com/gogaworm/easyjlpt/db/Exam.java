package com.gogaworm.easyjlpt.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class Exam extends StudyItem {
    @ColumnInfo
    public String japanese;

    @ColumnInfo
    public String translation;

    @TypeConverters(StringListConverter.class)
    public List<String> answers;

    @TypeConverters(IntegerListConverter.class)
    public List<Integer> correctIndexes;
}
