package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Kanji;

public class SelectKanjiByOnReadingGameTemplate extends SelectVariantGameTemplate<Kanji> {
    @Override
    protected String getTaskText(Kanji datum) {
        return datum.onReading;
    }

    @Override
    protected String[] getVariantsText(Kanji task) {
        return new String[] {task.kanji};
    }

    @Override
    public boolean canUseGame(Kanji datum) {
        return datum.hasOnReading();
    }

    @Override
    protected boolean canUseAsVariant(Kanji task) {
        return true;
    }
}
