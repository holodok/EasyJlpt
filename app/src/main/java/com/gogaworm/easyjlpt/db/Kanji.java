package com.gogaworm.easyjlpt.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Kanji extends StudyItem {
    @ColumnInfo
    public String kanji;

    @ColumnInfo
    public String onReading;

    @ColumnInfo
    public String kunReading;

    @ColumnInfo
    public String translation;

    @Ignore
    public List<Expression> expressions = new ArrayList<>();

    public Kanji(int lessonId, String code, String kanji, String onReading, String kunReading, String translation) {
        this.lessonId = lessonId;
        this.code = code;
        this.kanji = kanji;
        this.onReading = normalizeSpaces(onReading);
        this.kunReading = normalizeSpaces(kunReading);
        this.translation = translation;
    }

    public String getOnReading() {
        return hasOnReading() ? onReading : "";
    }

    public String getKunReading() {
        return hasKunReading() ? kunReading : "";
    }

    public boolean hasOnReading() {
        return onReading != null && onReading.length() > 0;
    }

    public boolean hasKunReading() {
        return kunReading != null && kunReading.length() > 0;
    }

    String normalizeSpaces(String text) {
        return text == null ? null : text.replaceAll("　", " ");
    }
}
