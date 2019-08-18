package com.gogaworm.easyjlpt.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.isEmpty;

@Entity
public class Word extends StudyItem {
    @ColumnInfo
    public String japanese;

    @ColumnInfo
    public String reading;

    @ColumnInfo
    public String translation;

    @Ignore
    public List<Expression> expressions = new ArrayList<>();

    @Ignore
    public List<Sentence> sentences = new ArrayList<>();

    public Word() {
    }

    public Word(int lessonId, String japanese, String reading, String translation) {
        this.lessonId = lessonId;
        this.japanese = japanese;
        this.reading = reading;
        this.translation = translation;
    }

    public boolean hasReading() {
        return !isEmpty(reading) && !japanese.equals(reading);
    }

    @Override
    public String toString() {
        return japanese;
    }
}
