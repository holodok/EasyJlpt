package com.gogaworm.easyjlpt.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.gogaworm.easyjlpt.utils.KanjiKanaSpannableString;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class KanjiKanaView extends TextView {

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
        setText(new KanjiKanaSpannableString(japanese, reading));
    }
}
