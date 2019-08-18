package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Kanji;

import java.util.List;

public class KanjiViewGameTemplate implements GameTemplate<Kanji> {
    @Override
    public boolean canUseGame(Kanji datum) {
        return true;
    }

    @Override
    public Game<Kanji>[] createGame(final Kanji datum, List<Kanji> allData) {
        return new Game[] {
                new Game<Kanji>() {
                    @Override
                    public GameTemplate getGame() {
                        return KanjiViewGameTemplate.this;
                    }

                    @Override
                    public Kanji getItem() {
                        return datum;
                    }

                    @Override
                    public AnswerVariant[] getVariants() {
                        return null;
                    }

                    @Override
                    public String getTaskText() {
                        return null;
                    }
                }
        };
    }
}
