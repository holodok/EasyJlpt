package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gogaworm.easyjlpt.R;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public class StartLessonFragment extends Fragment {
    private LearnLessonActivity learnLessonActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_start_lesson, container, false);
        parentView.findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learnLessonActivity.start();
            }
        });
        return parentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        learnLessonActivity = (LearnLessonActivity) context;
    }

    @Override
    public void onDetach() {
        learnLessonActivity = null;
        super.onDetach();
    }

}
