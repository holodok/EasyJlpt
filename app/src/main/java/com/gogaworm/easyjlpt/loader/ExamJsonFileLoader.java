package com.gogaworm.easyjlpt.loader;

import android.content.Context;
import com.gogaworm.easyjlpt.db.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExamJsonFileLoader extends JsonFileLoader<Exam> {
    @Override
    public void loadItems(Context context, AppDatabase appDatabase, int courseId, String lessonCode, String lessonFileName) {
        try {
            List<Exam> exams = loadItemsFromJson(context, appDatabase, courseId, lessonFileName, lessonCode);
            for (Exam exam : exams) {
                appDatabase.getExamDao().insert(exam);
            }
        } catch (Exception ex) {
            System.err.println("Parsing file: " + lessonFileName);
            ex.printStackTrace();
        }
    }

    @Override
    protected void parseItems(int lessonId, JSONObject root, List<Exam> results) throws JSONException {
        JSONArray jsonSentences = root.optJSONArray("sentences");

        for (int i = 0; i < jsonSentences.length(); i++) {
            JSONObject jsonSentence = jsonSentences.getJSONObject(i);
            Exam exam = new Exam();
            exam.japanese = jsonSentence.getString("japanese");
            exam.translation = jsonSentence.getString("translation");
            JSONArray jsonAnswers = jsonSentence.getJSONArray("answers");
            exam.answers = new ArrayList<>(jsonAnswers.length());
            for (int j = 0; j < jsonAnswers.length(); j++) {
                exam.answers.add(jsonAnswers.getJSONObject(j).getString("japanese"));
            }
            exam.correctIndexes = new ArrayList<>();
            int correctIndex = jsonSentence.optInt("correct", 0);
            if (correctIndex > 0) {
                exam.correctIndexes.add(correctIndex);
            }
            JSONArray jsonCorrectArray = jsonSentence.optJSONArray("correct");
            if (jsonCorrectArray != null) {
                for (int j = 0; j < jsonCorrectArray.length(); j++) {
                    exam.correctIndexes.add(jsonCorrectArray.getInt(j));
                }
            }

            results.add(exam);
        }
    }
}
