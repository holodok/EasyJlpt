package com.gogaworm.easyjlpt.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    long insert(Word word);

    @Query("SELECT * from Word WHERE lessonId=:lessonId")
    LiveData<List<Word>> getWordsForLesson(int lessonId);

    @Query("SELECT * from Word WHERE lessonId=:lessonId")
    List<Word> getWordsForLessonRaw(int lessonId);

    @Transaction
    @Query("SELECT * from Word WHERE lessonId=:lessonId")
    LiveData<List<WordDescription>> getWordDescriptionsForLesson(int lessonId);
}
