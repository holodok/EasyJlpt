package com.gogaworm.easyjlpt.data;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class Lesson {
    public int id;
    public Word title;
    public int trainId;
    public int examId;

    public Lesson(int id, Word title, int trainId, int examId) {
        this.id = id;
        this.title = title;
        this.trainId = trainId;
        this.examId = examId;
    }
}
