package com.gogaworm.easyjlpt.controller;

import android.content.Context;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.game.WordTask;

public class SelectReadingByKanjiGame extends WordSelectGame {

    public SelectReadingByKanjiGame() {
        variantsGenerator = new ReadingVariantsGenerator();
        for (int i = 0; i < variants.length; i++) {
            variants[i] = new GameVariant() {
                @Override
                public String getReading() {
                    return reading;
                }

                @Override
                public String getAnswer() {
                    return reading;
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
        return context.getString(isAnswered ? (isAnswerCorrect() ? R.string.label_answer_correct : R.string.label_answer_incorrect) : R.string.label_can_you_read);
    }

    @Override
    public String getJapanese() {
        return task.value.japanese;
    }

    @Override
    public String getTranslation() {
        return isAnswered ? task.value.translation : null;
    }
}
