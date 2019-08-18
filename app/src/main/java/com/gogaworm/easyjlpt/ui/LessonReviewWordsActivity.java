package com.gogaworm.easyjlpt.ui;

import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.games.*;
import com.gogaworm.easyjlpt.ui.gamefragments.*;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.ReviewWordsViewModel;

public class LessonReviewWordsActivity <Model extends GenericGamesLearnWordsViewModel<Word>> extends LessonLearnActivity<Word> {
    @Override
    protected GenericGamesLearnWordsViewModel<Word> createViewModel() {
        return ViewModelProviders.of(this).get(ReviewWordsViewModel.class);
    }

    protected GameFragment<Word> getFragmentByGameTemplate(GameTemplate<Word> gameTemplate) {
        GameFragment<Word> fragment = null;

        if (gameTemplate instanceof WordViewGameTemplate) {
            fragment = new WordFlashCardGameFragment();
        }
        return fragment;
    }
}