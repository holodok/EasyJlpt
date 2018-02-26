package com.gogaworm.easyjlpt.controller;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.game.WordTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class WordSelectGame extends Game<WordTask> {
    protected GameVariant[] variants;
    protected VariantsGenerator variantsGenerator;

    WordSelectGame() {
        variants = new GameVariant[4];
    }

    @Override
    public abstract boolean isTaskValidForGame(WordTask wordTask);

    @Override
    protected boolean canGenerateAnswer(List<WordTask> tasks, WordTask task) {
        return variantsGenerator.generateVariants(tasks, task, this, variants);
    }

    public GameVariant[] getVariants() {
        return variants;
    }

    public GameVariant getCorrectAnswer() {
        return variantsGenerator.correctAnswer;
    }

    public boolean isAnswerCorrect() {
        return variantsGenerator.correctAnswer == userVariant;
    }

    abstract static class VariantsGenerator {
        private List<WordTask> cache = new ArrayList<>();
        GameVariant correctAnswer;

        boolean generateVariants(List<WordTask> tasks, WordTask task, Game game, GameVariant[] variants) {
            cache.clear();
            cache.addAll(tasks);
            Collections.shuffle(cache);

            int variantIndex = 0;
            for (int i = 0; i < cache.size() && variantIndex < variants.length; i++) {
                WordTask currentTask = cache.get(i);
                if (game.isTaskValidForGame(currentTask) && !equals(task.value, currentTask.value) && task.value.getClass().equals(currentTask.value.getClass())
                        && !hasVariant(variants, variantIndex, currentTask.value)) { //not found
                    variants[variantIndex++].set(currentTask.value.japanese, currentTask.value.reading, currentTask.value.translation);
                }
            }

            int correctIndex = (int) (Math.random() * variants.length);
            variants[correctIndex].set(task.value.japanese, task.value.reading, task.value.translation);
            correctAnswer = variants[correctIndex];
            return variantIndex == variants.length;
        }

        abstract boolean equals(Word word1, Word word2);
        abstract boolean hasVariant(GameVariant[] variants, int variantIndex, Word word);
    }

}
