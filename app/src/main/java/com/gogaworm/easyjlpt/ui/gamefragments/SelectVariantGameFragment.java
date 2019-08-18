package com.gogaworm.easyjlpt.ui.gamefragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.games.SelectVariantGameTemplate;

public abstract class SelectVariantGameFragment<D> extends GameFragment<D> {
    private TextView japaneseView;
    private TextView readingView;
    private View separatorView;
    private TextView translationView;
    private Button[] answerButtons;
    private View submitButton;

    private SelectVariantGameTemplate.Game<D> game;
    private ColorStateList defaultBackgroundTintList;
    private ColorStateList defaultTextColors;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(getLayoutId(), container, false);
        japaneseView = parentView.findViewById(R.id.japanese);
        readingView = parentView.findViewById(R.id.reading);
        separatorView = parentView.findViewById(R.id.divider);
        translationView = parentView.findViewById(R.id.translation);

        answerButtons = new Button[] {
                parentView.findViewById(R.id.firstAnswer),
                parentView.findViewById(R.id.secondAnswer),
                parentView.findViewById(R.id.thirdAnswer),
                parentView.findViewById(R.id.forthAnswer)
        };

        defaultBackgroundTintList = ViewCompat.getBackgroundTintList(answerButtons[0]);
        defaultTextColors = answerButtons[0].getTextColors();

        submitButton = parentView.findViewById(R.id.submitButton);

        return parentView;
    }

    protected int getLayoutId() {
        return R.layout.fragment_variant_select_game;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GameTemplate.Game<D> game = viewModel.getCurrentGame().getValue();
        if (game != null && game.getGame() instanceof SelectVariantGameTemplate) {
            this.game = game;
            datum = game.getItem();
            setupGame();
        }
    }

    protected void setupGame() {
        japaneseView.setText("");
        readingView.setText("");
        translationView.setText("");
        separatorView.setVisibility(View.INVISIBLE);
        submitButton.setEnabled(false);

        initTaskText(game, japaneseView);

        for (int i = 0; i < answerButtons.length; i++) {
            final Button button = answerButtons[i];
            final GameTemplate.AnswerVariant variant = game.getVariants()[i];

            ViewCompat.setBackgroundTintList(button, defaultBackgroundTintList);
            button.setTextColor(defaultTextColors);

            button.setText(variant.variantText);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onUserAnswered(button, variant.correct);
                }
            });
        }
    }

    private void onUserAnswered(View view, final boolean correct) {
        for (Button button : answerButtons) {
            ViewCompat.setBackgroundTintList(button, ContextCompat.getColorStateList(button.getContext(),
                    button == view ? (correct ? R.color.correctAnswer : R.color.incorrectAnswer) : R.color.divider));
            if (button == view) {
                button.setTextColor(ContextCompat.getColorStateList(button.getContext(), R.color.primaryTextInverted));
            }
            button.setEnabled(false);
        }

        showAnswer(datum, japaneseView, readingView, translationView);
        separatorView.setVisibility(View.VISIBLE);

        submitButton.setEnabled(true);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.gameFinished(correct);
            }
        });
    }

    protected void initTaskText(SelectVariantGameTemplate.Game<D> game, TextView japaneseView) {
        japaneseView.setText(game.getTaskText());
    }

    protected abstract void showAnswer(D datum, TextView japaneseView, TextView readingView, TextView translationView);
}
