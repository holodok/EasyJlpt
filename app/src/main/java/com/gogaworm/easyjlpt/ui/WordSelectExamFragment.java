package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Exam;
import com.gogaworm.easyjlpt.ui.widgets.AnswerButton;
import com.gogaworm.easyjlpt.utils.UnitedKanjiKanaSpannableString;

/**
 * Created on 18.08.2017.
 *
 * @author ikarpova
 */
public class WordSelectExamFragment extends Fragment {
    private AnswerButton[] answerButtons;
    private TextView sentenceJapaneseView;
    private TextView sentenceTranslationView;
    private Exam exam;
    private boolean answered;
    private int answerIndex;

    private ExamListener examListener;
    private View submitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_exam_word_select, container, false);
        sentenceJapaneseView = (TextView) parentView.findViewById(R.id.sentenceJapanese);
        sentenceTranslationView = (TextView) parentView.findViewById(R.id.sentenceTranslation);

        answerButtons = new AnswerButton[] {
                (AnswerButton) parentView.findViewById(R.id.firstAnswer),
                (AnswerButton) parentView.findViewById(R.id.secondAnswer),
                (AnswerButton) parentView.findViewById(R.id.thirdAnswer),
                (AnswerButton) parentView.findViewById(R.id.forthAnswer)
        };
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(v.getId());
                submitButton.setEnabled(true);
            }
        };
        for (AnswerButton button : answerButtons) {
            button.setOnClickListener(listener);
        }

        makeButtonsVisible(0);

        submitButton = parentView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (examListener != null) {
                    examListener.nextSentence();
                }
            }
        });

        initExam(examListener.getCurrentExam());

        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void initExam(Exam exam) {
        this.exam = exam;
        answered = false;
        answerIndex = 0;
        sentenceJapaneseView.setText(new UnitedKanjiKanaSpannableString(exam.japanese, false));
        sentenceTranslationView.setVisibility(View.INVISIBLE);
        makeButtonsVisible(exam.answers.length);
        for (int i = 0; i < exam.answers.length; i++) {
            answerButtons[i].reset();
            answerButtons[i].setJapanese(exam.answers[i].japanese, "");
        }
        submitButton.setEnabled(false);
    }

    private void makeButtonsVisible(int count) {
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setVisibility(i < count ? View.VISIBLE : View.GONE);
        }
    }

    private void onAnswerSelected(int id) {
        //todo
        int buttonIndex = 0;
        switch (id) {
            case R.id.firstAnswer:
                buttonIndex = 1;
                break;
            case R.id.secondAnswer:
                buttonIndex = 2;
                break;
            case R.id.thirdAnswer:
                buttonIndex = 3;
                break;
            case R.id.forthAnswer:
                buttonIndex = 4;
                break;
        }
        if (answered) {
            //todo: show translation
        } else {
            boolean correctAnswer = exam.isAnswerCorrect(answerIndex, buttonIndex);
            sentenceJapaneseView.setText(replaceAnswer(sentenceJapaneseView.getText().toString(), exam.getCorrectAnswer(answerIndex).japanese));
            answerButtons[buttonIndex - 1].highlightCorrect(correctAnswer);

            if (exam.isLastAnswer(answerIndex) || !correctAnswer) {
                answered = true;
                sentenceTranslationView.setText(exam.translation);
                sentenceTranslationView.setVisibility(View.VISIBLE);
                sentenceJapaneseView.setText(new UnitedKanjiKanaSpannableString(sentenceJapaneseView.getText().toString()));
                //todo: show full answer in case of multianswers
            }
            answerIndex++;
        }
    }

    private CharSequence replaceAnswer(String japanese, String answer) {
        return new UnitedKanjiKanaSpannableString(japanese.replaceFirst("＿", answer), false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExamListener) {
            examListener = (ExamListener) context;
        }
    }

    @Override
    public void onDetach() {
        examListener = null;
        super.onDetach();
    }

    public interface ExamListener {
        Exam getCurrentExam();
        void nextSentence();
    }
}
