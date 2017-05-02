package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.Task;

import java.util.List;

/**
 * Created on 05.04.2017.
 *
 * @author ikarpova
 */
public abstract class WordGameFragment extends Fragment {
    private LearnLessonActivity learnLessonActivity;
    private boolean showAnswer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTask(getCurrentTask());
    }

    protected abstract void setupTask(Task task);


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

    protected List<Word> getWords() {
        return learnLessonActivity.getWords();
    }

    protected Task getCurrentTask() {
        return learnLessonActivity.getCurrentTask();
    }

    protected void onUserAnswer(boolean correct) {
        if (!showAnswer) {
            showAnswer = true;
            showAnswer(correct);
        } else {
            //then go to next
            learnLessonActivity.onUserAnswer(correct);
        }
    }

    protected abstract void showAnswer(boolean correct);
}
