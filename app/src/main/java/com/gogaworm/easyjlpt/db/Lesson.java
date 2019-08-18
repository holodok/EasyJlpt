package com.gogaworm.easyjlpt.db;

import androidx.room.*;

@Entity
public class Lesson {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ForeignKey(entity = Course.class, parentColumns = "id", childColumns = "courseId")
    public int courseId;

    @ColumnInfo
    public String code;

    @ColumnInfo
    public String title;

    @ColumnInfo
    public long lastStudiedTime;

    @ColumnInfo
    public int progress;

    public enum LESSON_TYPE {
        VIEW, LEARN_WORDS, LEARN_KANJI, LEARN_KANJI_WORDS, WORD_EXAM, KANJI_EXAM, REVIEW_WORDS
    }

    @Ignore
    public LESSON_TYPE lessonType; //todo: remove from here

    public Lesson(int courseId, String title, String code) {
        this.courseId = courseId;
        this.title = title;
        this.code = code;
    }
}
