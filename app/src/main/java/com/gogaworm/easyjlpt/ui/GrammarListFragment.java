package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.data.UserSession;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class GrammarListFragment extends UserSessionFragment  {
    private Grammar grammar;

    static GrammarListFragment getInstance(UserSession userSession, Grammar grammar) {
        GrammarListFragment fragment = new GrammarListFragment();
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
            grammarFormsView.setText(convertStringArrayToString(grammar.meanings[i].forms));
        }

        TextView grammarNotesView = (TextView) parentView.findViewById(R.id.grammarNotes);
        grammarNotesView.setText(convertStringArrayToString(grammar.notes));

        ViewGroup sentenceContent = (ViewGroup) parentView.findViewById(R.id.sentence);
        for (int i = 0; i < grammar.sentences.length; i++) {
            View sentenceView = inflater.inflate(R.layout.list_item_sentence, sentenceContent, false);
            TextView japaneseSentenceView = (TextView) sentenceView.findViewById(R.id.japanese);
            japaneseSentenceView.setText(grammar.sentences[i].japanese);
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
}
