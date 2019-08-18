package com.gogaworm.easyjlpt.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Expression extends Word {
    /*@ForeignKey(entity = Word.class, parentColumns = "id", childColumns = "groupId")*/
    @ColumnInfo
    public int groupId;

    /**
     * 1 - word expression
     * 2 - kanji expression
     */
    @ColumnInfo
    public int type;

    @ColumnInfo
    public String japanese;

    @ColumnInfo
    public String reading;

    @ColumnInfo
    public String translation;

    @Ignore
    public List<Sentence> sentences = new ArrayList<>();

    @Ignore
    public static final int TYPE_WORD = 1;
    @Ignore
    public static final int TYPE_KANJI = 2;

    public Expression(int lessonId, String japanese, String reading, String translation, int type) {
        super(lessonId, japanese, reading, translation);
        this.type = type;
    }

    public Expression() {
    }
}
