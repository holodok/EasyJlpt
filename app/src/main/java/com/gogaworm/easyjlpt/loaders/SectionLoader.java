package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.Section;
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
public class SectionLoader extends JlptDataLoader<Section> {

    public SectionLoader(Context context, JlptSection section, JlptLevel jlptLevel) {
        super(context, section, jlptLevel);
    }

    @Override
    protected List<Section> load() throws IOException, JSONException {
        List<Section> results = new ArrayList<>();
        loadFromFile(results, section, level, Type.SECTION, 0);
        return results;
    }

    @Override
    protected void parseJson(List<Section> results, String json) throws JSONException {
        parseSections(results, json);
    }

    private void parseSections(List<Section> sections, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray sectionsArray = root.getJSONArray("sections");
        for (int i = 0; i < sectionsArray.length(); i++) {
            JSONObject jsonSection = sectionsArray.getJSONObject(i);
            sections.add(new Section(jsonSection.getInt("section"), parseWord(jsonSection.getJSONObject("title"))));
        }
    }
}
