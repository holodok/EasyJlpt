package com.gogaworm.easyjlpt.ui;

import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.games.*;
import com.gogaworm.easyjlpt.ui.gamefragments.GameFragment;
import com.gogaworm.easyjlpt.ui.gamefragments.WordSelectVariantGameFragment;
import com.gogaworm.easyjlpt.ui.gamefragments.WordTypeGameFragment;
import com.gogaworm.easyjlpt.ui.gamefragments.WordViewGameFragment;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnWordsViewModel;

public class LessonLearnWordsActivity extends LessonLearnActivity<Word> {
    @Override
    protected GenericGamesLearnWordsViewModel<Word> createViewModel() {
        return ViewModelProviders.of(this).get(LearnWordsViewModel.class);
    }

    protected GameFragment<Word> getFragmentByGameTemplate(GameTemplate<Word> gameTemplate) {
        GameFragment<Word> fragment = null;

        if (gameTemplate instanceof WordViewGameTemplate) {
            fragment = new WordViewGameFragment();
        } else if (gameTemplate instanceof WordSelectTranslationByJapaneseTemplate
                || gameTemplate instanceof WordSelectReadingByJapaneseGameTemplate
                || gameTemplate instanceof WordSelectJapaneseByTranslationGameTemplate) {
            fragment = new WordSelectVariantGameFragment();
        } else if (gameTemplate instanceof  WordTypeReadingByJapaneseGameTemplate
                || gameTemplate instanceof WordTypeReadingByTranslationGameTemplate) {
            fragment = new WordTypeGameFragment();
        }
        return fragment;
    }
}
