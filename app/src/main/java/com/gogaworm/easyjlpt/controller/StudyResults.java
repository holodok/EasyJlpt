package com.gogaworm.easyjlpt.controller;

public class StudyResults {
    private int progress;
    private int wordCount;
    private long studyTime;

    public StudyResults(int progress, int wordCount, long studyTime) {
        this.progress = progress;
        this.wordCount = wordCount;
        this.studyTime = studyTime;
    }

    public int getProgress() {
        return progress;
    }

    public int getWordCount() {
        return wordCount;
    }

    public long getStudyTime() {
        return studyTime;
    }
}
