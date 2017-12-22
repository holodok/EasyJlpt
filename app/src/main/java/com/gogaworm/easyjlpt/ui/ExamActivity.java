package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Exam;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;

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
        lesson = getIntent().getParcelableExtra("lesson");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_EXAM;
    }

    @Override
    protected Loader<List<Exam>> createLoader(Bundle args) {
        args.putInt("lessonId", lesson.examId);
        return LoaderFactory.getExamLoader(this, args);
    }

    @Override
    protected void initData(List<Exam> data) {
        examList.clear();
        examList.addAll(data);
        examIndex = 0;
    }

    @Override
    protected void showFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        currentFragment = new WordSelectExamFragment();
        fragmentManager.beginTransaction().add(R.id.content, currentFragment).commit();
    }

    private void initExam() {
        if (examIndex >= examList.size()) {
            //todo: show message with results that exam finished
            finish();
            return;
        }
        ((WordSelectExamFragment) currentFragment).initExam(examList.get(examIndex));
    }

    @Override
    public Exam getCurrentExam() {
        return examList.get(examIndex);
    }

    public void nextSentence() {
        examIndex++;
        initExam();
    }
}
