package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Word;

public class WordSelectTranslationByJapaneseTemplate extends SelectVariantGameTemplate<Word> {
    @Override
    protected String getTaskText(Word datum) {
        return datum.japanese;
    }

    @Override
    protected String[] getVariantsText(Word task) {
        return new String[] { task.translation };
    }

    @Override
    public boolean canUseGame(Word datum) {
        return true;
    }
}
