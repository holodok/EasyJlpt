package com.gogaworm.easyjlpt.ui.gamefragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.ReviewWordsViewModel;

public class WordFlashCardGameFragment extends WordViewGameFragment {
    private View resultButtonsPanel;
    private Button noButton;
    private Button yesButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultButtonsPanel = view.findViewById(R.id.resultButtonsPanel);
        noButton = view.findViewById(R.id.noButton);
        yesButton = view.findViewById(R.id.yesButton);

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishGame(false);
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishGame(true);
            }
        });
    }

    @Override
    protected void initGame() {
        super.initGame();
        resultButtonsPanel.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void showAnswer(Word word) {
        super.showAnswer(word);
        submitButton.setVisibility(View.GONE);
        //show yes/no buttons
        resultButtonsPanel.setVisibility(View.VISIBLE);
        //set on click listener to set result and exit game
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_word_flash_card_game;
    }

    @Override
    protected Class<? extends GenericGamesLearnWordsViewModel<Word>> getViewModelClass() {
        return ReviewWordsViewModel.class;
    }
}
