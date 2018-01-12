package com.gogaworm.easyjlpt.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "word_learn_data")
public class WordLearnDataEntity {
    @PrimaryKey
    private int id;
    private int progress; // in percents
    private Date learnedOn;
    private int learnDelta; // used for calculation next learn date

    public void setId(int id) {
        this.id = id;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setLearnedOn(Date learnedOn) {
        this.learnedOn = learnedOn;
    }

    public void setLearnDelta(int learnDelta) {
        this.learnDelta = learnDelta;
    }

    public int getId() {
        return id;
    }

    public int getProgress() {
        return progress;
    }

    public Date getLearnedOn() {
        return learnedOn;
    }

    public int getLearnDelta() {
        return learnDelta;
    }
}
