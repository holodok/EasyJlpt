package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.Lesson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class LessonListLoader extends JlptDataLoader<Lesson> {
    private int sectionId;

    LessonListLoader(Context context, JlptSection section, JlptLevel jlptLevel, int sectionId) {
        super(context, section, jlptLevel);
        this.sectionId = sectionId;
    }

    @Override
    protected List<Lesson> load() throws IOException, JSONException {
        List<Lesson> results = new ArrayList<>();
        loadFromFile(results, section, level, Type.SECTION, 0);
        return results;
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
