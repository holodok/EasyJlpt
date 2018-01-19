package com.gogaworm.easyjlpt.controller;

import android.content.Context;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.game.WordTask;

import java.util.List;

public class FlashCardGame extends Game<WordTask> {

    @Override
    public boolean isTaskValidForGame(WordTask task) {
        return true;
    }

    @Override
    public String getQuestion(Context context) {
        return context.getString(isAnswered ? (isAnswerCorrect() ? R.string.label_answer_correct : R.string.label_answer_incorrect) : R.string.label_can_you_translate);
    }

    @Override
    public String getJapanese() {
        return task.value.hasKanji() ? task.value.japanese : null;
    }

    @Override
    public String getReading() {
        return task.value.hasKanji() ? task.value.reading : task.value.japanese;
    }

    @Override
    public String getTranslation() {
        return isAnswered ? task.value.translation : null;
    }

    @Override
    protected boolean canGenerateAnswer(List<WordTask> tasks, WordTask task) {
        return true;
    }

    @Override
    public boolean isAnswerCorrect() {
        return true;
    }
}
