package com.gogaworm.easyjlpt.loaders;

import android.content.Context;
import com.gogaworm.easyjlpt.data.Exam;
import com.gogaworm.easyjlpt.data.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 18.08.2017.
 *
 * @author ikarpova
 */
public class ExamLoader extends JlptDataLoader<Exam> {
    private int examId;

    public ExamLoader(Context context, String folder, int examId) {
        super(context, folder);
        this.examId = examId;
    }

    @Override
    protected void syncWithDataBase(List<Exam> results) {}

    @Override
    protected String[] getFiles(String folder) {
        return new String[] { folder + "/exam_" + examId };
    }

    @Override
    protected List<Exam> createEmptyList() {
        return new ArrayList<>();
    }

    @Override
    protected void parseJson(List<Exam> results, String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        JSONArray sectionsArray = root.getJSONArray("sentences");
        for (int i = 0; i < sectionsArray.length(); i++) {
            JSONObject jsonExam = sectionsArray.getJSONObject(i);
            results.add(new Exam(
                    jsonExam.getInt("id"),
                    jsonExam.getString("japanese"),
                    jsonExam.optString("reading"),
                    jsonExam.getString("translation"),
                    parseAnswers(jsonExam.getJSONArray("answers")),
                    jsonExam.getInt("correct")));
        }
    }

    private Word[] parseAnswers(JSONArray jsonAnswers) throws JSONException {
        Word[] answers = new Word[jsonAnswers.length()];
        for (int i = 0; i < answers.length; i++) {
            answers[i] = parseWord(jsonAnswers.getJSONObject(i));
        }
        return answers;
    }
}
