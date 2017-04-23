package com.gogaworm.easyjlpt.game;

/**
 * Created on 23.04.2017.
 *
 * @author ikarpova
 */
public class SelectReadingByKanjiGame implements Game {
    @Override
    public boolean isValid(Task task) {
        return false;
    }

    @Override
    public String getQuestion() {
        return null;
    }

    @Override
    public String getTaskWord() {
        return null;
    }

    @Override
    public String[] getVariants() {
        return new String[0];
    }

    @Override
    public Task getTask() {
        return null;
    }
}
