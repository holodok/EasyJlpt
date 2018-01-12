package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.GameController;
import com.gogaworm.easyjlpt.game.Task;
import com.gogaworm.easyjlpt.game.TaskCreator;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;

import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_WORD_LIST;

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
    private ActionBar actionBar;

    private void updateLeftStudyCount(int count) {
        if (count > 0) {
            actionBar.setTitle(getString(R.string.title_flash_cards, count));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        lesson = getIntent().getParcelableExtra("lesson");
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        gameController = new GameController();
        gameController.setOnGameStateChangedListener(this);
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_WORD_LIST;
    }

    @Override
    protected Loader<List<Word>> createLoader(Bundle args) {
        args.putInt("lessonId", lesson.trainId);
        return LoaderFactory.getLearnListLoader(this, args);
    }

    @Override
    protected void initData(List<Word> data) {
        words = data;
        TaskCreator taskCreator = new TaskCreator();
        taskCreator.addWords(data);
        //gameController.setTasks(taskCreator.generateLearnSession());
    }

    @Override
    protected void showFragment() {
        gameController.startGame();
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
                fragment = new FlashCardFragment();
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
        finish();
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void onUserAnswer(boolean correct) {
        gameController.onUserAnswer(correct);
    }
}
