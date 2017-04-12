package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Word;
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

    public JlptDataLoader(Context context, String folder) {
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

    protected Word parseWord(JSONObject json) throws JSONException {
        return new Word(
                json.getString("japanese"),
                json.getString("reading"),
                json.getString("translation")
        );
    }
}
