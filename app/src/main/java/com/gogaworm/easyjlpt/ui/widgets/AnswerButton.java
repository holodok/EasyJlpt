package com.gogaworm.easyjlpt.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public class AnswerButton extends FrameLayout {
    private KanjiKanaView headerView;
    private TextView subHeaderView;
    private View parentView;

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
        headerView = (KanjiKanaView) findViewById(R.id.answerJapanese);
        subHeaderView = (TextView) findViewById(R.id.answerReading);
    }

    public void setJapanese(String japanese, String reading) {
        headerView.setText(japanese, reading);
        headerView.setVisibility(VISIBLE);
    }

    public void setTranslation(String translation) {
        subHeaderView.setText(translation);
        subHeaderView.setVisibility(VISIBLE);
    }

    public void reset() {
        headerView.setText("");
        subHeaderView.setText("");
        headerView.setVisibility(GONE);
        subHeaderView.setVisibility(GONE);
    }
}
