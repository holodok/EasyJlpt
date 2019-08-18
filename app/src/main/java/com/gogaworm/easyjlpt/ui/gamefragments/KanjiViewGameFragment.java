package com.gogaworm.easyjlpt.ui.gamefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.viewmodel.LearnKanjiViewModel;

import static com.gogaworm.easyjlpt.utils.KanjiUtils.getKanjiReading;

public class KanjiViewGameFragment extends GameFragment<Kanji> {
    private TextView japaneseView;
    private TextView readingView;
    private TextView translationView;
    private Button submitButton;
    private GameTemplate.Game<Kanji> currentGameInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_word_view_game, container, false);
        japaneseView = parentView.findViewById(R.id.japanese);
        readingView = parentView.findViewById(R.id.reading);
        translationView = parentView.findViewById(R.id.translation);

        submitButton = parentView.findViewById(R.id.submitButton);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        currentGameInstance = viewModel.getCurrentGame().getValue();
        final Kanji kanji = currentGameInstance.getItem();
        japaneseView.setText(kanji.kanji);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (translationView.getText().length() == 0) {
                    readingView.setText(getKanjiReading(getContext(), kanji), TextView.BufferType.SPANNABLE);
                    translationView.setText(kanji.translation);
                } else {
                    viewModel.gameFinished(true);
                }
            }
        });
    }

    @Override
    protected Class<LearnKanjiViewModel> getViewModelClass() {
        return LearnKanjiViewModel.class;
    }
}
