package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Kanji;

public class KanjiSelectTranslationByKanjiGameTemplate extends SelectVariantGameTemplate<Kanji> {
    @Override
    protected String getTaskText(Kanji datum) {
        return datum.kanji;
    }

    @Override
    protected String[] getVariantsText(Kanji task) {
        return new String[] {task.translation};
    }

    @Override
    public boolean canUseGame(Kanji datum) {
        return true;
    }
}
