package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 07.06.2017.
 *
 * @author ikarpova
 */
public class KanjiWordListLoader extends WordListLoader {
    public KanjiWordListLoader(Context context, String folder, int lessonId) {
        super(context, folder, lessonId);
    }

    @Override
    protected void parseJson(List<Word> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonKanji = root.optJSONArray("kanji");
        if (jsonKanji != null) {
            for (int i = 0; i < jsonKanji.length(); i++) {
                JSONObject jsonKanjiItem = jsonKanji.getJSONObject(i);

                // add kanji as word, it's ok for flash cards
                results.add(new Word(
                        jsonKanjiItem.getString("japanese"),
                        getKanjiReading(jsonKanjiItem.optString("on"), jsonKanjiItem.optString("kun")),
                        jsonKanjiItem.getString("translation")));

                JSONArray jsonWords = jsonKanjiItem.optJSONArray("words");
                if (jsonWords != null) {
                    parseWords(jsonWords, results);
                }
            }
        }
    }

    private String getKanjiReading(String onReading, String kunReading) {
        boolean hasOnReading = !isEmpty(onReading);
        boolean hasKunReading = !isEmpty(kunReading);
        return hasOnReading ? onReading + (hasKunReading ? " " + kunReading : "") : kunReading;
    }
}
