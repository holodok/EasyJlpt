package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public abstract class JlptDataLoader<T> extends AbstractJsonDataLoader<T> {
    private String folder;

    JlptDataLoader(Context context, String folder) {
        super(context);
        this.folder = folder;
    }

    @Override
    public List<T> loadInBackground() {
        List<T> results = super.loadInBackground();
        syncWithDataBase(results); //TODO: pass database reference
        return results;
    }

    protected abstract void syncWithDataBase(List<T> results);

    @Override
    protected String[] getFiles() {
        return getFiles(folder);
    }

    protected abstract String[] getFiles(String folder);

    static Word parseWord(JSONObject json) throws JSONException {
        return new Word(
                json.getString("japanese"),
                json.optString("reading"),
                json.getString("translation")
        );
    }

    static String[] parseStrings(JSONArray array) throws JSONException {
        String[] values = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            values[i] = array.getString(i);
        }
        return values;
    }
}
