package com.gogaworm.easyjlpt.ui.gamefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.games.WordViewGameTemplate;
import com.gogaworm.easyjlpt.ui.adapters.SentenceDataAdapter;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnWordsViewModel;

public class WordViewGameFragment extends GameFragment<Word> {
    private TextView japaneseView;
    private TextView readingView;
    private TextView translationView;
    protected Button submitButton;
    private SentenceDataAdapter adapter;

    private WordViewGameTemplate.Game<Word> game;

    protected boolean isAnswerShown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(getLayoutId(), container, false);
        japaneseView = parentView.findViewById(R.id.japanese);
        readingView = parentView.findViewById(R.id.reading);
        translationView = parentView.findViewById(R.id.translation);

        RecyclerView recyclerView = parentView.findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SentenceDataAdapter(getContext(), null);
        recyclerView.setAdapter(adapter);


        submitButton = parentView.findViewById(R.id.submitButton);
        return parentView;
    }

    protected int getLayoutId() {
        return R.layout.fragment_word_view_game;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GameTemplate.Game<Word> game = viewModel.getCurrentGame().getValue();
        if (game != null && game.getGame() instanceof WordViewGameTemplate) {
            this.game = game;
            initGame();
        }
    }

    @Override
    protected Class<? extends GenericGamesLearnWordsViewModel<Word>> getViewModelClass() {
        return LearnWordsViewModel.class;
    }

    protected void initGame() {
        final Word word = game.getItem();
        japaneseView.setText(word.japanese);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
        isAnswerShown = false;
    }

    protected void onSubmit() {
        Word word = game.getItem();
        if (isAnswerShown) {
            finishGame(true);
        } else {
            showAnswer(word);
        }
    }

    protected void showAnswer(Word word) {
        readingView.setText(word.reading, TextView.BufferType.SPANNABLE);
        translationView.setText(word.translation);
        adapter.setData(word.sentences);
        isAnswerShown = true;
    }

    protected void finishGame(boolean result) {
        viewModel.gameFinished(result);
    }
}
