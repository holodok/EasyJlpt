package com.gogaworm.easyjlpt.data;

/**
 * Created on 18.08.2017.
 *
 * @author ikarpova
 */
public class Exam {
    public int id;
    public String japanese;
    public String reading;
    public String translation;

    public Word[] answers;
    public int correct;

    public Exam(int id, String japanese, String reading, String translation, Word[] answers, int correct) {
        this.id = id;
        this.japanese = japanese;
        this.reading = reading;
        this.translation = translation;
        this.answers = answers;
        this.correct = correct;
    }
}
