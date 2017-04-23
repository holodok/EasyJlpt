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
public class TaskCreator {
    private List<Task> tasks = new ArrayList<>();

    public void addWords(int sectionId, int lessonId, List<Word> words) {
        for (Word word : words) {
            tasks.add(new WordTask(word));
        }
    }

    public List<Task> generateLearnSession() {
        Collections.shuffle(tasks);
        return tasks;
    }
}
