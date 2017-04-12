package com.gogaworm.easyjlpt.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class UserSession implements Parcelable {
    public enum Mode {
        VOCABULARY,
        KANJI,
        GRAMMAR
    }
    public enum Level {
        N5,
        N4,
        N3,
        N2,
        N1
    }

    public Mode mode;
    public Level level;

    public UserSession() {
    }

    public UserSession(Mode mode, Level level) {
        this.mode = mode;
        this.level = level;
    }

    public String getFolder() {
        return mode.name().toLowerCase() + '_' + level.name();
    }

    private UserSession(Parcel in) {
        mode = Mode.valueOf(in.readString());
        level = Level.valueOf(in.readString());
    }

    public static final Creator<UserSession> CREATOR = new Creator<UserSession>() {
        @Override
        public UserSession createFromParcel(Parcel in) {
            return new UserSession(in);
        }

        @Override
        public UserSession[] newArray(int size) {
            return new UserSession[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mode.toString());
        dest.writeString(level.toString());
    }
}
