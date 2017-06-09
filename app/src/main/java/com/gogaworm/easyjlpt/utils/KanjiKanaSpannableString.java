package com.gogaworm.easyjlpt.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.LineHeightSpan;
import android.text.style.ReplacementSpan;

/**
 * Created on 08.05.2017.
 *
 * @author ikarpova
 */
public class KanjiKanaSpannableString extends SpannableString {
    public KanjiKanaSpannableString(String japanese, String reading) {
        super(japanese);

        setSpan(new DoubleHeightSpan(), 0, japanese.length(), 0);

        int startKanji = 0;
        int startKana = 0;
        for (int i = 0; i < japanese.length(); i++) {
            char ch = japanese.charAt(i);
            if (startKana < reading.length() && reading.charAt(startKana) == ch && startKanji == i) {
                startKanji++;
                startKana++;
                continue;
            }
            if (isSeparator(ch) || i == japanese.length() - 1) {
                int endKanji = i;
                if (!isSeparator(ch)) endKanji++;
                String kanjiWord = japanese.substring(startKanji, endKanji);
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
                    for (int j = kanaWord.length() - 1, k = kanjiWord.length() - 1; j >= 0; j--, k--) {
                        if (kanaWord.charAt(j) == kanjiWord.charAt(k)) {
                            endKanji--;
                        } else {
                            kanaWord = kanaWord.substring(0, j + 1);
                            kanjiWord = kanjiWord.substring(0, k + 1);
                            break;
                        }
                    }
                    setSpan(new KanjiSpan(kanjiWord, kanaWord), startKanji, endKanji, 0);
                }
                startKanji = i + 1;
            }
        }
    }

    private boolean isSeparator(char ch) {
        return Character.isDigit(ch) || Character.isSpaceChar(ch) || Character.isWhitespace(ch) || ch == '・' || ch == 'っ';
    }

    class DoubleHeightSpan implements LineHeightSpan {
        @Override
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
            int size = (-fm.top + fm.bottom) / 2;
            fm.top = fm.top - size;
        }
    }

    public static class KanjiSpan extends ReplacementSpan {
        CharSequence kanji;
        CharSequence kana;

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
