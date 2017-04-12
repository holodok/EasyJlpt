package com.gogaworm.easyjlpt.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;

/**
 * Created on 04.04.2017.
 *
 * @author ikarpova
 */
public class KeyboardView extends LinearLayout {
    private OnKeyPressedListener keyPressedListener;

    public KeyboardView(Context context) {
        this(context, null);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        init(context);
    }

    private void init(Context context) {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyPressedListener == null) return;

                if (view instanceof TextView) {
                    CharSequence text = ((TextView) view).getText();
                    char character = text.charAt(0);
                    if (character >= 'A' && character <= 'Z' || character == '-') {
                        keyPressedListener.onKeyPressed(character);
                    } else {
                        keyPressedListener.onDeletePressed();
                    }
                }
            }
        };

        char[][] characters = new char[][] {
                new char[] { 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P' },
                new char[] { 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L' },
                new char[] { '-', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<' }
        };

        for (char[] character : characters) {
            LinearLayout buttonRow = new LinearLayout(context);
            addView(buttonRow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (char letter : character) {
                View buttonView = LayoutInflater.from(context).inflate(R.layout.keyboard_button, buttonRow, false);
                TextView keyboardButton = (TextView) buttonView.findViewById(R.id.keyboardButton);
                keyboardButton.setText(String.valueOf(letter));
                keyboardButton.setOnClickListener(listener);
                buttonRow.addView(buttonView);
            }
        }
    }

    public void setOnKeyPressedListener(OnKeyPressedListener listener) {
        keyPressedListener = listener;
    }

    public interface OnKeyPressedListener {
        void onKeyPressed(char ch);
        void onDeletePressed();
    }
}
