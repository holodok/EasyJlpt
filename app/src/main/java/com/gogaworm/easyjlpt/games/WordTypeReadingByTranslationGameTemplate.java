package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Word;

import java.util.List;

public class WordTypeReadingByTranslationGameTemplate  implements GameTemplate<Word> {
    @Override
    public boolean canUseGame(Word datum) {
        return true;
    }

    @Override
    public Game<Word>[] createGame(final Word datum, List<Word> allData) {
        return new Game[] {
                new Game<Word>() {
                    @Override
                    public GameTemplate<Word> getGame() {
                        return WordTypeReadingByTranslationGameTemplate.this;
                    }

                    @Override
                    public Word getItem() {
                        return datum;
                    }

                    @Override
                    public AnswerVariant[] getVariants() {
                        return new AnswerVariant[] {
                                new AnswerVariant(datum.hasReading() ? datum.reading : datum.japanese, true)
                        };
                    }

                    @Override
                    public String getTaskText() {
                        return datum.translation;
                    }
                }
        };
    }
}
