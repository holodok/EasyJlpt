package com.gogaworm.easyjlpt.utils;

import android.content.Context;
import android.text.SpannableString;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Kanji;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.ui.widgets.rcbs.RoundedCornersBackgroundSpan;

public class KanjiUtils {
    public static CharSequence getReading(Context context, Word word) {
        if (word instanceof Kanji) {
            return getKanjiReading(context, (Kanji) word);
        }
        return !word.japanese.equals(word.reading) ? word.reading : "";
    }

    private static SpannableString getKanjiReading(Context context, Kanji kanji) {
        SpannableString reading = new SpannableString(kanji.reading);
        if (kanji.hasOnReading()) {
            setReadingSpans(reading, kanji.onReading, 0, context.getResources().getColor(R.color.colorAccent), context.getResources().getColor(R.color.primaryInvertedText));
        }
        if (kanji.hasKunReading()) {
            int startPosition = kanji.hasOnReading() ? (kanji.onReading.length() + 1) : 0;
            setReadingSpans(reading, kanji.kunReading, startPosition, context.getResources().getColor(R.color.colorPrimary), context.getResources().getColor(R.color.primaryInvertedText));
        }
        return reading;
    }

    private static void setReadingSpans(SpannableString spannable, String reading, int startPosition, int backgroundColor, int textColor) {
        String[] readings = reading.split(" ");
        int position = startPosition;
        for (String readingPart : readings) {
            spannable.setSpan(new RoundedCornersBackgroundSpan(backgroundColor, textColor),
                    position, position + readingPart.length(), 0);
            position += readingPart.length() + 1;
        }
    }
}
