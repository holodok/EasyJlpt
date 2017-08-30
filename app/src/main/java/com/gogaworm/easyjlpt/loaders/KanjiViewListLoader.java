package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.Kanji;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created on 30.08.2017.
 *
 * @author ikarpova
 */
public class KanjiViewListLoader extends LessonLoader<Kanji> {
    KanjiViewListLoader(Context context, JlptSection section, JlptLevel jlptLevel, int lessonid) {
        super(context, section, jlptLevel, lessonid);
    }

    @Override
    protected List<Kanji> createEmptyList() {
        return new LinkedList<>();
    }

    @Override
    protected void parseJson(List<Kanji> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray jsonKanji = root.optJSONArray("kanji");
        if (jsonKanji != null) {
            for (int i = 0; i < jsonKanji.length(); i++) {
                JSONObject jsonKanjiItem = jsonKanji.getJSONObject(i);

                // add kanji as word, it's ok for flash cards
                results.add(new Kanji(
                        jsonKanjiItem.getString("japanese"),
                        jsonKanjiItem.optString("on"),
                        jsonKanjiItem.optString("kun"),
                        jsonKanjiItem.getString("translation")));
            }
        }
    }
}
