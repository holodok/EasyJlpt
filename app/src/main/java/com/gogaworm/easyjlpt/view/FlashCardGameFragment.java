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
import com.gogaworm.easyjlpt.controller.FlashCardGame;
import com.gogaworm.easyjlpt.controller.Game;
import com.gogaworm.easyjlpt.model.WordTaskListViewModel;

public class FlashCardGameFragment extends Fragment {
    private TextView questionView;
    private TextView japaneseView;
    private TextView readingView;
    private TextView translationView;
    private View submitButton;

    private WordTaskListViewModel wordTaskListViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_flash_card, container, false);
        questionView = parentView.findViewById(R.id.question);
        japaneseView = parentView.findViewById(R.id.japanese);
        readingView = parentView.findViewById(R.id.reading);
        translationView = parentView.findViewById(R.id.translation);

        submitButton = parentView.findViewById(R.id.submitButton);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordTaskListViewModel = ViewModelProviders.of(getActivity()).get(WordTaskListViewModel.class);
        wordTaskListViewModel.getCurrentGame().observe(this, new Observer<Game>() {
            @Override
            public void onChanged(@Nullable Game currentGame) {
                if (currentGame instanceof FlashCardGame) {
                    onGameChanged ((FlashCardGame) currentGame);
                }
            }
        });
    }

    private void onGameChanged(final FlashCardGame game) {
        questionView.setText(game.getQuestion(getContext()));
        japaneseView.setText(game.getJapanese());
        readingView.setText(game.getReading(getContext()), TextView.BufferType.SPANNABLE);
        translationView.setText(game.getTranslation());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.isAnswered()) {
                    wordTaskListViewModel.nextTask();
                } else {
                    wordTaskListViewModel.userAnswered(null);
                }
            }
        });
    }
}
