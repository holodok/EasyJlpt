package com.gogaworm.easyjlpt.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SelectVariantGameTemplate<D> implements GameTemplate<D> {
    protected List<D> cache = new ArrayList<>();

    @Override
    public Game<D>[] createGame(final D datum, List<D> allData) {
        String[] variantsText = getVariantsText(datum);
        Game<D>[] games = new Game[variantsText.length];
        for (int i = 0; i < variantsText.length; i++) {
            final String variantText = variantsText[i];
            games[i] = new SelectVariantGame<D>(this, datum, getTaskText(datum));
            generateVariants(allData, variantText, variantsText, games[i].getVariants()); //todo: if no variants can be generated, skip game
        }

        return games;
    }

    protected abstract String getTaskText(D datum);

    protected boolean generateVariants(List<D> tasks, String taskVariantText, String[] answerVariantsText, AnswerVariant[] variants) {
        cache.addAll(tasks);
        Collections.shuffle(cache);

        int variantIndex = 0;
        for (int i = 0; i < cache.size() && variantIndex < variants.length; i++) {
            D tmp = cache.get(i);
            String tmpVariantText = getVariantsText(tmp)[0];
            if (canUseAsVariant(tmp)
                    && !hasVariant(variants, variantIndex, tmpVariantText, answerVariantsText)) {

                variants[variantIndex++].variantText = tmpVariantText;
            }
        }

        int correctIndex = (int) (Math.random() * variants.length);
        variants[correctIndex].variantText = taskVariantText;
        variants[correctIndex].correct = true;
        cache.clear();
        return variantIndex == variants.length;
    }

    protected boolean canUseAsVariant(D task) {
        return canUseGame(task);
    }

    protected boolean hasVariant(AnswerVariant[] variants, int variantIndex, String variantText, String[] answerVariantsText) {
        for (int i = 0; i < variantIndex; i++) {
            if (variants[i].variantText.equals(variantText)) {
                return true;
            }
        }
        for (String answerVariant : answerVariantsText) {
            if (answerVariant.equalsIgnoreCase(variantText)) {
                return true;
            }
        }
        return false;
    }

    protected abstract String[] getVariantsText(D task);

    class SelectVariantGame<D> implements Game<D> {
        private GameTemplate<D> game;
        private D item;
        private String taskText;
        private final AnswerVariant[] variants;

        public SelectVariantGame(GameTemplate<D> game, D item, String taskText) {
            this.game = game;
            this.item = item;
            this.taskText = taskText;
            variants = new AnswerVariant[4];
            for (int i = 0; i < variants.length; i++) {
                variants[i] = new AnswerVariant();
            }
        }

        @Override
        public GameTemplate<D> getGame() {
            return game;
        }

        @Override
        public D getItem() {
            return item;
        }

        @Override
        public AnswerVariant[] getVariants() {
            return variants;
        }

        @Override
        public String getTaskText() {
            return taskText;
        }
    }
}
