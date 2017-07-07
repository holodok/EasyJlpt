package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Grammar;
import org.json.JSONException;

import java.util.List;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class GrammarLoader extends JlptDataLoader<Grammar> {
    public GrammarLoader(Context context, String folder) {
        super(context, folder);
    }

    @Override
    protected void syncWithDataBase(List<Grammar> results) {

    }

    @Override
    protected String[] getFiles(String folder) {
        return new String[0];
    }

    @Override
    protected List<Grammar> createEmptyList() {
        return null;
    }

    @Override
    protected void parseJson(List<Grammar> results, String json) throws JSONException {

    }
}
