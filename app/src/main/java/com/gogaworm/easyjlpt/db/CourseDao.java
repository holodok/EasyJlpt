package com.gogaworm.easyjlpt.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    long insert(Course course);

    @Query("SELECT * from Course ORDER BY id")
    LiveData<List<Course>> getCourses();
}
