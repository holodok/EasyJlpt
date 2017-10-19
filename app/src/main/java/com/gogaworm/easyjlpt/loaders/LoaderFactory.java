package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import android.os.Bundle;
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

    public static SectionLoader getSectionLoader(Context context, Bundle bundle) {
        JlptSection section = JlptSection.valueOf(bundle.getString("section"));
        JlptLevel jlptLevel = JlptLevel.valueOf(bundle.getString("level"));

        return new SectionLoader(context, section, jlptLevel);
    }

    public static LessonListLoader getLessonListLoader(Context context, Bundle bundle) {
        JlptSection section = JlptSection.valueOf(bundle.getString("section"));
        JlptLevel jlptLevel = JlptLevel.valueOf(bundle.getString("level"));
        int sectionId = bundle.getInt("sectionId");
        return new LessonListLoader(context, section, jlptLevel, sectionId);
    }

    public static LessonLoader getViewListLoader(Context context, Bundle bundle) {
        JlptSection section = JlptSection.valueOf(bundle.getString("section"));
        JlptLevel jlptLevel = JlptLevel.valueOf(bundle.getString("level"));
        int lessonId = bundle.getInt("lessonId", 0);
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

    public static Loader getLearnListLoader(Context context, Bundle bundle) {
        JlptSection section = JlptSection.valueOf(bundle.getString("section"));
        JlptLevel jlptLevel = JlptLevel.valueOf(bundle.getString("level"));
        int lessonId = bundle.getInt("lessonId", 0);

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

    public static Loader getExamLoader(Context context, Bundle bundle) {
        JlptSection section = JlptSection.valueOf(bundle.getString("section"));
        JlptLevel level = JlptLevel.valueOf(bundle.getString("level"));
        int lessonId = bundle.getInt("lessonId", 0);
        return new ExamLoader(context, section, level, lessonId);
    }

    public static Loader getSearchLoader(Context context, Bundle bundle) {
        JlptSection section = JlptSection.valueOf(bundle.getString("section"));
        switch (section) {
            case VOCABULARY:
                return null;
            case KANJI:
                return null;
            case GRAMMAR:
                return new GrammarDictionaryLoader(context, section);
        }
        return null;
    }
}
