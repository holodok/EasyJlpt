package com.gogaworm.easyjlpt.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class KanjiKanaSpannableTest {
    @Test
    public void testCreation() throws Exception {
        assertEquals("あの人はさびしげな目をしている。", UnitedKanjiKanaSpannableString.getKanjiFromReading("あの|人-ひと|はさびしげな|目-め|をしている。"));
        assertEquals("彼は何か言いたげだった。", UnitedKanjiKanaSpannableString.getKanjiFromReading("|彼-かれ|は|何-なに|か|言-い|いたげだった。"));
        assertEquals("彼女は悲しげの様子で話した。", UnitedKanjiKanaSpannableString.getKanjiFromReading("|彼-かの||女-じょ|は悲しげの様子で話した。"));
        assertEquals("風邪＿、熱＿。", UnitedKanjiKanaSpannableString.getKanjiFromReading("|風邪-かぜ|＿、|熱-ねつ|＿。"));
    }
}