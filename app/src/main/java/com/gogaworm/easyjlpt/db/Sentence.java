package com.gogaworm.easyjlpt.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Sentence extends StudyItem {

    @ColumnInfo
    public int wordId;

    @ColumnInfo
    public String japanese;

    @ColumnInfo
    public String translation;


    public Sentence(int lessonId, String japanese, String translation) {
        this.lessonId = lessonId;
        this.japanese = japanese;
        this.translation = translation;
    }

    public Sentence() {
    }
}
