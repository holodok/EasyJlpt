package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Word;

public class WordSelectReadingByJapaneseGameTemplate extends SelectVariantGameTemplate<Word> {
    @Override
    protected String getTaskText(Word datum) {
        return datum.japanese;
    }

    @Override
    protected String[] getVariantsText(Word task) {
        return new String[] {task.reading};
    }

    @Override
    public boolean canUseGame(Word datum) {
        return datum.hasReading();
    }
}
