package com.gogaworm.easyjlpt.game;

import com.gogaworm.easyjlpt.data.Word;

/**
 * Created on 17.04.2017.
 *
 * @author ikarpova
 */
public class WordTask extends Task<Word> {
    public int currentGame;

    public WordTask(Word word) {
        value = word;
    }
}
