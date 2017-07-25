package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.GameController;
import com.gogaworm.easyjlpt.game.Task;
import com.gogaworm.easyjlpt.game.TaskCreator;
import com.gogaworm.easyjlpt.loaders.WordListLoader;
import com.gogaworm.easyjlpt.utils.Constants;

import java.util.List;

/**
 * Created on 29.03.2017.
 *
 * @author ikarpova
 */
public class LearnLessonActivity extends UserSessionLoaderActivity<Word> implements GameController.OnGameStateChangedListener {
    private Lesson lesson;
    private List<Word> words;
    private GameController gameController;
    private Task currentTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lesson = getIntent().getParcelableExtra("lesson");

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new StartLessonFragment();
        fragmentManager.beginTransaction().add(R.id.content, fragment).commit();

        getSupportActionBar().setTitle("1 of 10");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportLoaderManager().initLoader(Constants.LOADER_ID_WORD_LIST, null, this).forceLoad();

        gameController = new GameController();
        gameController.setOnGameStateChangedListener(this);
    }

    @Override
    protected Loader<List<Word>> createLoader(String folder) {
        return new WordListLoader(this, folder, lesson.trainId);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> data) {
        words = data;
        TaskCreator taskCreator = new TaskCreator();
        taskCreator.addWords(data);
        gameController.setTasks(taskCreator.generateLearnSession());
    }

    public List<Word> getWords() {
        return words;
    }

    public void start() {
        gameController.startGame();
    }

    @Override
    public void onNextTask(Task task) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        switch (task.gameType) {
            case FLASH_CARD:
                //fragment = new FlashCardFragment();
                fragment = new EnterReadingGameFragment();
                break;
            case SELECT_TRANSLATION_BY_READING:
                fragment = new SelectWordGameFragment();
                break;
            case SELECT_TRANSLATION_BY_KANJI:
                fragment = new SelectWordGameFragment();
                break;
            case SELECT_READING_BY_KANJI:
                fragment = new SelectWordGameFragment();
                break;
            case SELECT_KANJI_BY_READING:
                fragment = new SelectWordGameFragment();
                break;
            case SELECT_KANJI_BY_TRANSLATION:
                fragment = new SelectWordGameFragment();
                break;
            case WRITE_READING:
                fragment = new EnterReadingGameFragment();
                break;
            case MULTYSELECT_KANJI_READING:
                fragment = new EnterReadingGameFragment();
                break;
            case WRITE_KANJI_IN_KANJI:
                fragment = new EnterReadingGameFragment();
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
        currentTask = task;
    }

    @Override
    public void onGameOver() {

    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void onUserAnswer(boolean correct) {
        gameController.onUserAnswer(correct);
    }
}
