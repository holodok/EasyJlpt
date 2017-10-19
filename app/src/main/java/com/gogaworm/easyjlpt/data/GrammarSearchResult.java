package com.gogaworm.easyjlpt.data;

/**
 * Created on 15.10.2017.
 *
 * @author ikarpova
 */
public class GrammarSearchResult {
    public final JlptSection section;
    public final JlptLevel level;
    public final int lessonId;
    public final String item;
    public final Grammar.Meaning meaning;

    public GrammarSearchResult(JlptSection section, JlptLevel level, int lessonId, String item, Grammar.Meaning meaning) {
        this.section = section;
        this.level = level;
        this.lessonId = lessonId;
        this.item = item;
        this.meaning = meaning;
    }
}
