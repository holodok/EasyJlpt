package com.gogaworm.easyjlpt.db;

import androidx.room.*;

@Entity
public class StudyItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ForeignKey(entity = Lesson.class, parentColumns = "id", childColumns = "lessonId")
    public int lessonId;

    @ColumnInfo
    public String code;

    @ColumnInfo
    public long lastStudiedTime;
}
