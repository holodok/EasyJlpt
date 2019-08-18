package com.gogaworm.easyjlpt.ui.gamefragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.games.SelectVariantGameTemplate;
import com.gogaworm.easyjlpt.games.WordTypeReadingByJapaneseGameTemplate;
import com.gogaworm.easyjlpt.games.WordTypeReadingByTranslationGameTemplate;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;
import com.gogaworm.easyjlpt.viewmodel.LearnWordsViewModel;

public class WordTypeGameFragment extends GameFragment<Word> {
    private TextView japaneseView;
    private TextView readingView;
    private View separatorView;
    private TextView translationView;

    private TextInputLayout inputAnswerLayoutView;
    private TextInputEditText inputAnswerView;

    private View submitButton;

    private SelectVariantGameTemplate.Game<Word> game;
    private Word word;
    private ColorStateList defaultBackgroundTintList;
    private ColorStateList defaultTextColors;
    private String answerText;

    private boolean isAnswered;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_type_game, container, false);
        japaneseView = parentView.findViewById(R.id.japanese);
        readingView = parentView.findViewById(R.id.reading);
        separatorView = parentView.findViewById(R.id.divider);
        translationView = parentView.findViewById(R.id.translation);

        inputAnswerLayoutView = parentView.findViewById(R.id.inputAnswerLayoutView);
        inputAnswerView = parentView.findViewById(R.id.inputAnswerView);
        inputAnswerView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                inputAnswerLayoutView.setHint(getString(R.string.inputAnswerHint, answerText.length() - editable.length()));
            }
        });

        inputAnswerView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    // Your action on done
                    if (isAnswered) {
                        onUserAnswered();
                    }
                    isAnswered = true;
                    hideKeyboard(inputAnswerView.getContext(), inputAnswerView);
                    return true;
                }
                return false;
            }
        });

/*
        defaultBackgroundTintList = ViewCompat.getBackgroundTintList(answerButtons[0]);
        defaultTextColors = answerButtons[0].getTextColors();
*/

        submitButton = parentView.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUserAnswered();
            }
        });

        return parentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GameTemplate.Game<Word> game = viewModel.getCurrentGame().getValue();
        if (game != null && game.getGame() instanceof WordTypeReadingByTranslationGameTemplate ||
                game.getGame() instanceof WordTypeReadingByJapaneseGameTemplate) {
            this.game = game;
            word = game.getItem();
            answerText = game.getVariants()[0].variantText;
            setupGame();
        }
    }

    @Override
    protected Class<? extends GenericGamesLearnWordsViewModel<Word>> getViewModelClass() {
        return LearnWordsViewModel.class;
    }

    private void setupGame() {
        japaneseView.setText("");
        readingView.setText("");
        translationView.setText("");
        separatorView.setVisibility(View.INVISIBLE);
        inputAnswerView.setText("");
        inputAnswerView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(answerText.length())});
        inputAnswerLayoutView.setHint(getString(R.string.inputAnswerHint, answerText.length()));

        japaneseView.setText(game.getTaskText());
        isAnswered = false;
    }

    private void onUserAnswered() {
        japaneseView.setText(word.japanese);
        readingView.setText(word.reading, TextView.BufferType.SPANNABLE);
        translationView.setText(word.translation);
        separatorView.setVisibility(View.VISIBLE);

        inputAnswerView.setKeyListener(null);
        inputAnswerView.setCursorVisible(false);
        final boolean correct = normalize(inputAnswerView.getText().toString()).equals(normalize(answerText));
        int answerColor = correct ? R.color.correctAnswer : R.color.incorrectAnswer;
        inputAnswerLayoutView.setBoxStrokeColor(ContextCompat.getColor(getContext(), answerColor));
        inputAnswerLayoutView.setDefaultHintTextColor(ContextCompat.getColorStateList(getContext(), answerColor));
        inputAnswerLayoutView.setHint(getString(correct ? R.string.inputAnswerHintCorrect : R.string.inputAnswerHintWrong));

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.gameFinished(correct);
            }
        });
    }

    private static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private String normalize(String text) {
        return text.replaceAll("[0-9～~]", "#");
    }
}
