package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.games.StudyResults;
import com.gogaworm.easyjlpt.ui.widget.CircularProgressBar;
import com.gogaworm.easyjlpt.viewmodel.LearnWordsViewModel;

public class GameResultFragment extends Fragment {

    private CircularProgressBar progressView;
    private TextView studyWordCountView;
    private TextView studyTimeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_word_learn_results, container, false);
        progressView = parentView.findViewById(R.id.progress);
        studyWordCountView = parentView.findViewById(R.id.studyWordCountView);
        studyTimeView = parentView.findViewById(R.id.studyTimeView);

        progressView.setProgressColor(getResources().getColor(R.color.colorPrimary));
        progressView.setTextColor(getResources().getColor(R.color.colorAccent));

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LearnWordsViewModel wordTaskListViewModel = ViewModelProviders.of(getActivity()).get(LearnWordsViewModel.class);

        StudyResults studyResults = wordTaskListViewModel.getStudyResults();
        progressView.setProgress(studyResults.getProgress());
        studyWordCountView.setText(String.valueOf(studyResults.getWordCount()));
        long studyTime = studyResults.getStudyTime();
        long minutes = studyTime / 60;
        long seconds = studyTime - minutes * 60;
        studyTimeView.setText(getString(R.string.value_study_time, minutes, seconds));
    }
}
