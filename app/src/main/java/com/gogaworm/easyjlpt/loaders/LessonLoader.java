package com.gogaworm.easyjlpt.loaders;

import android.content.Context;

import java.util.List;

/**
 * Created on 17.07.2017.
 *
 * @author ikarpova
 */
public abstract class LessonLoader<V> extends JlptDataLoader<V> {
    private int lessonId;

    LessonLoader(Context context, String folder, int lessonId) {
        super(context, folder);
        this.lessonId = lessonId;
    }

    @Override
    protected void syncWithDataBase(List<V> results) {

    }

    @Override
    protected String[] getFiles(String folder) {
        return new String[] { folder + "/lesson_" + lessonId };
    }
}
