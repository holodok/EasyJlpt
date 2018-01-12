package com.gogaworm.easyjlpt.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.utils.KanjiKanaSpannableString;
import com.gogaworm.easyjlpt.utils.UnitedKanjiKanaSpannableString;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public class AnswerButton extends FrameLayout {
    private KanjiKanaView headerView;
    private TextView subHeaderView;
    private View parentView;

    private GameVariant gameVariant;

    public AnswerButton(Context context) {
        super(context);
        init(context);
    }

    public AnswerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnswerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_answer_button, this, true);
        parentView = findViewById(R.id.parentPanel);
        headerView = findViewById(R.id.answerJapanese);
        subHeaderView = findViewById(R.id.answerReading);
        setClickable(true);
    }

    public void setJapanese(CharSequence japanese, CharSequence reading) {
        if (isEmpty(reading) && isEmpty(japanese)) {
            headerView.setVisibility(GONE);
        } else {
            if (!isEmpty(reading) && !isEmpty(japanese)) {
                headerView.setText(new KanjiKanaSpannableString(japanese.toString(), reading.toString()));
            } else if (!isEmpty(reading)) {
                headerView.setText(reading);
            } else if (!isEmpty(japanese)) {
                headerView.setText(new UnitedKanjiKanaSpannableString(japanese));
            }
            headerView.setVisibility(VISIBLE);
        }
    }

    public void setTranslation(String translation) {
        if (isEmpty(translation)) {
            subHeaderView.setVisibility(GONE);
        } else {
            subHeaderView.setText(translation);
            subHeaderView.setVisibility(VISIBLE);
        }
    }

    public GameVariant getVariant() {
        return gameVariant;
    }

    public void reset() {
        headerView.setText("");
        subHeaderView.setText("");
        headerView.setVisibility(GONE);
        subHeaderView.setVisibility(GONE);
        changeBackground(R.drawable.correct_answer_button_selector);
    }

    public void highlightCorrect(boolean correct) {
        changeBackground(correct ? R.drawable.correct_answer_background : R.drawable.wrong_answer_background);
    }

    public void setupBackground(boolean correct) {
        changeBackground(correct ? R.drawable.correct_answer_button_selector : R.drawable.wrong_answer_button_selector);
    }

    private void changeBackground(int colorId) {
        int paddingTop = parentView.getPaddingTop();
        int paddingLeft = parentView.getPaddingLeft();
        int paddingRight = parentView.getPaddingRight();
        int paddingBottom = parentView.getPaddingBottom();
        parentView.setBackgroundResource(colorId);
        parentView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setVariant(GameVariant variant) {
        this.gameVariant = variant;
        setJapanese(gameVariant.getJapanese(), gameVariant.getReading());
        setTranslation(gameVariant.getTranslation());
    }
}
