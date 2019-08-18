package com.gogaworm.easyjlpt.loader;

import android.content.Context;
import com.gogaworm.easyjlpt.db.AppDatabase;
import com.gogaworm.easyjlpt.db.Lesson;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class JsonFileLoader<D> {
    public abstract void loadItems(Context context, AppDatabase appDatabase, int courseId, String lessonCode, String lessonFileName);

    List<D> loadItemsFromJson(Context context, AppDatabase appDatabase, int courseId, String lessonCode, String lessonFileName) throws IOException, JSONException {
        List<D> words = new ArrayList<>();
        String content = loadJsonFromAsset(context, lessonFileName);
        parseJson(appDatabase, courseId, lessonCode, words, content);
        return words;
    }

    private String loadJsonFromAsset(Context context, String fileName) throws IOException {
        StringBuilder text = new StringBuilder();
        try (InputStream is = context.getAssets().open(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
            }
            bufferedReader.close();
        }
        return text.toString();
    }

    private void parseJson(AppDatabase appDatabase, int courseId, String code, List<D> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);

        //save lesson
        Lesson lesson = appDatabase.getLessonDao().getLessonByCode(code);
        if (lesson == null) {
            lesson = new Lesson(courseId, root.optString("title"));
            lesson.id = (int) appDatabase.getLessonDao().insert(lesson); //todo: insert or update
        }

        parseItems(appDatabase, lesson.id, root, results);
    }

    protected abstract void parseItems(AppDatabase appDatabase, int lessonId, JSONObject root, List<D> results) throws JSONException;
}
