package com.gogaworm.easyjlpt.game;

/**
 * Created on 21.04.2017.
 *
 * @author ikarpova
 */
public interface Game {
    boolean isValid(Task task);
    int getQuestion();
    String getTaskWord(Task task);
    String[] getVariants();
}
