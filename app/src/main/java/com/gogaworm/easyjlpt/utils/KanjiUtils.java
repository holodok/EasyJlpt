package com.gogaworm.easyjlpt.utils;

import android.content.Context;
import android.text.SpannableString;
import androidx.core.content.ContextCompat;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.ui.widget.RoundedCornersBackgroundSpan;

import static android.text.TextUtils.isEmpty;

public class KanjiUtils {

    public static SpannableString getKanjiReading(Context context, Kanji kanji) {
        boolean hasOnReading = !isEmpty(kanji.onReading);
        boolean hasKunReading = !isEmpty(kanji.kunReading);

        SpannableString reading = new SpannableString(kanji.getOnReading().concat(hasOnReading ? " " : "").concat(kanji.kunReading));
        if (hasOnReading) {
            setReadingSpans(reading, kanji.onReading, 0, ContextCompat.getColor(context, R.color.colorAccent), ContextCompat.getColor(context, android.R.color.white));
        }
        if (hasKunReading) {
            int startPosition = hasOnReading ? (kanji.onReading.length() + 1) : 0;
            setReadingSpans(reading, kanji.kunReading, startPosition, ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, android.R.color.white));
        }
        return reading;
    }

    public static SpannableString getOnReading(Context context, String onReading) {
        SpannableString reading = new SpannableString(onReading);
        setReadingSpans(reading, onReading, 0,
                ContextCompat.getColor(context, R.color.colorAccent), ContextCompat.getColor(context, android.R.color.white));
        return reading;
    }

    public static SpannableString getKunReading(Context context, String kunReading) {
        SpannableString reading = new SpannableString(kunReading);
        setReadingSpans(reading, kunReading, 0,
                ContextCompat.getColor(context, R.color.colorPrimary), ContextCompat.getColor(context, android.R.color.white));
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
