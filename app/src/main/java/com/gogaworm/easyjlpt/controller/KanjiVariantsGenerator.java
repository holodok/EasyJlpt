package com.gogaworm.easyjlpt.controller;

import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.GameVariant;

public class KanjiVariantsGenerator extends WordSelectGame.VariantsGenerator {
    @Override
    boolean equals(Word word1, Word word2) {
        return word1.japanese.equalsIgnoreCase(word2.japanese);
    }

    @Override
    boolean hasVariant(GameVariant[] variants, int variantIndex, Word word) {
        for (int i = 0; i < variantIndex; i++) {
            if (variants[i].getAnswer().equals(word.japanese)) {
                return true;
            }
        }
        return false;
    }
}
