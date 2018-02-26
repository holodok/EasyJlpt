package com.gogaworm.easyjlpt.controller;

import android.content.Context;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Kanji;
import com.gogaworm.easyjlpt.game.WordTask;
import com.gogaworm.easyjlpt.utils.KanjiUtils;

import java.util.List;

public class FlashCardGame extends Game<WordTask> {

    @Override
    public boolean isTaskValidForGame(WordTask task) {
        return true;
    }

    @Override
    public String getQuestion(Context context) {
        return context.getString(isAnswered ?
                (isAnswerCorrect() ? R.string.label_answer_correct : R.string.label_answer_incorrect) :
                (task.value instanceof Kanji ? R.string.label_do_you_know_kanji : R.string.label_can_you_translate));
    }

    @Override
    public String getJapanese() {
        return task.value.hasKanji() ? task.value.japanese : null;
    }

    @Override
    public CharSequence getReading(Context context) {
        return KanjiUtils.getReading(context, task.value);
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
