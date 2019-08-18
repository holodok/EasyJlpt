package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Kanji;

public class SelectKanjiByKunReadingGameTemplate extends SelectVariantGameTemplate<Kanji> {
    @Override
    protected String getTaskText(Kanji datum) {
        return datum.kunReading;
    }

    @Override
    protected String[] getVariantsText(Kanji task) {
        return new String[] {task.kanji};
    }

    @Override
    public boolean canUseGame(Kanji datum) {
        return datum.hasKunReading();
    }

    @Override
    protected boolean canUseAsVariant(Kanji task) {
        return true;
    }
}
