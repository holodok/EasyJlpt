package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Kanji;

public class KanjiSelectJapaneseByTranslationGameTemplate extends SelectVariantGameTemplate<Kanji> {
    @Override
    protected String getTaskText(Kanji datum) {
        return datum.translation;
    }

    @Override
    protected String[] getVariantsText(Kanji task) {
        return new String[] { task.kanji };
    }

    @Override
    public boolean canUseGame(Kanji datum) {
        return true;
    }
}
