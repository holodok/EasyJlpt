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
import com.gogaworm.easyjlpt.game.GameController;
import com.gogaworm.easyjlpt.game.Task;
import com.gogaworm.easyjlpt.game.WordTask;
import com.gogaworm.easyjlpt.ui.widgets.AnswerButton;
import com.gogaworm.easyjlpt.ui.widgets.KanjiKanaView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    private View buttonSeparator;
    private AnswerButton[] answerButtons;

    private GameController.GameType gameType;
    private Word[] variants;
    private int correctIndex;
    private int userSelectedIndex;
    private Word word;
    private View nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_select_word_game, container, false);
        questionView = (TextView) parentView.findViewById(R.id.question);
        japaneseView = (KanjiKanaView) parentView.findViewById(R.id.japanese);
        separatorView = parentView.findViewById(R.id.separator);
        translationView = (TextView) parentView.findViewById(R.id.translation);
        buttonSeparator = parentView.findViewById(R.id.buttonSeparator);

        answerButtons = new AnswerButton[] {
                (AnswerButton) parentView.findViewById(R.id.firstAnswer),
                (AnswerButton) parentView.findViewById(R.id.secondAnswer),
                (AnswerButton) parentView.findViewById(R.id.thirdAnswer),
                (AnswerButton) parentView.findViewById(R.id.forthAnswer)
        };
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.firstAnswer:
                        userSelectedIndex = 0;
                        break;
                    case R.id.secondAnswer:
                        userSelectedIndex = 1;
                        break;
                    case R.id.thirdAnswer:
                        userSelectedIndex = 2;
                        break;
                    case R.id.forthAnswer:
                        userSelectedIndex = 3;
                        break;
                    default:
                        return;
                }
                onUserAnswer(userSelectedIndex == correctIndex); //todo: check answer
                nextButton.setVisibility(VISIBLE);
            }
        };
        for (AnswerButton button : answerButtons) {
            button.setOnClickListener(listener);
        }
        variants = new Word[answerButtons.length];

        separatorView.setVisibility(GONE);
        translationView.setVisibility(GONE);

        nextButton = parentView.findViewById(R.id.submitButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNext();
            }
        });
        nextButton.setVisibility(GONE);

        return parentView;
    }

    @Override
    protected void setupTask(Task task) {
        word = ((WordTask) task).value;

        switch (task.gameType) {
            case SELECT_TRANSLATION_BY_READING:
                questionView.setText(R.string.label_can_you_translate);
                japaneseView.setText(word.hasKanji() ? word.reading : word.japanese);
                new TranslationVariantsGenerator().generateVariants(word);
                break;
            case SELECT_TRANSLATION_BY_KANJI:
                questionView.setText(R.string.label_can_you_translate);
                japaneseView.setText(word.japanese);
                new TranslationVariantsGenerator().generateVariants(word);
                break;
            case SELECT_READING_BY_KANJI:
                questionView.setText(R.string.label_can_you_read);
                japaneseView.setText(word.japanese);
                new ReadingVariantsGenerator().generateVariants(word);
                break;
            case SELECT_KANJI_BY_READING:
                questionView.setText(R.string.label_do_you_know);
                japaneseView.setText(word.reading);
                new KanjiVariantsGenerator().generateVariants(word);
                break;
            case SELECT_KANJI_BY_TRANSLATION:
                questionView.setText(R.string.label_do_you_know);
                translationView.setText(word.translation);
                new KanjiVariantsGenerator().generateVariants(word);
                break;
        }
        gameType = task.gameType;
    }

    @Override
    protected void showAnswer(boolean correct) {
        //todo
        //hide all but clicked and correct
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setVisibility(i == correctIndex || i == userSelectedIndex ? VISIBLE : GONE);
            if (i == correctIndex) {
                answerButtons[i].highlightCorrect(true);
            } else if (i == userSelectedIndex) {
                answerButtons[i].highlightCorrect(false);
            }
        }
        buttonSeparator.setVisibility(GONE);

        //mark correct and incorrect

        switch (gameType) {
            case SELECT_TRANSLATION_BY_READING:
                if (word instanceof Kanji) {
                    japaneseView.setText(word.reading);
                    break;
                }
            case SELECT_TRANSLATION_BY_KANJI:
                if (word instanceof Kanji) {
                    japaneseView.setText(word.japanese);
                } else {
                    japaneseView.setText(word.japanese, word.reading);
                }
                break;
            case SELECT_READING_BY_KANJI:
                translationView.setVisibility(VISIBLE);
                translationView.setText(word.translation);
                break;
        }
    }

    private abstract class VariantsGenerator {
        void generateVariants(Word taskWord) {
            List<Word> words = getWords();
            Collections.shuffle(words);

            for (int i = 0, variantIndex = 0; i < words.size() && variantIndex < variants.length; i++) {
                Word word = words.get(i);
                if (taskWord.getClass().equals(word.getClass()) && !equals(taskWord, word) && Arrays.binarySearch(variants, word, getComparator()) < 0) { //not found
                    variants[variantIndex++] = word;
                }
            }

            correctIndex = (int) (Math.random() * variants.length);
            variants[correctIndex] = taskWord;

            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].reset();
                answerButtons[i].setupBackground(i == correctIndex);
                setupButton(answerButtons[i], variants[i]);
            }
        }

        abstract boolean equals(Word word1, Word word2);
        abstract Comparator<Word> getComparator();
        abstract void setupButton(AnswerButton button, Word word);
    }

    private class TranslationVariantsGenerator extends VariantsGenerator {
        private Comparator<Word> comparator;

        @Override
        boolean equals(Word word1, Word word2) {
            return word1.translation.equalsIgnoreCase(word2.translation);
        }

        @Override
        Comparator<Word> getComparator() {
            if (comparator == null) {
                comparator = new Comparator<Word>() {
                    @Override
                    public int compare(Word o1, Word o2) {
                        return o1 == null ? (null == o2 ? 0 : -1) : o2 == null ? 1 : o1.translation.compareTo(o2.translation);
                    }
                };
            }
            return comparator;
        }

        @Override
        void setupButton(AnswerButton button, Word word) {
            button.setTranslation(word.translation);
        }
    }

    private class ReadingVariantsGenerator extends VariantsGenerator {
        private Comparator<Word> comparator;

        @Override
        boolean equals(Word word1, Word word2) {
            return word1.reading.equalsIgnoreCase(word2.reading);
        }

        @Override
        Comparator<Word> getComparator() {
            if (comparator == null) {
                comparator = new Comparator<Word>() {
                    @Override
                    public int compare(Word o1, Word o2) {
                        return o1 == null ? (null == o2 ? 0 : -1) : o2 == null ? 1 : o1.reading.compareTo(o2.reading);
                    }
                };
            }
            return comparator;
        }

        @Override
        void setupButton(AnswerButton button, Word word) {
            button.setJapanese(word.hasKanji() ? word.reading : word.japanese, "");
        }
    }

    private class KanjiVariantsGenerator extends VariantsGenerator {
        private Comparator<Word> comparator;

        @Override
        boolean equals(Word word1, Word word2) {
            return word1.japanese.equalsIgnoreCase(word2.japanese);
        }

        @Override
        Comparator<Word> getComparator() {
            if (comparator == null) {
                comparator = new Comparator<Word>() {
                    @Override
                    public int compare(Word o1, Word o2) {
                        return o1 == null ? (null == o2 ? 0 : -1) : o2 == null ? 1 : o1.japanese.compareTo(o2.japanese);
                    }
                };
            }
            return comparator;
        }

        @Override
        void setupButton(AnswerButton button, Word word) {
            button.setJapanese(word.japanese, "");
        }
    }
}
