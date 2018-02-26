package com.gogaworm.easyjlpt.controller;

import android.content.Context;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.game.Task;

import java.util.List;

/*
Three states:
- canInitialize
- correct
- incorrect
* */
public abstract class Game<T extends Task> {
    protected T task;
    protected GameVariant userVariant;
    protected boolean isAnswered;

    public abstract boolean isTaskValidForGame(T task); //todo: task instance of

    public boolean canInitialize(List<T> tasks, T task) {
        if (!isTaskValidForGame(task) || !canGenerateAnswer(tasks, task)) {
            return false;
        }

        //reset game
        userVariant = null;
        isAnswered = false;
        this.task = task;

        return true;
    }

    public abstract String getQuestion(Context context);

    public String getJapanese() {
        return null;
    }

    public CharSequence  getReading(Context context) {
        return null;
    }

    public String getTranslation() {
        return null;
    }

    public void userAnswered(GameVariant answer) {
        userVariant = answer;
        isAnswered = true;
        if (isAnswerCorrect()) {
            //onCorrectAnswer();
        } else {
            //onIncorrectAnswer();
        }
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public GameVariant getUserAnswer() {
        return userVariant;
    }

    protected abstract boolean canGenerateAnswer(List<T> tasks, T task);
    public abstract boolean isAnswerCorrect() ;
}
