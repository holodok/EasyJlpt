package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
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
public class WordListLoader extends LessonLoader<Word> {

    public WordListLoader(Context context, JlptSection section, JlptLevel jlptLevel, int lessonId) {
        super(context, section, jlptLevel, lessonId);
    }

    @Override
    protected List<Word> createEmptyList() {
        return new LinkedList<>();
    }

    @Override
    protected void parseJson(List<Word> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonWords = root.optJSONArray("words");
        if (jsonWords != null) {
            parseWords(jsonWords, results);
        }
    }

    void parseWords(JSONArray jsonWords, List<Word> results) throws JSONException {
        for (int i = 0; i < jsonWords.length(); i++) {
            results.add(parseWord(jsonWords.getJSONObject(i)));
        }
    }
}
