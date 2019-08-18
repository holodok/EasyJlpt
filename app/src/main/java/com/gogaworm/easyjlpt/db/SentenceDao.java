package com.gogaworm.easyjlpt.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SentenceDao {
    @Insert
    long insert(Sentence sentence);

    @Query("select * from Sentence where wordId=:wordId")
    List<Sentence> getSentencesForWord(int wordId);
}
