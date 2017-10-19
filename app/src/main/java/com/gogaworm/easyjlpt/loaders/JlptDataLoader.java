package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
abstract class JlptDataLoader<T> extends AbstractJsonDataLoader<T> {
    JlptSection section;
    JlptLevel level;

    JlptDataLoader(Context context, JlptSection section, JlptLevel jlptLevel) {
        super(context);
        this.section = section;
        this.level = jlptLevel;
    }

    static Word parseWord(JSONObject json) throws JSONException {
        return new Word(
                json.getString("japanese"),
                json.optString("reading"),
                json.optString("translation")
        );
    }

    static String[] parseStrings(JSONArray array) throws JSONException {
        String[] values = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            values[i] = array.getString(i);
        }
        return values;
    }

    static int[] parseIntegers(JSONArray array) throws JSONException {
        if (array == null) return null;

        int[] values = new int[array.length()];
        for (int i = 0; i < array.length(); i++) {
            values[i] = array.getInt(i);
        }
        return values;
    }
}
