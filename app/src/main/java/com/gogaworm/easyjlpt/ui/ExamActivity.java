package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.view.View;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Exam;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.loaders.ExamLoader;

import java.util.ArrayList;
import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_EXAM;

/**
 * Created on 18.08.2017.
 *
 * @author ikarpova
 */
public class ExamActivity extends UserSessionLoaderActivity<Exam> implements WordSelectExamFragment.ExamListener {
    private Lesson lesson;
    private List<Exam> examList = new ArrayList<>();
    private int examIndex;

    private WordSelectExamFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSentence();
            }
        });
*/

        lesson = getIntent().getParcelableExtra("lesson");

        FragmentManager fragmentManager = getSupportFragmentManager();
        currentFragment = new WordSelectExamFragment();
        fragmentManager.beginTransaction().add(R.id.content, currentFragment).commit();

        getSupportLoaderManager().initLoader(LOADER_ID_EXAM, null, this).forceLoad();
    }

    @Override
    public void onLoadFinished(Loader<List<Exam>> loader, List<Exam> data) {
        examList.clear();
        examList.addAll(data);
        examIndex = 0;
        initExam();
    }

    @Override
    protected Loader<List<Exam>> createLoader(String folder) {
        return new ExamLoader(this, folder, lesson.examId);
    }

    private void initExam() {
        if (examIndex >= examList.size()) {
            //show message that exam finished
            return;
        }
        currentFragment.initExam(examList.get(examIndex));
    }

    public void nextSentence() {
        examIndex++;
        initExam();
    }
}
