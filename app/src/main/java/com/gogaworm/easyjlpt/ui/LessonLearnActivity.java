package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Lesson;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.ui.gamefragments.GameFragment;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;

import java.util.List;

public abstract class LessonLearnActivity<D> extends AppCompatActivity {
    private ContentLoadingProgressBar progressBar;
    private GenericGamesLearnWordsViewModel<D> viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = inflator.inflate(R.layout.actionbar_title_progress, null);
        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionBarView, layout);
        actionBar.setDisplayShowCustomEnabled(true);

        progressBar = findViewById(R.id.progress);

        final TextView title = actionBarView.findViewById(R.id.title);
        title.setVisibility(View.GONE);
        final NumberProgressBar titleProgress = actionBarView.findViewById(R.id.studyProgress);

        progressBar = findViewById(R.id.progressBar);
        progressBar.show();

        viewModel = createViewModel();

        viewModel.getSelectedLesson().observe(this, new Observer<Lesson>() {
            @Override
            public void onChanged(@Nullable final Lesson lesson) {
                if (lesson == null) return;
                //viewModel.getSelectedLesson().removeObserver(this);
                final LiveData<List<D>> dataForLesson = viewModel.getDataForLesson(lesson.id);
                dataForLesson.observe(LessonLearnActivity.this, new Observer<List<D>>() {
                    @Override
                    public void onChanged(@Nullable List<D> items) {
                        if (items == null) return;
                        //wordsForLesson.removeObserver(this);
                        viewModel.startGame(items);
                    }
                });
            }
        });

        viewModel.getCurrentGame().observe(this, new Observer<GameTemplate.Game<D>>() {
            @Override
            public void onChanged(@Nullable GameTemplate.Game<D> currentGame) {
                if (currentGame == null) {
                    //show results TODO: better make new game
                    title.setVisibility(View.VISIBLE);
                    titleProgress.setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new GameResultFragment()).commit();
                    return;
                }
                //todo
                GameTemplate<D> game = currentGame.getGame();
                Fragment fragment = getFragmentByGameTemplate(game);

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment).commit(); //todo: reuse fragment
                }

                if (progressBar.isShown()) {
                    progressBar.hide();
                }
            }
        });

        viewModel.getGameProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer progress) {
                titleProgress.setProgress(progress);
            }
        });
    }

    protected abstract GenericGamesLearnWordsViewModel<D> createViewModel();
    protected abstract GameFragment<D> getFragmentByGameTemplate(GameTemplate<D> gameTemplate);
}

