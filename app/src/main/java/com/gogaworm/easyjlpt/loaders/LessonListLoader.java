package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Lesson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class LessonListLoader extends JlptDataLoader<Lesson> {
    private int sectionId;

    public LessonListLoader(Context context, String folder, int sectionId) {
        super(context, folder);
        this.sectionId = sectionId;
    }

    @Override
    protected void syncWithDataBase(List<Lesson> results) {
        //todo: load user data from db and progress
    }

    @Override
    protected String[] getFiles(String folder) {
        return new String[] { folder + "/sections"};
    }

    @Override
    protected List<Lesson> createEmptyList() {
        return new ArrayList<>();
    }

    @Override
    protected void parseJson(List<Lesson> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray sectionsArray = root.getJSONArray("sections");
        for (int i = 0; i < sectionsArray.length(); i++) {
            JSONObject jsonSection = sectionsArray.getJSONObject(i);
            int sectionId = jsonSection.getInt("section");
            if (sectionId == this.sectionId) {
                JSONArray lessonArray = jsonSection.getJSONArray("lessons");
                for (int j = 0; j < lessonArray.length(); j++) {
                    JSONObject jsonLesson = lessonArray.getJSONObject(j);
                    results.add(parseLesson(jsonLesson, sectionId));
                }
                break;
            }
        }
    }

    private Lesson parseLesson(JSONObject json, int sectionId) throws JSONException {
        return new Lesson(
                json.getInt("lesson"),
                parseWord(json.getJSONObject("title")),
                json.getInt("lesson") + sectionId * 10,
                json.getInt("lesson") + sectionId * 10);
    }
}
