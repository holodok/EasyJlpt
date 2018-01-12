package com.gogaworm.easyjlpt.controller;

import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.GameVariant;

public class ReadingVariantsGenerator extends WordSelectGame.VariantsGenerator {
    @Override
    boolean equals(Word word1, Word word2) {
        return word1.reading.equalsIgnoreCase(word2.reading);
    }

    @Override
    boolean hasVariant(GameVariant[] variants, int variantIndex, Word word) {
        for (int i = 0; i < variantIndex; i++) {
            if (variants[i].getAnswer().equals(word.reading)) {
                return true;
            }
        }
        return false;
    }
}
