package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Kanji;

public class KanjiSelectKunReadingByKanjiGameTemplate extends SelectVariantGameTemplate<Kanji> {
    @Override
    protected String getTaskText(Kanji datum) {
        return datum.kanji;
    }

    @Override
    protected String[] getVariantsText(Kanji task) {
        return task.kunReading.split(" ");
    }

    @Override
    public boolean canUseGame(Kanji datum) {
        return datum.hasKunReading();
    }
}
