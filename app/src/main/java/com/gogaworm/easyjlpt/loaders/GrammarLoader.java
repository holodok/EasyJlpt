package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.data.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class GrammarLoader extends LessonLoader<Grammar> {
    public GrammarLoader(Context context, String folder, int lessonId) {
        super(context, folder, lessonId);
    }

    @Override
    protected List<Grammar> createEmptyList() {
        return new ArrayList<>();
    }

    @Override
    protected void parseJson(List<Grammar> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonGrammarList = root.optJSONArray("grammar");
        if (jsonGrammarList != null) {
            for (int i = 0; i < jsonGrammarList.length(); i++) {
                JSONObject jsonGrammar = jsonGrammarList.getJSONObject(i);
                Grammar grammarItem = new Grammar();
                grammarItem.item = jsonGrammar.getString("item");
                grammarItem.meanings = parseMeanings(jsonGrammar.getJSONArray("meanings"));
                grammarItem.notes = parseStrings(jsonGrammar.getJSONArray("notes"));
                grammarItem.sentences = parseSentences(jsonGrammar.getJSONArray("sentences"));
                results.add(grammarItem);
            }
        }
    }

    private Grammar.Meaning[] parseMeanings(JSONArray jsonMeanings) throws JSONException {
        Grammar.Meaning[] meanings = new Grammar.Meaning[jsonMeanings.length()];
        for (int i = 0; i < jsonMeanings.length(); i++) {
            JSONObject jsonMeaning = jsonMeanings.getJSONObject(i);
            meanings[i] = new Grammar.Meaning(
                    parseWord(jsonMeaning.getJSONObject("meaning")),
                    parseStrings(jsonMeaning.getJSONArray("forms")));
        }
        return meanings;
    }

    private Word[] parseSentences(JSONArray jsonSentences) throws JSONException {
        Word[] words = new Word[jsonSentences.length()];
        for (int i = 0; i < jsonSentences.length(); i++) {
            words[i] = parseWord(jsonSentences.getJSONObject(i));
        }
        return words;
    }
}
