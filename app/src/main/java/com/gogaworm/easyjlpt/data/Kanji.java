package com.gogaworm.easyjlpt.data;

import android.os.Parcel;
import android.text.TextUtils;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 30.08.2017.
 *
 * @author ikarpova
 */
public class Kanji extends Word {
    public final String onReading;
    public final String kunReading;

    public Kanji(String japanese, String onReading, String kunReading, String translation) {
        super(japanese, getKanjiReading(onReading, kunReading), translation);
        this.onReading = onReading;
        this.kunReading = kunReading;
    }

    public boolean hasOnReading() {
        return !TextUtils.isEmpty(onReading);
    }

    public boolean hasKunReading() {
        return !TextUtils.isEmpty(kunReading);
    }

    protected Kanji(Parcel in) {
        super(in);
        onReading = in.readString();
        kunReading = in.readString();
    }

    private static String getKanjiReading(String onReading, String kunReading) {
        boolean hasOnReading = !isEmpty(onReading);
        boolean hasKunReading = !isEmpty(kunReading);
        return hasOnReading ? onReading + (hasKunReading ? " " + kunReading : "") : kunReading;
    }

    public static final Creator<Kanji> CREATOR = new Creator<Kanji>() {
        @Override
        public Kanji createFromParcel(Parcel in) {
            return new Kanji(in);
        }

        @Override
        public Kanji[] newArray(int size) {
            return new Kanji[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(onReading);
        dest.writeString(kunReading);
    }
}
