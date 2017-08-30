package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;

/**
 * Created on 30.08.2017.
 *
 * @author ikarpova
 */
public class LoaderFactory {
    private LoaderFactory() {}

    public static SectionLoader getSectionLoader(Context context, JlptSection section, JlptLevel jlptLevel) {
        return new SectionLoader(context, section, jlptLevel);
    }

    public static LessonListLoader getLessonListLoader(Context context, JlptSection section, JlptLevel jlptLevel, int sectionId) {
        return new LessonListLoader(context, section, jlptLevel, sectionId);
    }

    public static LessonLoader getViewListLoader(Context context, JlptSection section, JlptLevel jlptLevel, int lessonId) {
        switch (section) {
            case VOCABULARY:
                return new WordListLoader(context, section, jlptLevel, lessonId);
            case KANJI:
                return new KanjiViewListLoader(context, section, jlptLevel, lessonId);
            case GRAMMAR:
                return new GrammarLoader(context, section, jlptLevel, lessonId);
        }
        return null;
    }

    public static Loader getLearnListLoader(Context context, JlptSection section, JlptLevel jlptLevel, int lessonId) {
        switch (section) {
            case VOCABULARY:
                return new WordListLoader(context, section, jlptLevel, lessonId);
            case KANJI:
                return new KanjiWordListLoader(context, section, jlptLevel, lessonId);
            case GRAMMAR:
                return new GrammarWordListLoader(context, section, jlptLevel, lessonId);
        }
        return null;
    }

    public static Loader getExamLoader() {
        return null;
    }

    public static Loader getSearchLoader() {
        return null;
    }
}
