package com.gogaworm.easyjlpt.ui.widgets.rcbs;

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
        return (int) (PADDING_X + paint.measureText(text.subSequence(start, end).toString()) + PADDING_X);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float width = paint.measureText(text.subSequence(start, end).toString());
        RectF rect = new RectF(x, top, x + width + 2 * PADDING_X, bottom);
        paint.setColor(mBackgroundColor);
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, paint);
        paint.setColor(mTextColor);
        canvas.drawText(text, start, end, x + PADDING_X, y, paint);
    }
}

