package com.gogaworm.easyjlpt.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class UserSession implements Parcelable {
    public JlptSection section;
    public JlptLevel level;

    public UserSession(JlptSection section, JlptLevel level) {
        this.section = section;
        this.level = level;
    }

    public String getFolder() {
        return section.name().toLowerCase() + '_' + level.name();
    }

    private UserSession(Parcel in) {
        section = JlptSection.valueOf(in.readString());
        level = JlptLevel.valueOf(in.readString());
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
        dest.writeString(section.toString());
        dest.writeString(level.toString());
    }
}
