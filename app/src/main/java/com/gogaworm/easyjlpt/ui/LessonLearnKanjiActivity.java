package com.gogaworm.easyjlpt.ui;

import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.games.*;
import com.gogaworm.easyjlpt.ui.gamefragments.GameFragment;
import com.gogaworm.easyjlpt.ui.gamefragments.KanjiBigSelectVariantGameFragment;
import com.gogaworm.easyjlpt.ui.gamefragments.KanjiSelectVariantGameFragment;
import com.gogaworm.easyjlpt.ui.gamefragments.KanjiViewGameFragment;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnKanjiViewModel;

public class LessonLearnKanjiActivity extends LessonLearnActivity<Kanji> {
    @Override
    protected GenericGamesLearnWordsViewModel<Kanji> createViewModel() {
        return ViewModelProviders.of(this).get(LearnKanjiViewModel.class);
    }

    @Override
    protected GameFragment<Kanji> getFragmentByGameTemplate(GameTemplate<Kanji> gameTemplate) {
        GameFragment<Kanji> fragment = null;
        if (gameTemplate instanceof KanjiViewGameTemplate) {
            fragment = new KanjiViewGameFragment();
        } else if (gameTemplate instanceof KanjiSelectJapaneseByTranslationGameTemplate ||
                gameTemplate instanceof KanjiSelectOnReadingByKanjiGameTemplate ||
                gameTemplate instanceof SelectKanjiByOnReadingGameTemplate ||
                gameTemplate instanceof SelectKanjiByKunReadingGameTemplate) {
            fragment = new KanjiBigSelectVariantGameFragment();
        } else if (gameTemplate instanceof KanjiSelectKunReadingByKanjiGameTemplate ||
                gameTemplate instanceof KanjiSelectTranslationByKanjiGameTemplate) {
            fragment = new KanjiSelectVariantGameFragment();
        }

        return fragment;
    }
}
