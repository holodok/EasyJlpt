package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.ui.widgets.AnswerButton;
import com.gogaworm.easyjlpt.ui.widgets.KanjiKanaView;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public class SelectWordGameFragment extends WordGameFragment {
    private TextView questionView;
    private KanjiKanaView japaneseView;
    private View separatorView;
    private TextView translationView;
    private AnswerButton[] answerButtons;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_select_word_game, container, false);
        questionView = (TextView) parentView.findViewById(R.id.question);
        japaneseView = (KanjiKanaView) parentView.findViewById(R.id.japanese);
        separatorView = parentView.findViewById(R.id.separator);
        translationView = (TextView) parentView.findViewById(R.id.translation);

        answerButtons = new AnswerButton[] {
                (AnswerButton) parentView.findViewById(R.id.firstAnswer),
                (AnswerButton) parentView.findViewById(R.id.secondAnswer),
                (AnswerButton) parentView.findViewById(R.id.thirdAnswer),
                (AnswerButton) parentView.findViewById(R.id.forthAnswer)
        };

        return parentView;
    }

    @Override
    protected void setupTask(Word word) {
        questionView.setText(R.string.label_can_you_read);
        japaneseView.setText(word.japanese, word.reading);
        translationView.setText(word.translation);

        for (AnswerButton answerButton : answerButtons) {
            answerButton.setJapanese(word.japanese, word.reading);
            answerButton.setTranslation(word.translation);
        }
    }
}
