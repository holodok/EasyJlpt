package com.gogaworm.easyjlpt.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

/**
 * Implementation of LineBackgroundSpan that adds rounded rectangle backgrounds to text.
 */
public final class RoundedCornersBackgroundSpan extends ReplacementSpan {

    private static final int CORNER_RADIUS = 8;
    private static final int PADDING_X = 12;

    private int   mBackgroundColor;
    private int   mTextColor;
    private int width;

    /**
     * @param backgroundColor background color
     * @param textColor       text color
     */
    public RoundedCornersBackgroundSpan(int backgroundColor, int textColor) {
        mBackgroundColor = backgroundColor;
        mTextColor = textColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        width = (int) (PADDING_X + paint.measureText(text, start, end) + PADDING_X);
        return width;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + width, bottom);
        paint.setColor(mBackgroundColor);
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, paint);
        paint.setColor(mTextColor);
        canvas.drawText(text, start, end, x + PADDING_X, y, paint);
    }
}

