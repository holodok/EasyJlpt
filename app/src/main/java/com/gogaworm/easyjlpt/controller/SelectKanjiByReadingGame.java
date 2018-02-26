package com.gogaworm.easyjlpt.controller;

import android.content.Context;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.game.WordTask;

public class SelectKanjiByReadingGame extends WordSelectGame {

    public SelectKanjiByReadingGame() {
        variantsGenerator = new KanjiVariantsGenerator();
        for (int i = 0; i < variants.length; i++) {
            variants[i] = new GameVariant() {
                @Override
                public String getReading() {
                    return japanese;
                }

                @Override
                public String getAnswer() {
                    return japanese;
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
        return context.getString(isAnswered ? (isAnswerCorrect() ? R.string.label_answer_correct : R.string.label_answer_incorrect) : R.string.label_do_you_know);
    }

    @Override
    public CharSequence getReading(Context context) {
        return task.value.reading;
    }

    @Override
    public String getTranslation() {
        return isAnswered ? task.value.translation : null;
    }
}
