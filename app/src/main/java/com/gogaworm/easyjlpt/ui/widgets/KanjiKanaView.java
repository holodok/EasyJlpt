package com.gogaworm.easyjlpt.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;
import com.gogaworm.easyjlpt.utils.KanjiKanaSpannableString;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class KanjiKanaView extends AppCompatTextView {

    public KanjiKanaView(Context context) {
        super(context);
    }

    public KanjiKanaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KanjiKanaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setText(String japanese, String reading) {
        if (!isEmpty(japanese) && !isEmpty(reading)) {
            setText(new KanjiKanaSpannableString(japanese, reading));
        } else if (!isEmpty(japanese)) {
            setText(japanese);
        } else {
            setText(reading);
        }
    }
}
