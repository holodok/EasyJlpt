package com.gogaworm.easyjlpt.loader;

import java.io.File;

public class LessonFile {
    enum LessonType { LESSON, EXAM}

    public final String level;
    public final String section;
    public final String course;
    LessonType lessonType;
    public final File lessonFile;

    public LessonFile(String level, String section, String course, LessonType lessonType, File lessonFile) {
        this.level = level;
        this.section = section;
        this.course = course;
        this.lessonType = lessonType;
        this.lessonFile = lessonFile;
    }

    public static LessonFile newLesson(String level, String section, String course, File lessonFile) {
        return new LessonFile(level, section, course, LessonType.LESSON, lessonFile);
    }

    public static LessonFile newExam(String level, String section, String course, File lessonFile) {
        return new LessonFile(level, section, course, LessonType.EXAM, lessonFile);
    }
}
