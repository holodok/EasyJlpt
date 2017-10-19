package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created on 17.07.2017.
 *
 * @author ikarpova
 */
public abstract class LessonLoader<V> extends JlptDataLoader<V> {
    int lessonId;

    LessonLoader(Context context, JlptSection section, JlptLevel jlptLevel, int lessonId) {
        super(context, section, jlptLevel);
        this.lessonId = lessonId;
    }

    @Override
    protected List<V> load() throws IOException, JSONException {
        List<V> results = createEmptyList();
        loadFromFile(results, section, level, Type.LESSON, lessonId);
        return results;

    }

    protected abstract List<V> createEmptyList();
}
