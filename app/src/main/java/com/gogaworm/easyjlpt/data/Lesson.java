package com.gogaworm.easyjlpt.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class Lesson implements Parcelable {
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

    protected Lesson(Parcel in) {
        id = in.readInt();
        title = in.readParcelable(Word.class.getClassLoader());
        trainId = in.readInt();
        examId = in.readInt();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(title, flags);
        dest.writeInt(trainId);
        dest.writeInt(examId);
    }
}
