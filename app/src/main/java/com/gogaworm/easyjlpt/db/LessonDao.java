package com.gogaworm.easyjlpt.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LessonDao {
    @Insert
    long insert(Lesson lesson);

    @Query("SELECT * from Lesson where courseId=:courseId")
    LiveData<List<Lesson>> getLessonsForCourse(int courseId);

    @Query("SELECT * from Lesson where code=:code")
    Lesson getLessonByCode(String code);

    @Update
    void update(Lesson dbLesson);
}
