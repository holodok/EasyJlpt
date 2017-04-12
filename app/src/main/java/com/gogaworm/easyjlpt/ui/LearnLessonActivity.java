package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.loaders.WordListLoader;
import com.gogaworm.easyjlpt.utils.Constants;

import java.util.List;

/**
 * Created on 29.03.2017.
 *
 * @author ikarpova
 */
public class LearnLessonActivity extends UserSessionLoaderActivity<Word> {
    private int lessonId;
    private List<Word> words;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lessonId = getIntent().getIntExtra("lessonId", 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new StartLessonFragment();
        fragmentManager.beginTransaction().add(R.id.content, fragment).commit();

        getSupportActionBar().setTitle("1 of 10");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(Constants.LOADER_ID_WORD_LIST, null, this).forceLoad();
    }

    @Override
    protected Loader<List<Word>> createLoader(String folder) {
        return new WordListLoader(this, folder, lessonId);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> data) {
        words = data;
    }

    public List<Word> getWords() {
        return words;
    }

    public void start() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new EnterReadingGameFragment();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
    }
}
