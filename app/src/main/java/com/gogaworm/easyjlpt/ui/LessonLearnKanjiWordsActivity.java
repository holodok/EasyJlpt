package com.gogaworm.easyjlpt.ui;

import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.games.*;
import com.gogaworm.easyjlpt.ui.gamefragments.*;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnKanjiWordsViewModel;

public class LessonLearnKanjiWordsActivity extends LessonLearnWordsActivity {
    @Override
    protected GenericGamesLearnWordsViewModel<Word> createViewModel() {
        return ViewModelProviders.of(this).get(LearnKanjiWordsViewModel.class);
    }

    protected GameFragment<Word> getFragmentByGameTemplate(GameTemplate<Word> gameTemplate) {
        GameFragment<Word> fragment = null;

        if (gameTemplate instanceof WordViewGameTemplate) {
            fragment = new KanjiWordViewGameFragment();
        } else if (gameTemplate instanceof WordSelectTranslationByJapaneseTemplate
                || gameTemplate instanceof WordSelectReadingByJapaneseGameTemplate
                || gameTemplate instanceof WordSelectJapaneseByTranslationGameTemplate) {
            fragment = new KanjiWordSelectVariantGameFragment();
        } else if (gameTemplate instanceof  WordTypeReadingByJapaneseGameTemplate
                || gameTemplate instanceof WordTypeReadingByTranslationGameTemplate) {
            fragment = new KanjiWordTypeGameFragment();
        }
        return fragment;
    }
}
