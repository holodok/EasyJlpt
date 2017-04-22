package com.gogaworm.easyjlpt.game;

import com.gogaworm.easyjlpt.data.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 17.04.2017.
 *
 * @author ikarpova
 */
public class GameController {
    enum GameType {
        FLASH_CARD,
        SELECT_TRANSLATION_BY_READING,
        SELECT_TRANSLATION_BY_KANJI,
        SELECT_READING_BY_KANJI,
        SELECT_KANJI_BY_READING,
        SELECT_KANJI_BY_TRANSLATION,
        WRITE_READING,
        MULTYSELECT_KANJI_READING,
        WRITE_KANJI_IN_KANJI
    }

    private List<Task> tasks = new ArrayList<>();
    private int currentIndex;

    public void addWord(Word word) {
        tasks.add(new WordTask(word));
    }

    public void generateLearnSession() {
        Collections.shuffle(tasks);
    }

    public boolean hasNext() {
        return currentIndex < tasks.size();
    }

    public Task getTask() {
        Task task = tasks.get(currentIndex);
        task.learnStep = 1;
        return task;
    }

    public void onReply(boolean correct) {
        // adjust learnStep
        currentIndex++;
    }
}
