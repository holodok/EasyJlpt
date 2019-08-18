package com.gogaworm.easyjlpt.ui.gamefragments;

import android.widget.TextView;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.games.SelectKanjiByKunReadingGameTemplate;
import com.gogaworm.easyjlpt.games.SelectKanjiByOnReadingGameTemplate;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnKanjiViewModel;

import static com.gogaworm.easyjlpt.utils.KanjiUtils.*;

public class KanjiSelectVariantGameFragment extends SelectVariantGameFragment<Kanji> {

    @Override
    protected void initTaskText(GameTemplate.Game<Kanji> game, TextView japaneseView) {
        if (game.getGame() instanceof SelectKanjiByOnReadingGameTemplate) {
            japaneseView.setText(getOnReading(getContext(), game.getTaskText()), TextView.BufferType.SPANNABLE);
        } else if (game.getGame() instanceof SelectKanjiByKunReadingGameTemplate) {
            japaneseView.setText(getKunReading(getContext(), game.getTaskText()), TextView.BufferType.SPANNABLE);
        } else {
            super.initTaskText(game, japaneseView);
        }
    }

    @Override
    protected void showAnswer(Kanji kanji, TextView japaneseView, TextView readingView, TextView translationView) {
        japaneseView.setText(kanji.kanji);
        readingView.setText(getKanjiReading(getContext(), kanji), TextView.BufferType.SPANNABLE);
        translationView.setText(kanji.translation);
    }

    @Override
    protected Class<? extends GenericGamesLearnWordsViewModel<Kanji>> getViewModelClass() {
        return LearnKanjiViewModel.class;
    }
}
