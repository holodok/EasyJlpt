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
import com.gogaworm.easyjlpt.game.WordTask;
import com.gogaworm.easyjlpt.ui.widgets.AnswerButton;
import com.gogaworm.easyjlpt.ui.widgets.KanjiKanaView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private Word[] variants;

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
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserAnswer(true); //todo: check answer
            }
        };
        for (AnswerButton button : answerButtons) {
            button.setOnClickListener(listener);
        }
        variants = new Word[answerButtons.length];

        return parentView;
    }

    @Override
    protected void setupTask(Task task) {
        Word word = ((WordTask) task).value;

        switch (task.gameType) {
            case SELECT_TRANSLATION_BY_READING:
                questionView.setText(R.string.label_can_you_translate);
                japaneseView.setText(word.reading);

                new TranslationVariantsGenerator().generateVariants(word);
                for (int i = 0; i < answerButtons.length; i++) {
                    answerButtons[i].reset();
                    answerButtons[i].setTranslation(variants[i].translation);
                }
                break;
            case SELECT_TRANSLATION_BY_KANJI:
                questionView.setText(R.string.label_can_you_translate);
                japaneseView.setText(word.japanese);

                new TranslationVariantsGenerator().generateVariants(word);
                for (int i = 0; i < answerButtons.length; i++) {
                    answerButtons[i].reset();
                    answerButtons[i].setTranslation(variants[i].translation);
                }
                break;
            case SELECT_READING_BY_KANJI:
                questionView.setText(R.string.label_can_you_read);
                japaneseView.setText(word.japanese);

                new ReadingVariantsGenerator().generateVariants(word);
                for (int i = 0; i < answerButtons.length; i++) {
                    answerButtons[i].reset();
                    answerButtons[i].setJapanese("", variants[i].reading);
                }
                break;
            case SELECT_KANJI_BY_READING:
                questionView.setText(R.string.label_do_you_know);
                japaneseView.setText(word.reading);

                new ReadingVariantsGenerator().generateVariants(word);
                for (int i = 0; i < answerButtons.length; i++) {
                    answerButtons[i].reset();
                    answerButtons[i].setJapanese(variants[i].japanese, "");
                }
                break;
            case SELECT_KANJI_BY_TRANSLATION:
                questionView.setText(R.string.label_do_you_know);
                translationView.setText(word.translation);

                new KanjiVariantsGenerator().generateVariants(word);
                for (int i = 0; i < answerButtons.length; i++) {
                    answerButtons[i].reset();
                    answerButtons[i].setJapanese(variants[i].japanese, "");
                }
                break;
        }
    }

    @Override
    protected void showAnswer(boolean correct) {
        //todo
    }

    private abstract class VariantsGenerator {
        void generateVariants(Word taskWord) {
            List<Word> words = getWords();
            Collections.shuffle(words);

            for (int i = 0, variantIndex = 0; i < words.size() && variantIndex < variants.length; i++) {
                Word word = words.get(i);
                if (!equals(taskWord, word) && Arrays.binarySearch(variants, word, getComparator()) < 0) { //not found
                    variants[variantIndex++] = word;
                }
            }

            variants[(int) (Math.random() * variants.length)] = taskWord;
        }

        abstract boolean equals(Word word1, Word word2);
        abstract Comparator<Word> getComparator();
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
    }
}
