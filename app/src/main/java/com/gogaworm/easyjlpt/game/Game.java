package com.gogaworm.easyjlpt.game;

/**
 * Created on 21.04.2017.
 *
 * @author ikarpova
 */
public interface Game {
    String getQuestion();
    String getTaskWord();
    String[] getVariants();
    Task getTask();
}
