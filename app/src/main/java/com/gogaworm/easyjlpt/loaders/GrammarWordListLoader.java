package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import static com.gogaworm.easyjlpt.loaders.GrammarLoader.parseMeanings;
import static com.gogaworm.easyjlpt.loaders.GrammarLoader.parseSentences;

/**
 * Created on 11.08.2017.
 *
 * @author ikarpova
 */
public class GrammarWordListLoader extends WordListLoader {
    public GrammarWordListLoader(Context context, JlptSection section, JlptLevel level, int lessonId) {
        super(context, section, level, lessonId);
    }

    @Override
    protected void parseJson(List<Word> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonGrammarList = root.optJSONArray("grammar");
        if (jsonGrammarList != null) {
            for (int i = 0; i < jsonGrammarList.length(); i++) {
                JSONObject jsonGrammar = jsonGrammarList.getJSONObject(i);
                Grammar.Meaning[] meanings = parseMeanings(jsonGrammar.getJSONArray("meanings"));
                for (Grammar.Meaning meaning : meanings) {
                    results.add(new Word(arrayToString(meaning.forms), null, meaning.meaning.translation));
                }
                results.addAll(Arrays.asList(parseSentences(jsonGrammar.getJSONArray("sentences"))));
            }
        }
    }

    private String arrayToString(String[] array) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i > 0) {
                buffer.append(',').append(' ');
            }
            buffer.append(array[i]);
        }
        return buffer.toString();
    }
}
