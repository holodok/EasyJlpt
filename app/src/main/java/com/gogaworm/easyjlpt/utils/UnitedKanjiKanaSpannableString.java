package com.gogaworm.easyjlpt.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.LineHeightSpan;
import android.text.style.ReplacementSpan;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 24.07.2017.
 *
 * @author ikarpova
 */
public class UnitedKanjiKanaSpannableString extends SpannableString {

    public UnitedKanjiKanaSpannableString(CharSequence text) {
        this(text, true);
    }

    public UnitedKanjiKanaSpannableString(CharSequence text, boolean showReading) {
        super(getKanjiFromReading(text)); //todo: remove reading from here
        if (isEmpty(text)) {
            return;
        }

        StringBuilder kanji = new StringBuilder();
        StringBuilder kana = new StringBuilder();
        int startPosition = 0;
        int endPosition = 0;
        int currentPosition = 0;
        boolean kanjiStart = false;
        boolean readingStart = false;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '|') {
                if (readingStart) {
                    setSpan(new KanjiSpan(kanji.toString(), kana.toString(), showReading), startPosition, endPosition, 0);

                    readingStart = false;
                    kanji.delete(0, kanji.length());
                    kana.delete(0, kana.length());
                } else {
                    kanjiStart = true;
                    startPosition = currentPosition;
                }
            } else if (ch == '-') {
                //start reading part
                kanjiStart = false;
                readingStart = true;
                endPosition = currentPosition;
            } else {
                if (kanjiStart) {
                    kanji.append(ch);
                    currentPosition++;
                } else if (readingStart) {
                    kana.append(ch);
                } else {
                    currentPosition++;
                }
            }
        }
    }

    static String getKanjiFromReading(CharSequence reading) {
        StringBuilder buffer = new StringBuilder();
        boolean kanjiStart = false;
        boolean readingStart = false;
        for (int i = 0; i < reading.length(); i++) {
            char ch = reading.charAt(i);
            if (ch == '|') {
                if (kanjiStart) readingStart = false;
                kanjiStart = !kanjiStart;
                continue;
            }
            if (ch == '-') readingStart = true;

            if (!readingStart) {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static class KanjiSpan extends ReplacementSpan implements LineHeightSpan {
        CharSequence kanji;
        CharSequence kana;
        private boolean showReading;

        private float kanjiWidth;
        private float kanaWidth;
        private float textSize;

        KanjiSpan(CharSequence kanji, CharSequence kana, boolean showReading) {
            this.kanji = kanji;
            this.kana = kana;
            this.showReading = showReading;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            kanjiWidth = paint.measureText(kanji.toString());

            textSize = paint.getTextSize();
            paint.setTextSize(textSize / 2);
            kanaWidth = paint.measureText(kana.toString());

            paint.setTextSize(textSize);
            return (int) Math.max(kanjiWidth, kanaWidth);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int width = (int) Math.max(kanjiWidth, kanaWidth);

            int alpha = paint.getAlpha();
            if (showReading) {
                paint.setTextSize(textSize / 2);
                paint.setAlpha(137);
                canvas.drawText(kana.toString(), x + width / 2 - kanaWidth / 2, y - textSize, paint);
            }

            paint.setTextSize(textSize);
            paint.setAlpha(alpha);
            canvas.drawText(kanji.toString(), x + width / 2 - kanjiWidth / 2, y, paint);
        }

        @Override
        public void chooseHeight(CharSequence text, int start, int end, int spanstartv, int v, Paint.FontMetricsInt fm) {
            int ht = (int) (textSize * 2);

            int need = ht - (v + fm.descent - fm.ascent - spanstartv);
            if (need > 0)
                fm.descent += need;

            need = ht - (v + fm.bottom - fm.top - spanstartv);
            if (need > 0)
                fm.top -= need;
        }
    }
}
