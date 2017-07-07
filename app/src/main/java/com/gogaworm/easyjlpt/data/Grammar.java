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

    public class Meaning {
        public Word meaning;
        public String[] forms;
    }
}
