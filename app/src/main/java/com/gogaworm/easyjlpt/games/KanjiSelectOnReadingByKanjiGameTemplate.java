package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Kanji;

public class KanjiSelectOnReadingByKanjiGameTemplate extends SelectVariantGameTemplate<Kanji> {
    @Override
    protected String getTaskText(Kanji datum) {
        return datum.kanji;
    }

    @Override
    protected String[] getVariantsText(Kanji task) {
        return task.onReading.split(" ");
    }

    @Override
    public boolean canUseGame(Kanji datum) {
        return datum.hasOnReading();
    }
}
