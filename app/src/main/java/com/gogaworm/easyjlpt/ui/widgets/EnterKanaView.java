package com.gogaworm.easyjlpt.ui.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.utils.English2KanaConverter;
import com.gogaworm.easyjlpt.utils.WanaKanaJava;

import java.io.IOException;

/**
 * Created on 04.04.2017.
 *
 * @author ikarpova
 */
public class EnterKanaView extends View implements KeyboardView.OnKeyPressedListener {
    private int maxLength;

    private String text = "";
    private Paint textPaint;
    private Paint linePaint;
    private int strokeWidth;
    private int strokeGap;
    private char[] drawChar;

    private English2KanaConverter english2KanaConverter;
    private WanaKanaJava kanaConverter;
    private String expectedWord;
    enum KanaType {Hiragana, Katakana, Mixed}
    private KanaType kanaType;

    public EnterKanaView(Context context) {
        this(context, null);
    }

    public EnterKanaView(Context context, AttributeSet attrs) {
        super(context, attrs);

        final Resources.Theme theme = context.getTheme();
        textPaint = new TextPaint();
        linePaint = new Paint();

        TypedArray typedArray = theme.obtainStyledAttributes(attrs, R.styleable.EnterKanaView, 0, 0);
        textPaint.setColor(typedArray.getColor(R.styleable.EnterKanaView_textColor, getResources().getColor(R.color.primaryText)));
        textPaint.setTextSize(typedArray.getDimensionPixelSize(R.styleable.EnterKanaView_textSize, 14));
        typedArray.recycle();

        textPaint.setAntiAlias(true);
        linePaint.setColor(textPaint.getColor());
        strokeWidth = context.getResources().getDimensionPixelSize(R.dimen.enter_kana_stroke_width);
        linePaint.setStrokeWidth(strokeWidth);
        strokeGap = context.getResources().getDimensionPixelSize(R.dimen.enter_kana_stroke_gap);

        drawChar = text.toCharArray();
        english2KanaConverter = new English2KanaConverter();
        kanaConverter = new WanaKanaJava(false);
    }

    public void init() throws IOException {
        english2KanaConverter.initMapping(getContext());
    }

    public void setExpectedWord(String expectedWord) {
        this.expectedWord = expectedWord;
        this.maxLength = expectedWord.length();
        kanaType = kanaConverter.isHiragana(expectedWord) ? KanaType.Hiragana : kanaConverter.isKatakana(expectedWord) ? KanaType.Katakana : KanaType.Mixed;
        invalidate();
    }

    private void onCharacterInput(char ch) {
        String toConvert = text + ch;
        String converted = kanaType == KanaType.Hiragana ? kanaConverter.toHiragana(toConvert) : kanaConverter.toKatakana(toConvert);
        if (converted.length() > maxLength) {
            toConvert = text.substring(0, text.length() - 1) + ch;
            text = kanaType == KanaType.Hiragana ? kanaConverter.toHiragana(toConvert) : kanaConverter.toKatakana(toConvert);
        } else {
            text = converted;
        }
        drawChar = text.toCharArray();
        invalidate();
    }

    private void onCharacterDelete() {
        if (text.length() > 0) {
            text = text.substring(0, text.length() - 1);
            drawChar = text.toCharArray();
            invalidate();
        }
    }

    @Override
    public void onKeyPressed(char ch) {
        onCharacterInput(ch); //convert to japanese
    }

    @Override
    public void onDeletePressed() {
        onCharacterDelete();
    }

    public String getText() {
        return new String(drawChar);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = (int) (maxLength * textPaint.getTextSize() + strokeGap * (maxLength - 1));
        int height = (int) (textPaint.getTextSize() + strokeWidth * 3);
        setMeasuredDimension(width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        float x = 0;
        float y = textPaint.getTextSize();

        //draw placeholders
        float placeHolderWidth = textPaint.getTextSize();
        int drawnPlaceholderCount = maxLength;
        for (int i = 0; i < drawnPlaceholderCount; i++) {
            //draw char if any
            if (i < text.length()) {
                canvas.drawText(drawChar, i, 1, x, y, textPaint);
            }

            canvas.drawLine(x, y  + strokeWidth * 2, x + placeHolderWidth, y + strokeWidth * 2, linePaint);
            x += placeHolderWidth + strokeGap;
        }
    }
}
