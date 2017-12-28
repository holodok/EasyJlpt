package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.Task;
import com.gogaworm.easyjlpt.ui.widgets.EnterKanaView;
import com.gogaworm.easyjlpt.ui.widgets.KeyboardView;

import java.io.IOException;

/**
 * Created on 04.04.2017.
 *
 * @author ikarpova
 */
public class EnterReadingGameFragment extends WordGameFragment {
    private TextView questionView;
    private TextView japaneseView;
    private EnterKanaView readingView;
    private Word word;
    private KeyboardView keyboardView;
    private boolean answerShown;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_enter_reading, container, false);
        questionView = (TextView) parentView.findViewById(R.id.question);
        japaneseView = (TextView) parentView.findViewById(R.id.japanese);
        readingView = (EnterKanaView) parentView.findViewById(R.id.reading);
        keyboardView = (KeyboardView) parentView.findViewById(R.id.keyboard);
        keyboardView.setOnKeyPressedListener(readingView);

        try {
            readingView.init();
        } catch (IOException ignore) { //should never happen
            ignore.printStackTrace();
        }

        parentView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answerShown) {
                    answerShown = !answerShown;
                    onUserAnswer(word.reading.equals(readingView.getText()));
                } else {
                    gotoNext();
                }
            }
        });
        return parentView;
    }

    @Override
    protected void setupTask(Task task) {
        word = (Word) task.value;
        questionView.setText(R.string.label_can_you_read);
        japaneseView.setText(word.japanese);
        readingView.setExpectedWord(word.reading);
    }

    @Override
    protected void showAnswer(boolean correct) {
        readingView.showDifference();
        keyboardView.setVisibility(View.INVISIBLE);
    }
}
