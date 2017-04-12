package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.gogaworm.easyjlpt.data.Word;

import java.util.List;

/**
 * Created on 05.04.2017.
 *
 * @author ikarpova
 */
public abstract class WordGameFragment extends Fragment {
    private LearnLessonActivity learnLessonActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Word word = learnLessonActivity.getWords().get(4); //todo: remove
        setupTask(word);
    }

    protected abstract void setupTask(Word word);


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
}
