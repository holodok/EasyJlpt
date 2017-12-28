package com.gogaworm.easyjlpt.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 18.08.2017.
 *
 * @author ikarpova
 */
public class Exam implements Parcelable {
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

    protected Exam(Parcel in) {
        id = in.readInt();
        japanese = in.readString();
        reading = in.readString();
        translation = in.readString();
        answers = in.createTypedArray(Word.CREATOR);
        correct = in.readInt();
        corrects = in.createIntArray();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(japanese);
        dest.writeString(reading);
        dest.writeString(translation);
        dest.writeTypedArray(answers, flags);
        dest.writeInt(correct);
        dest.writeIntArray(corrects);
    }

    public static final Creator<Exam> CREATOR = new Creator<Exam>() {
        @Override
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };
}
