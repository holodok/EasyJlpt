package com.gogaworm.easyjlpt.data;

/**
 * Created on 18.08.2017.
 *
 * @author ikarpova
 */
public class Exam {
    public final int id;
    public final String japanese;
    public final String reading;
    public final String translation;

    public final Word[] answers;
    public final int correct;
    public final int[] corrects;

    public Exam(int id, String japanese, String reading, String translation, Word[] answers, int correct, int[] corrects) {
        this.id = id;
        this.japanese = japanese.replace("_", "＿");
        this.reading = reading;
        this.translation = translation;
        this.answers = answers;
        this.correct = correct;
        this.corrects = corrects;
    }

    public boolean isMultiAnswer() {
        return corrects != null && corrects.length > 1;
    }

    public boolean isLastAnswer(int index) {
        return corrects == null || corrects.length - 1 == index;
    }

    public Word getCorrectAnswer(int answerIndex) {
        return corrects == null ? answers[correct - 1] : answers[corrects[answerIndex] - 1];
    }

    public boolean isAnswerCorrect(int answerIndex, int answer) {
        return corrects == null && (correct == answer) || corrects != null && (corrects[answerIndex] == answer);
    }
}
