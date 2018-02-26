package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Kanji;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.Task;

import static com.gogaworm.easyjlpt.utils.KanjiUtils.getReading;

/**
 * Created on 02.05.2017.
 *
 * @author ikarpova
 */
public class FlashCardFragment extends WordGameFragment {
    private TextView questionView;
    private TextView japaneseView;
    private TextView readingView;
    private TextView translationView;

    private Word word;
    private boolean showAnswer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_flash_card, container, false);
        questionView = parentView.findViewById(R.id.question);
        japaneseView = parentView.findViewById(R.id.japanese);
        readingView = parentView.findViewById(R.id.reading);
        translationView = parentView.findViewById(R.id.translation);

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
        questionView.setText(word instanceof Kanji ? R.string.label_do_you_know_kanji : R.string.label_do_you_know_word);
        word = (Word) task.value;
        japaneseView.setText(word.japanese);
        readingView.setText(getReading(getContext(), word), TextView.BufferType.SPANNABLE);
    }

    protected void onUserAnswer(boolean correct) {
        if (!showAnswer) {
            showAnswer = true;
            super.onUserAnswer(correct);
        } else {
            //then go to next
            gotoNext();
        }
    }

    @Override
    protected void showAnswer(boolean correct) {
        translationView.setText(word.translation);
    }
}
