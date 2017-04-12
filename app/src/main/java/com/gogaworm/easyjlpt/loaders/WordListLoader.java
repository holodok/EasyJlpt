package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public class WordListLoader extends JlptDataLoader<Word> {
    private int lessonId;

    public WordListLoader(Context context, String folder, int lessonId) {
        super(context, folder);
        this.lessonId = lessonId;
    }

    @Override
    protected void syncWithDataBase(List<Word> results) {

    }

    @Override
    protected String[] getFiles(String folder) {
        return new String[] { folder + "/lesson_" + lessonId };
    }

    @Override
    protected List<Word> createEmptyList() {
        return new LinkedList<>();
    }

    @Override
    protected void parseJson(List<Word> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonWords = root.getJSONArray("words");
        for (int i = 0; i < jsonWords.length(); i++) {
            JSONObject jsonWord = jsonWords.getJSONObject(i);
            results.add(new Word(
                    jsonWord.getString("japanese"),
                    jsonWord.getString("reading"),
                    jsonWord.getString("translation")));
        }
    }
}
