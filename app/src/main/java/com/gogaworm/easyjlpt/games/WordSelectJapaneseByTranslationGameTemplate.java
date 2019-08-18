package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Word;

public class WordSelectJapaneseByTranslationGameTemplate extends SelectVariantGameTemplate<Word> {
    @Override
    protected String getTaskText(Word datum) {
        return datum.translation;
    }

    @Override
    protected String[] getVariantsText(Word task) {
        return new String[] {task.japanese};
    }

    @Override
    public boolean canUseGame(Word datum) {
        return true;
    }
}
