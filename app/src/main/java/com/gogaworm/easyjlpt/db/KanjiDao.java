package com.gogaworm.easyjlpt.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KanjiDao {
    @Insert
    long insert(Kanji kanji);

    @Query("SELECT * from Kanji WHERE lessonId=:lessonId")
    LiveData<List<Kanji>> getKanjiForLesson(int lessonId);

    @Query("SELECT * from Kanji WHERE lessonId=:lessonId")
    List<Kanji> getKanjiForLessonRaw(int lessonId);

    @Query("SELECT * from Kanji WHERE code=:code")
    Kanji getKanjiByCode(String code);

    @Update
    void update(Kanji kanji);
}
