package com.gogaworm.easyjlpt.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Course {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String title;

    @ColumnInfo
    public String level;

    @ColumnInfo
    public String section;

    public Course(String title, String level, String section) {
        this.title = title;
        this.level = level;
        this.section = section;
    }
}
