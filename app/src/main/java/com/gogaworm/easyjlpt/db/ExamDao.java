package com.gogaworm.easyjlpt.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExamDao {
    @Insert
    long insert(Exam exam);

    @Query("select * from Exam where lessonId=:lessonId")
    List<Exam> getExamForLesson(int lessonId);
}
