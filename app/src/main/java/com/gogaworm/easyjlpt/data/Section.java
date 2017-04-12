package com.gogaworm.easyjlpt.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class Section implements Parcelable {
    public int id;
    public Word title;

    public int progress;

    public Section(int id, Word title) {
        this.id = id;
        this.title = title;
    }

    protected Section(Parcel in) {
        id = in.readInt();
        title = in.readParcelable(Word.class.getClassLoader());
        progress = in.readInt();
    }

    public static final Creator<Section> CREATOR = new Creator<Section>() {
        @Override
        public Section createFromParcel(Parcel in) {
            return new Section(in);
        }

        @Override
        public Section[] newArray(int size) {
            return new Section[size];
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
        dest.writeInt(progress);
    }
}
