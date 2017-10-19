package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.utils.UnitedKanjiKanaSpannableString;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class GrammarItemFragment extends UserSessionFragment  {
    private Grammar grammar;

    static GrammarItemFragment getInstance(UserSession userSession, Grammar grammar) {
        GrammarItemFragment fragment = new GrammarItemFragment();
        setArguments(fragment, userSession);
        fragment.grammar = grammar;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.list_item_grammar, container, false);
        ViewGroup grammarContent = (ViewGroup) parentView.findViewById(R.id.grammarContent);

        for (int i = 0; i < grammar.meanings.length; i++) {
            View meaning = inflater.inflate(R.layout.list_item_meaning, grammarContent, false);
            grammarContent.addView(meaning, 0);
            TextView grammarItemView = (TextView) meaning.findViewById(R.id.grammarItem);
            grammarItemView.setText(grammar.item);
            TextView meaningTranslationView = (TextView) meaning.findViewById(R.id.meaningTranslation);
            meaningTranslationView.setText(grammar.meanings[i].meaning.translation);
            TextView meaningJapaneseView = (TextView) meaning.findViewById(R.id.meaningJapanese);
            meaningJapaneseView.setText(grammar.meanings[i].meaning.japanese);

            TextView grammarFormsView = (TextView) meaning.findViewById(R.id.grammarForms);
            grammarFormsView.setText(getGrammarFormsSpannable(grammar.meanings[i].forms));
        }

        TextView grammarNotesView = (TextView) parentView.findViewById(R.id.grammarNotes);
        parentView.findViewById(R.id.notes).setVisibility(grammar.notes.length != 0 ? VISIBLE : GONE);
        grammarNotesView.setText(convertStringArrayToString(grammar.notes));

        ViewGroup sentenceContent = (ViewGroup) parentView.findViewById(R.id.sentence);
        for (int i = 0; i < grammar.sentences.length; i++) {
            View sentenceView = inflater.inflate(R.layout.list_item_sentence, sentenceContent, false);
            TextView japaneseSentenceView = (TextView) sentenceView.findViewById(R.id.japanese);
            japaneseSentenceView.setText(new UnitedKanjiKanaSpannableString(grammar.sentences[i].japanese));
            TextView translationSentenceView = (TextView) sentenceView.findViewById(R.id.translation);
            translationSentenceView.setText(grammar.sentences[i].translation);
            sentenceContent.addView(sentenceView);
        }

        return parentView;
    }

    private String convertStringArrayToString(String[] strings) {
        StringBuilder value = new StringBuilder();
        if (strings != null) {
            for (int i = 0; i < strings.length; i++) {
                value.append(strings[i]);
                if (i < strings.length - 1) {
                    value.append('\n');
                }
            }
        }
        return value.toString();
    }

    private Spannable getGrammarFormsSpannable(String[] grammarForms) {
        SpannableString spannableString = new SpannableString(convertStringArrayToString(grammarForms));
        int startMinus = -1;
        int startItem = -1;
        for (int i = 0; i < spannableString.length(); i++) {
            char ch = spannableString.charAt(i);
            if (ch == '形' || ch == '動' || ch == '名') {
                //display grayed
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.secondaryText)), i, i + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            } else if (ch == '-' || ch == '—') {
                startMinus = i;
            } else if (ch == '+' || ch == '＋') {
                startItem = i;
                if (startMinus >=0) {
                    spannableString.setSpan(new StrikethroughSpan(), startMinus + 1, i, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    startMinus = -1;
                }
            } else if (ch == '\n' && startItem >=0) {
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), startItem + 1, i, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                startItem = -1;
            }
        }
        if (startItem >=0) {
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), startItem + 1, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }
}
