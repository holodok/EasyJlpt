package com.gogaworm.easyjlpt.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.controller.FlashCardGame;
import com.gogaworm.easyjlpt.controller.Game;
import com.gogaworm.easyjlpt.controller.StudyResults;
import com.gogaworm.easyjlpt.controller.WordSelectGame;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.model.WordTaskListViewModel;
import com.gogaworm.easyjlpt.ui.WordLearnGameResultsFragment;

public class LearnWordsViewActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private NumberProgressBar titleProgress;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_session);

        final ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = inflator.inflate(R.layout.actionbar_title_progress, null);
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        actionBar.setCustomView(actionBarView, layout);
        actionBar.setDisplayShowCustomEnabled(true);

        progressBar = findViewById(R.id.progress);

        title = actionBarView.findViewById(R.id.title);
        titleProgress = actionBarView.findViewById(R.id.studyProgress);

        UserSession userSession = getIntent().getParcelableExtra("userSession");
        WordTaskListViewModel wordTaskListViewModel = ViewModelProviders.of(this).get(WordTaskListViewModel.class);
        wordTaskListViewModel.startGame(userSession);

        wordTaskListViewModel.getCurrentGame().observe(this, new Observer<Game>() {
            @Override
            public void onChanged(@Nullable Game currentGame) {
                // create proper fragment
                Fragment fragment;
                if (currentGame instanceof FlashCardGame) {
                    fragment = new FlashCardGameFragment();
                } else if (currentGame instanceof WordSelectGame) {
                    fragment = new WordSelectGameFragment();
                } else {
                    throw new IllegalArgumentException("Game not supported!");
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, "GAME").commit();
                progressBar.setVisibility(View.GONE);
            }
        });

        wordTaskListViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer progress) {
                int delta = progress - titleProgress.getProgress();
                for (int i = 0; i < delta; i++) {
                    titleProgress.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            titleProgress.setProgress(titleProgress.getProgress() + 1);
                        }
                    }, 100 * i);
                }
            }
        });

        wordTaskListViewModel.getStudyResults().observe(this, new Observer<StudyResults>() {
            @Override
            public void onChanged(@Nullable StudyResults studyResults) {
                if (studyResults == null) {
                    return;
                }
                title.setText(R.string.title_results);
                titleProgress.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new WordLearnGameResultsFragment(), "GAME").commit();
            }
        });
    }
}
