package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.db.Exam;
import com.gogaworm.easyjlpt.games.ExamGameTemplate;
import com.gogaworm.easyjlpt.games.GameTemplate;

import java.util.Collections;
import java.util.List;

public class ExamViewModel extends GenericGamesLearnWordsViewModel<Exam> {
    final AppController controller;

    public ExamViewModel(@NonNull Application application) {
        super(application);
        controller = ((EasyJlptApplication) application).getAppController();
    }

    @Override
    public LiveData<List<Exam>> getDataForLesson(int lessonId) {
        MutableLiveData<List<Exam>> liveData = new MutableLiveData<>();
        new DataLoadTask(controller, liveData).execute(lessonId);
        return liveData;
    }

    @Override
    protected void createGames(List<GameTemplate<Exam>> games) {
        games.add(new ExamGameTemplate());
    }

    static class DataLoadTask extends AsyncTask<Integer, Void, List<Exam>> {
        private AppController controller;
        private MutableLiveData<List<Exam>> liveData;

        DataLoadTask(AppController controller, MutableLiveData<List<Exam>> liveData) {
            this.controller = controller;
            this.liveData = liveData;
        }

        @Override
        protected List<Exam> doInBackground(Integer... lessonId) {
            List<Exam> exams = controller.getExamForSelectedLesson(lessonId[0]);
            //shuffle
            Collections.shuffle(exams);
            return exams;
        }

        @Override
        protected void onPostExecute(List<Exam> exams) {
            super.onPostExecute(exams);
            liveData.setValue(exams);
        }
    }
}
