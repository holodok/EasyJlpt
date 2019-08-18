package com.gogaworm.easyjlpt.loader;

import android.content.Context;
import com.gogaworm.easyjlpt.db.AppDatabase;
import com.gogaworm.easyjlpt.db.Expression;
import com.gogaworm.easyjlpt.db.Kanji;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class KanjiJsonFileLoader extends JsonFileLoader<Kanji> {
    @Override
    public void loadItems(Context context, AppDatabase appDatabase, int courseId, String lessonCode, String lessonFileName) {
        try {
            List<Kanji> kanjis = loadItemsFromJson(context, appDatabase, courseId, lessonCode, lessonFileName);
            for (Kanji kanji : kanjis) {
                int wordId = (int) appDatabase.getKanjiDao().insert(kanji);
                for (Expression expression : kanji.expressions) {
                    expression.groupId = wordId;
                    appDatabase.getExpressionDao().insert(expression);
                }
            }
        } catch (Exception ex) {
            System.err.println("Parsing file: " + lessonFileName);
            ex.printStackTrace();
        }
    }

    @Override
    protected void parseItems(AppDatabase appDatabase, int lessonId, JSONObject root, List<Kanji> results) throws JSONException {
        JSONArray jsonKanjiArray = root.optJSONArray("kanji");

        for (int i = 0; i < jsonKanjiArray.length(); i++) {
            JSONObject json = jsonKanjiArray.getJSONObject(i);
            Kanji kanji = new Kanji(
                    lessonId,
                    json.getString("japanese"),
                    json.optString("on"),
                    json.optString("kun"),
                    json.optString("translation")
            );
            JSONArray expressionsJsonArray = json.optJSONArray("words");
            if (expressionsJsonArray != null) {
                for (int j = 0; j < expressionsJsonArray.length(); j++) {
                    JSONObject expressionJson = expressionsJsonArray.getJSONObject(j);
                    kanji.expressions.add(new Expression(
                            lessonId,
                            expressionJson.getString("japanese"),
                            expressionJson.optString("reading"),
                            expressionJson.optString("translation"),
                            Expression.TYPE_KANJI));
                }
            }
            results.add(kanji);
        }
    }
}
