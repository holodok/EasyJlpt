package com.gogaworm.easyjlpt.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Word;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_enter_reading, container, false);
        questionView = (TextView) parentView.findViewById(R.id.question);
        japaneseView = (TextView) parentView.findViewById(R.id.japanese);
        readingView = (EnterKanaView) parentView.findViewById(R.id.reading);
        KeyboardView keyboardView = (KeyboardView) parentView.findViewById(R.id.keyboard);
        keyboardView.setOnKeyPressedListener(readingView);

        try {
            readingView.init();
        } catch (IOException ignore) { //should never happen
            ignore.printStackTrace();
        }
/*
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }
        }.execute();
*/
        return parentView;
    }

    @Override
    protected void setupTask(Word word) {
        questionView.setText(R.string.label_can_you_read);
        japaneseView.setText(word.japanese);
        readingView.setMaxLength(word.reading.length());
    }
}
