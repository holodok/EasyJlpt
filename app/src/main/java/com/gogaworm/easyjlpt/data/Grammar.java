package com.gogaworm.easyjlpt.data;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class Grammar {
    public String item;
    public Meaning[] meanings;
    public String[] excludes;
    public String examples;
    public String[] notes;
    public Word[] sentences;

    public static class Meaning {
        public Word meaning;
        public String[] forms;

        public Meaning(Word meaning, String[] forms) {
            this.meaning = meaning;
            this.forms = forms;
        }
    }
}
