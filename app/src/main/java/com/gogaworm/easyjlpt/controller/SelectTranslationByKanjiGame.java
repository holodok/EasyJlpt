package com.gogaworm.easyjlpt.controller;

import android.content.Context;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Kanji;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.game.WordTask;

public class SelectTranslationByKanjiGame extends WordSelectGame {

    public SelectTranslationByKanjiGame() {
        variantsGenerator = new TranslationVariantsGenerator();
        for (int i = 0; i < variants.length; i++) {
            variants[i] = new GameVariant() {
                @Override
                public String getTranslation() {
                    return translation;
                }

                @Override
                public String getAnswer() {
                    return translation;
                }
            };
        }
    }

    @Override
    public boolean isTaskValidForGame(WordTask wordTask) {
        return wordTask.value.hasKanji();
    }

    @Override
    public String getQuestion(Context context) {
        return context.getString(isAnswered ?
                (isAnswerCorrect() ? R.string.label_answer_correct : R.string.label_answer_incorrect) :
                (task.value instanceof Kanji ? R.string.label_do_you_know_kanji :  R.string.label_can_you_translate));
    }

    @Override
    public String getJapanese() {
        return task.value.japanese;
    }

    @Override
    public CharSequence getReading(Context context) {
        return isAnswered ? task.value.reading : null;
    }
}
