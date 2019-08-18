package com.gogaworm.easyjlpt.loader;

import android.content.Context;
import com.gogaworm.easyjlpt.db.AppDatabase;
import com.gogaworm.easyjlpt.db.Expression;
import com.gogaworm.easyjlpt.db.Sentence;
import com.gogaworm.easyjlpt.db.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WordJsonFileLoader extends JsonFileLoader<Word> {

    public void loadItems(Context context, AppDatabase appDatabase, int courseId, String lessonCode, String lessonFileName) {
        try {
            loadItemsFromJson(context, appDatabase, courseId, lessonCode, lessonFileName);
        } catch (Exception ex) {
            System.err.println("Parsing file: " + lessonFileName);
            ex.printStackTrace();
        }
    }

    protected void parseItems(AppDatabase appDatabase, int lessonId, JSONObject root, List<Word> results) throws JSONException {
        JSONArray jsonWords = root.optJSONArray("words");

        for (int i = 0; i < jsonWords.length(); i++) {
            JSONObject json = jsonWords.getJSONObject(i);
            JSONObject wordJson = json.optJSONObject("word");
            boolean newFormatData = wordJson != null;
            Word word;
            if (newFormatData) {
                word = new Word(
                        lessonId,
                        wordJson.getString("japanese"),
                        wordJson.optString("reading"),
                        wordJson.optString("translation")
                );
                JSONArray expressionsJsonArray = json.optJSONArray("expressions");
                if (expressionsJsonArray != null) {
                    for (int j = 0; j < expressionsJsonArray.length(); j++) {
                        JSONObject expressionJson = expressionsJsonArray.getJSONObject(j);
                        word.expressions.add(new Expression(
                                lessonId,
                                expressionJson.getString("japanese"),
                                expressionJson.optString("reading"),
                                expressionJson.optString("translation"),
                                Expression.TYPE_WORD));
                    }
                }
                JSONArray sentencesJsonArray = json.optJSONArray("sentences");
                if (sentencesJsonArray != null) {
                    for (int j = 0; j < sentencesJsonArray.length(); j++) {
                        JSONObject sentencesJson = sentencesJsonArray.getJSONObject(j);
                        word.sentences.add(new Sentence(
                                lessonId,
                                sentencesJson.getString("japanese"),
                                sentencesJson.optString("translation")));
                    }
                }
            } else {
                word = new Word(
                        lessonId,
                        json.getString("japanese"),
                        json.optString("reading"),
                        json.optString("translation")
                );
            }
            results.add(word);

            //sync with DB
            syncWithDb(appDatabase, lessonId, results);
        }
    }

    private void syncWithDb(AppDatabase appDatabase, int lessonId, List<Word> wordsFromJson) {
        List<Word> wordsFromDb = appDatabase.getWordDao().getWordsForLessonRaw(lessonId);

        for (Word word : wordsFromJson) {

            int wordId = (int) appDatabase.getWordDao().insert(word);
            for (Expression expression : word.expressions) {
                expression.groupId = wordId;
                appDatabase.getExpressionDao().insert(expression);
            }
            for (Sentence sentence : word.sentences) {
                sentence.wordId = wordId;
                appDatabase.getSentenceDao().insert(sentence);
            }
        }
    }

    private void updateWord(Word word, List<Word> wordsFromDb) {
        for (int i = 0; i < wordsFromDb.size(); i++) {
            Word wordFromDb = wordsFromDb.get(i);
            if (word.guid.equals(wordFromDb.guid)) {
                word.id = wordFromDb.id;
                wordsFromDb.remove(i);
                break;
            }
        }
        //todo: insert or update word
    }
}
