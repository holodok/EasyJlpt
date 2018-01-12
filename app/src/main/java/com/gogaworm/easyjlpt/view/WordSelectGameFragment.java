package com.gogaworm.easyjlpt.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.controller.Game;
import com.gogaworm.easyjlpt.controller.WordSelectGame;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.model.WordTaskListViewModel;
import com.gogaworm.easyjlpt.ui.widgets.AnswerButton;
import com.gogaworm.easyjlpt.ui.widgets.KanjiKanaView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class WordSelectGameFragment extends Fragment {

    private WordTaskListViewModel wordTaskListViewModel;

    private TextView questionView;
    private KanjiKanaView japaneseView;
    private View separatorView;
    private TextView translationView;
    private View buttonSeparator;
    private AnswerButton[] answerButtons;
    private View submitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_word_select_game, container, false);
        questionView = parentView.findViewById(R.id.question);
        japaneseView = parentView.findViewById(R.id.japanese);
        separatorView = parentView.findViewById(R.id.separator);
        translationView = parentView.findViewById(R.id.translation);
        buttonSeparator = parentView.findViewById(R.id.buttonSeparator);

        answerButtons = new AnswerButton[] {
                parentView.findViewById(R.id.firstAnswer),
                parentView.findViewById(R.id.secondAnswer),
                parentView.findViewById(R.id.thirdAnswer),
                parentView.findViewById(R.id.forthAnswer)
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserAnswered(((AnswerButton) v).getVariant());
            }
        };
        for (AnswerButton button : answerButtons) {
            button.setOnClickListener(listener);
        }

        submitButton = parentView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordTaskListViewModel.nextTask();
            }
        });

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordTaskListViewModel = ViewModelProviders.of(getActivity()).get(WordTaskListViewModel.class);
        wordTaskListViewModel.getCurrentGame().observe(this, new Observer<Game>() {
            @Override
            public void onChanged(@Nullable Game currentGame) {
                if (currentGame instanceof WordSelectGame) {
                    onGameChanged ((WordSelectGame) currentGame);
                }
            }
        });
    }

    private void onUserAnswered(GameVariant variant) {
        wordTaskListViewModel.userAnswered(variant);
    }

    private void onGameChanged(WordSelectGame game) {
        questionView.setText(game.getQuestion(getContext()));
        japaneseView.setText(game.getJapanese(), game.getReading());
        translationView.setText(game.getTranslation());
        submitButton.setVisibility(game.isAnswered() ? VISIBLE : GONE);
        separatorView.setVisibility(translationView.getText().length() > 0 ? VISIBLE : GONE);

        if (game.isAnswered()) {
            answerButtons[0].setVariant(game.getCorrectAnswer());
            answerButtons[0].highlightCorrect(true);

            if (!game.isAnswerCorrect()) {
                answerButtons[1].setVariant(game.getUserAnswer());
                answerButtons[1].highlightCorrect(false);
            }

            for (int i = game.isAnswerCorrect() ? 1 : 2; i < answerButtons.length; i++) {
                answerButtons[i].setVisibility(GONE);
            }
            buttonSeparator.setVisibility(GONE);
        } else {
            GameVariant[] variants = game.getVariants();
            for (int i = 0; i < answerButtons.length; i++) {
                if (i >= variants.length) {
                    answerButtons[i].setVisibility(GONE);
                } else {
                    answerButtons[i].setVariant(variants[i]);
                    answerButtons[i].setVisibility(VISIBLE);
                }
            }
        }
    }
}
