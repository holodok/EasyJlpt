package com.gogaworm.easyjlpt.ui.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.LineHeightSpan;
import android.text.style.ReplacementSpan;

import static android.text.TextUtils.isEmpty;

public class UnitedKanjiKanaSpannableString extends SpannableString {

    public UnitedKanjiKanaSpannableString(CharSequence text) {
        this(text, true);
    }

    public UnitedKanjiKanaSpannableString(CharSequence text, boolean showReading) {
        super(text); //todo: remove reading from here
        if (isEmpty(text)) {
            return;
        }

        StringBuilder kanji = new StringBuilder();
        StringBuilder kana = new StringBuilder();
        int startPosition = 0;
        boolean kanjiStart = false;
        boolean readingStart = false;
        int answerPlaceholderIndex = 1;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '|') {
                if (readingStart) {
                    setSpan(new KanjiSpan(kanji.toString(), kana.toString(), showReading), startPosition, i + 1, SPAN_INCLUSIVE_INCLUSIVE);

                    readingStart = false;
                    kanji.delete(0, kanji.length());
                    kana.delete(0, kana.length());
                } else {
                    kanjiStart = true;
                    startPosition = i;
                }
            } else if (ch == '-') {
                //start reading part
                kanjiStart = false;
                readingStart = true;
            } else if (ch == '_' || ch == '＿') {
                setSpan(new AnswerPlaceholderSpan(answerPlaceholderIndex++, Color.BLUE), i, i + 1, SPAN_INCLUSIVE_EXCLUSIVE);
            } else {
                if (kanjiStart) {
                    kanji.append(ch);
                } else if (readingStart) {
                    kana.append(ch);
                }
            }
        }
    }

    public static String getKanjiFromReading(CharSequence reading) {
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
            kanjiWidth = paint.measureText(kanji, 0, kanji.length());

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
                canvas.drawText(kana, 0, kana.length(), x + width / 2 - kanaWidth / 2, y - textSize, paint);
            }

            paint.setTextSize(textSize);
            paint.setAlpha(alpha);
            canvas.drawText(kanji, 0, kanji.length(), x + width / 2 - kanjiWidth / 2, y, paint);
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

    public static class AnswerPlaceholderSpan extends ReplacementSpan {
        private String index;
        private int color;
        private int holderWidth;
        private int indexWidth;

        AnswerPlaceholderSpan(int index, int color) {
            this.index = String.valueOf(index);
            this.color = color;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            holderWidth = (int) paint.measureText(text, start, end);
            indexWidth = (int) paint.measureText(index);
            return holderWidth;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            paint.setColor(color);
            canvas.drawText(text, start, end, x, y, paint);
            canvas.drawText(index, x + holderWidth / 2 - indexWidth / 2, y, paint);
        }
    }
}

