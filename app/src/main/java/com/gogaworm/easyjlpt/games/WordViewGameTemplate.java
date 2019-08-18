package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Word;

import java.util.List;

public class WordViewGameTemplate implements GameTemplate<Word> {
    @Override
    public boolean canUseGame(Word datum) {
        return true;
    }

    @Override
    public Game<Word>[] createGame(final Word datum, List<Word> allData) {
        Game<Word>[] games = new Game[] {
                new Game() {
                    @Override
                    public GameTemplate<Word> getGame() {
                        return WordViewGameTemplate.this;
                    }

                    @Override
                    public Word getItem() {
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
        return games;
    }
}
