package com.gogaworm.easyjlpt.ui.gamefragments;

import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnKanjiWordsViewModel;

public class KanjiWordSelectVariantGameFragment extends WordSelectVariantGameFragment {
    @Override
    protected Class<? extends GenericGamesLearnWordsViewModel<Word>> getViewModelClass() {
        return LearnKanjiWordsViewModel.class;
    }
}
