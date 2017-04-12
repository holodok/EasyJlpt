package com.gogaworm.easyjlpt.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class Word implements Parcelable {
    public String japanese;
    public String reading;
    public String translation;

    public Word(String japanese, String reading, String translation) {
        this.japanese = japanese;
        this.reading = reading;
        this.translation = translation;
    }

    protected Word(Parcel in) {
        japanese = in.readString();
        reading = in.readString();
        translation = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(japanese);
        dest.writeString(reading);
        dest.writeString(translation);
    }
}
