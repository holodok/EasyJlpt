package com.gogaworm.easyjlpt.ui;

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
import com.gogaworm.easyjlpt.controller.StudyResults;
import com.gogaworm.easyjlpt.model.WordTaskListViewModel;
import com.gogaworm.easyjlpt.ui.widgets.ArcProgress;

public class WordLearnGameResultsFragment extends Fragment {
    private ArcProgress progressView;
    private TextView studyWordCountView;
    private TextView studyTimeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_word_learn_results, container, false);
        progressView = parentView.findViewById(R.id.progress);
        studyWordCountView = parentView.findViewById(R.id.studyWordCountView);
        studyTimeView = parentView.findViewById(R.id.studyTimeView);

        progressView.setFinishedStrokeColor(getContext().getResources().getColor(R.color.colorPrimary));
        progressView.setUnfinishedStrokeColor(getContext().getResources().getColor(R.color.divider));
        progressView.setStrokeWidth(10);
        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WordTaskListViewModel wordTaskListViewModel = ViewModelProviders.of(getActivity()).get(WordTaskListViewModel.class);
        wordTaskListViewModel.getStudyResults().observe(this, new Observer<StudyResults>() {
            @Override
            public void onChanged(@Nullable StudyResults studyResults) {
                progressView.setProgress(studyResults.getProgress());
                studyWordCountView.setText(String.valueOf(studyResults.getWordCount()));
                long studyTime = studyResults.getStudyTime();
                long minutes = studyTime / 60;
                long seconds = studyTime - minutes * 60;
                studyTimeView.setText(getString(R.string.value_study_time, minutes, seconds));
            }
        });
    }
}
