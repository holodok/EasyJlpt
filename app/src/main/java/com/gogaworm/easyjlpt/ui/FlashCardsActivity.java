package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;
import com.gogaworm.easyjlpt.utils.UnitedKanjiKanaSpannableString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_WORD_LIST;

/**
 * Created on 24.05.2017.
 *
 * @author ikarpova
 */
public class FlashCardsActivity extends UserSessionLoaderActivity<Word> {
    private Lesson lesson;

    private TextView questionView;
    private TextView kanjiView;
    private TextView readingView;
    private TextView translationView;
    private Button yesButton;
    private Button noButton;

    private boolean taskNotAnswered;

    private SimpleGameController simpleGameController;
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lesson = getIntent().getParcelableExtra("lesson");
        super.onCreate(savedInstanceState);

        LayoutInflater.from(this).inflate(R.layout.flash_card, (ViewGroup) findViewById(R.id.content), true);

        questionView = (TextView) findViewById(R.id.question);
        kanjiView = (TextView) findViewById(R.id.japanese);
        readingView = (TextView) findViewById(R.id.reading);
        translationView = (TextView) findViewById(R.id.translation);
        yesButton = (Button) findViewById(R.id.yeButton);
        noButton = (Button) findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserAnswer(true);
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserAnswer(false);
            }
        });

        setOnBackPressedText(getString(R.string.messageLeaveFlashCards));
        simpleGameController = new SimpleGameController();
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_WORD_LIST;
    }

    private void onUserAnswer(boolean correct) {
        if (taskNotAnswered) {
            showAnswer();
        } else {
            simpleGameController.onAnswered(word, correct);
            showTask(); //todo: move to next
        }
        taskNotAnswered = !taskNotAnswered;
    }

    @Override
    protected Loader<List<Word>> createLoader(Bundle args) {
        args.putInt("lessonId", lesson.trainId);
        return LoaderFactory.getLearnListLoader(this, args);
    }

    @Override
    protected void initData(List<Word> data) {
        simpleGameController.init(data);
        taskNotAnswered = true;
    }

    @Override
    protected void showFragment() {
        showTask();
    }

    private void showTask() {
        word = simpleGameController.getNextWord();
        if (word == null) {
            finish();
        } else {
            questionView.setText(R.string.label_do_you_know_word);
            if (word.isNewKanjiMode()) {
                kanjiView.setText(new UnitedKanjiKanaSpannableString(word.japanese));
            } else {
                kanjiView.setText(word.japanese);
            }

            readingView.setVisibility(View.GONE);
            translationView.setVisibility(View.GONE);
            yesButton.setText(R.string.button_check);
            noButton.setVisibility(View.GONE);
        }
    }

    private void showAnswer() {
        questionView.setText(R.string.label_is_it_correct);

        if (word.hasKanji()) {
            readingView.setText(word.reading);
            readingView.setVisibility(View.VISIBLE);
        }

        translationView.setText(word.translation);
        translationView.setVisibility(View.VISIBLE);
        yesButton.setText(R.string.button_yes);
        noButton.setVisibility(View.VISIBLE);
    }

    class SimpleGameController {
        List<FlashCardTask> tasks;
        int index;
        Random random = new Random(System.currentTimeMillis());

        void init(List<Word> words) {
            tasks = new ArrayList<>();
            for (Word word : words) {
                tasks.add(new FlashCardTask(word));
            }
            Collections.shuffle(this.tasks);
            index = 0;
            updateLeftCount(tasks.size());
        }

        Word getNextWord() {
            return index == this.tasks.size() ? null : tasks.get(index).word;
        }

        void onAnswered(Word word, boolean correct) {
            // put as last item and add last check time to prevent repeating one card twice
            FlashCardTask task = tasks.get(index);
            if (!correct || task.wasWrong) {
                int nextIndexMin = !correct ? 3 : 6;
                int insertIndex = Math.min(tasks.size(), index + random.nextInt(nextIndexMin) + 2);
                this.tasks.add(insertIndex, task);
            }
            task.wasWrong = !correct;
            index++;
            updateLeftCount(tasks.size() - index);
        }
    }

    class FlashCardTask {
        Word word;
        boolean wasWrong;

        FlashCardTask(Word word) {
            this.word = word;
        }
    }
}
