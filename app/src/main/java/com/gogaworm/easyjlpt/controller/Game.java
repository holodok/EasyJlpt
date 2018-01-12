package com.gogaworm.easyjlpt.controller;

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
