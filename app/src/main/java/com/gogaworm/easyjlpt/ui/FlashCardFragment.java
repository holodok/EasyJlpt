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

/**
 * Created on 02.05.2017.
 *
 * @author ikarpova
 */
public class FlashCardFragment extends WordGameFragment {
    private TextView japaneseView;
    private TextView readingView;
    private TextView translationView;

    private Word word;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_flash_card, container, false);
        japaneseView = (TextView) parentView.findViewById(R.id.japanese);
        readingView = (TextView) parentView.findViewById(R.id.reading);
        translationView = (TextView) parentView.findViewById(R.id.translation);

        parentView.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserAnswer(true);
            }
        });
        return parentView;
    }

    @Override
    protected void setupTask(Task task) {
        word = (Word) task.value;
        japaneseView.setText(word.japanese);
        readingView.setText(!word.japanese.equals(word.reading) ? word.reading : "");
    }

    @Override
    protected void showAnswer(boolean correct) {
        translationView.setText(word.translation);
    }
}
