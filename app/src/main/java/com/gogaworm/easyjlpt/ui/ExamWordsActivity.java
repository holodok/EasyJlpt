package com.gogaworm.easyjlpt.ui;

import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.db.Exam;
import com.gogaworm.easyjlpt.games.ExamGameTemplate;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.ui.gamefragments.ExamGameFragment;
import com.gogaworm.easyjlpt.ui.gamefragments.GameFragment;
import com.gogaworm.easyjlpt.viewmodel.ExamViewModel;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;

public class ExamWordsActivity extends LessonLearnActivity<Exam> {

    @Override
    protected GenericGamesLearnWordsViewModel<Exam> createViewModel() {
        return ViewModelProviders.of(this).get(ExamViewModel.class);
    }

    @Override
    protected GameFragment<Exam> getFragmentByGameTemplate(GameTemplate<Exam> gameTemplate) {
        GameFragment<Exam> fragment = null;
        if (gameTemplate instanceof ExamGameTemplate) {
            fragment = new ExamGameFragment();
        }

        return fragment;
    }
}
