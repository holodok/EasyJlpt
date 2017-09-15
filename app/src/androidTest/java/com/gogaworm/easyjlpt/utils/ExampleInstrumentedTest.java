package com.gogaworm.easyjlpt.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.gogaworm.easyjlpt", appContext.getPackageName());
    }

    @Test
    public void testKanjiKanaSpannable() {
        KanjiKanaSpannableString spannableString = new KanjiKanaSpannableString("無料", "むりょう");
        KanjiKanaSpannableString.KanjiSpan[] kanjiSpans = spannableString.getSpans(0, spannableString.length(), KanjiKanaSpannableString.KanjiSpan.class);
        assertEquals(1, kanjiSpans.length);
        assertEquals("むりょう", kanjiSpans[0].kana);

        spannableString = new KanjiKanaSpannableString("友人 を 招きました。", "ゆうじん を まねきました。");
        kanjiSpans = spannableString.getSpans(0, spannableString.length(), KanjiKanaSpannableString.KanjiSpan.class);
        assertEquals(2, kanjiSpans.length);
        assertEquals("ゆうじん", kanjiSpans[0].kana);
        assertEquals("まね", kanjiSpans[1].kana);
    }

    @Test
    public void testEnterKanaView() {

    }

    @Test
    public void testConvert() {
        new UnitedKanjiKanaSpannableString("|風邪-かぜ|＿、|熱-ねつ|＿。");
        new UnitedKanjiKanaSpannableString("|仕-し||事-ごと|は|順-じゅん||調-ちょう|ですか。");
    }
}
