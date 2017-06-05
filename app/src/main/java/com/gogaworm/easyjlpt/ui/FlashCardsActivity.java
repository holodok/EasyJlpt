package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.Task;
import com.gogaworm.easyjlpt.game.TaskCreator;
import com.gogaworm.easyjlpt.game.WordTask;
import com.gogaworm.easyjlpt.loaders.WordListLoader;
import com.gogaworm.easyjlpt.utils.Constants;

import java.util.Collections;
import java.util.List;

/**
 * Created on 24.05.2017.
 *
 * @author ikarpova
 */
public class FlashCardsActivity extends UserSessionLoaderActivity<Word> {
    private int lessonId;

    private Toolbar toolbar;
    private TextView questionView;
    private TextView kanjiView;
    private TextView readingView;
    private TextView translationView;
    private Button yesButton;
    private Button noButton;
    private GestureDetectorCompat detector;

    private int currentWordIndex;
    private List<Task> tasks;
    private boolean taskNotAnswered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lessonId = getIntent().getIntExtra("lessonId", 0);

        ActionBar actionBar = getSupportActionBar();

        View contentPanel = LayoutInflater.from(this).inflate(R.layout.flash_card, (ViewGroup) findViewById(R.id.content), true);

        questionView = (TextView) findViewById(R.id.question);
        kanjiView = (TextView) findViewById(R.id.japanese);
        readingView = (TextView) findViewById(R.id.reading);
        translationView = (TextView) findViewById(R.id.translation);
        yesButton = (Button) findViewById(R.id.yeButton);
        noButton = (Button) findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskNotAnswered) {
                    showAnswer();
                } else {
                    showTask(); //todo: move to next
                }
                taskNotAnswered = !taskNotAnswered;
            }
        });

        detector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float delta = e1.getX() - e2.getX();
                if (delta > 100) {
                    boolean moveForward = delta > 0f;
                    currentWordIndex += moveForward ? 1 : -1;
                    //initCard();
                    return true;
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                //showCard();
                return true;
            }
        });

        getSupportLoaderManager().initLoader(Constants.LOADER_ID_WORD_LIST, null, this).forceLoad();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected Loader<List<Word>> createLoader(String folder) {
        return new WordListLoader(this, folder, lessonId);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> data) {
        TaskCreator taskCreator = new TaskCreator();
        taskCreator.addWords(data);
        this.tasks = taskCreator.generateLearnSession();
        //randomize tasks
        Collections.shuffle(this.tasks);
        currentWordIndex = 0;

        //show first card
        showTask();
    }

    private void showTask() {
        WordTask task = (WordTask) tasks.get(currentWordIndex);

        kanjiView.setText(task.value.japanese);
        readingView.setVisibility(View.GONE);
        translationView.setVisibility(View.GONE);
        noButton.setVisibility(View.GONE);
    }

    private void showAnswer() {
        WordTask task = (WordTask) tasks.get(currentWordIndex);

        kanjiView.setText(task.value.japanese);
        readingView.setText(task.value.reading);
        translationView.setText(task.value.translation);

        readingView.setVisibility(View.VISIBLE);
        translationView.setVisibility(View.VISIBLE);
        noButton.setVisibility(View.VISIBLE);
    }

    private void moveToCard(boolean next) {
        currentWordIndex += next ? 1 : -1;
        initCard();
    }

    private void initCard() {
        loadWord(true, true, false);
    }

    private void showCard() {
        loadWord(true, true, true);
    }

    private void loadWord(boolean showKanji, boolean showReading, boolean showTranslation) {
/*
        previousButton.setVisibility(currentWordIndex > 0 ? View.VISIBLE : View.GONE);
        nextButton.setVisibility(currentWordIndex < tasks.size() - 1 ? View.VISIBLE : View.GONE);
*/

        if (currentWordIndex >= 0 || currentWordIndex < tasks.size()) {
            Word word = (Word) tasks.get(currentWordIndex).value;
            kanjiView.setText(showKanji ? word.japanese : "");
            readingView.setText(showReading ? word.reading : "");
            translationView.setText(showTranslation ? word.translation : "");
            //toolbar.setTitle(getString(R.string.flash_cards_title, (currentWordIndex + 1), tasks.size()));
        } else {
            finish(); //todo: ask to start from the beginning
        }
    }
}
