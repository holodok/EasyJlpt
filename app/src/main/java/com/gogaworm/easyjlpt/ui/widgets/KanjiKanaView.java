package com.gogaworm.easyjlpt.ui.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.LineHeightSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.widget.TextView;

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
        SpannableString text = new SpannableString(japanese);
        text.setSpan(new LineHeightSpan() {
            @Override
            public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
                int size = (int) getTextSize() * 2;
                fm.ascent = fm.descent - size;
                fm.top = fm.bottom - size;
            }
        }, 0, japanese.length(), 0);

        int startKanji = 0;
        int startKana = 0;
        for (int i = 0; i < japanese.length(); i++) {
            char ch = japanese.charAt(i);
            if (isSeparator(ch) || i == japanese.length() - 1) {
                String kanjiWord = japanese.substring(startKanji, i);
                int endKana = reading.length();
                for (int j = startKana; j < reading.length(); j++) {
                    char ch2 = reading.charAt(j);
                    if (isSeparator(ch2)) {
                        endKana = j;
                        break;
                    }
                }
                String kanaWord = reading.substring(startKana, endKana);
                startKana = endKana + 1;
                if (!kanjiWord.equals(kanaWord)) {
                    // try to cut kana ending
                    int endKanji = i;
                    for (int j = kanaWord.length() - 1, k = kanjiWord.length() - 1; j >= 0; j--, k--) {
                        if (kanaWord.charAt(j) == kanjiWord.charAt(k)) {
                            endKanji--;
                        } else {
                            kanaWord = kanaWord.substring(0, j + 1);
                            kanjiWord = kanjiWord.substring(0, k + 1);
                            break;
                        }
                    }
                    text.setSpan(new KanjiSpan(kanjiWord, kanaWord), startKanji, endKanji, 0);
                }
                startKanji = i + 1;
            }
        }
        setText(text);
    }

    private boolean isSeparator(char ch) {
        return ch == ' ' || ch == '、' || Character.isDigit(ch) || ch == '・';
    }

    class KanjiSpan extends ReplacementSpan {
        private CharSequence kanji;
        private CharSequence kana;

        private int kanjiWidth;
        private int kanaWidth;

        KanjiSpan(CharSequence kanji, CharSequence kana) {
            this.kanji = kanji;
            this.kana = kana;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            Rect bounds = new Rect();
            paint.getTextBounds(kanji.toString(), 0, kanji.length(), bounds);
            kanjiWidth = bounds.width();

            float textSize = paint.getTextSize();
            paint.setTextSize(textSize / 2);
            bounds = new Rect();
            paint.getTextBounds(kana.toString(), 0, kana.length(), bounds);
            kanaWidth = bounds.width();

            paint.setTextSize(textSize);
            return Math.max(kanjiWidth, kanaWidth);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int width = Math.max(kanjiWidth, kanaWidth);

            float textSize = paint.getTextSize();
            int alpha = paint.getAlpha();

            paint.setTextSize(textSize / 2);
            paint.setAlpha(137);
            canvas.drawText(kana.toString(), x + width / 2 - kanaWidth / 2, y - textSize, paint);

            paint.setTextSize(textSize);
            paint.setAlpha(alpha);
            canvas.drawText(kanji.toString(), x + width / 2 - kanjiWidth / 2, y, paint);
        }
    }
}
