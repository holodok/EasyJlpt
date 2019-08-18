package com.gogaworm.easyjlpt.games;

import java.util.List;

public interface GameTemplate<D> {
    boolean canUseGame(D datum);
    Game<D>[] createGame(D datum, List<D> allData);

    interface Game<D> {
        GameTemplate<D> getGame();
        D getItem();
        AnswerVariant[] getVariants();

        String getTaskText(); //todo: can be multiple choice
    }

    class AnswerVariant {
        public String variantText;
        public boolean correct;

        public AnswerVariant(String variantText, boolean correct) {
            this.variantText = variantText;
            this.correct = correct;
        }

        public AnswerVariant() {
        }
    }
}
