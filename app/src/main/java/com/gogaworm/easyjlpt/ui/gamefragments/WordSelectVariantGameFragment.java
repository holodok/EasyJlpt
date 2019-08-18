package com.gogaworm.easyjlpt.ui.gamefragments;

import android.widget.TextView;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnWordsViewModel;

public class WordSelectVariantGameFragment extends SelectVariantGameFragment<Word> {

    @Override
    protected void showAnswer(Word word, TextView japaneseView, TextView readingView, TextView translationView) {
        japaneseView.setText(word.japanese);
        readingView.setText(word.reading, TextView.BufferType.SPANNABLE);
        translationView.setText(word.translation);
    }

    @Override
    protected Class<? extends GenericGamesLearnWordsViewModel<Word>> getViewModelClass() {
        return LearnWordsViewModel.class;
    }
}
