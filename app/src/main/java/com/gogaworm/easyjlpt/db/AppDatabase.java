package com.gogaworm.easyjlpt.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class, Lesson.class, Word.class, Kanji.class, Expression.class, Sentence.class, Exam.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao getCourseDao();
    public abstract LessonDao getLessonDao();
    public abstract WordDao getWordDao();

    public abstract ExpressionDao getExpressionDao();

    public abstract KanjiDao getKanjiDao();

    public abstract SentenceDao getSentenceDao();

    public abstract ExamDao getExamDao();
}
