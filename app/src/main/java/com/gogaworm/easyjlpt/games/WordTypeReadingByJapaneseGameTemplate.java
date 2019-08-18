package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Word;

import java.util.List;

public class WordTypeReadingByJapaneseGameTemplate implements GameTemplate<Word> {
    @Override
    public boolean canUseGame(Word datum) {
        return datum.hasReading();
    }

    @Override
    public Game<Word>[] createGame(final Word datum, List<Word> allData) {
        return new Game[] {
                new Game<Word>() {
                    @Override
                    public GameTemplate getGame() {
                        return WordTypeReadingByJapaneseGameTemplate.this;
                    }

                    @Override
                    public Word getItem() {
                        return datum;
                    }

                    @Override
                    public AnswerVariant[] getVariants() {
                        return new AnswerVariant[] {
                                new AnswerVariant(datum.reading, true)
                        };
                    }

                    @Override
                    public String getTaskText() {
                        return datum.japanese;
                    }
                }
        };
    }
}
