package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.games.*;

import java.util.Collections;
import java.util.List;

public class LearnKanjiViewModel extends GenericGamesLearnWordsViewModel<Kanji> {
    private final AppController controller;

    public LearnKanjiViewModel(@NonNull Application application) {
        super(application);
        controller = ((EasyJlptApplication) application).getAppController();
    }

    @Override
    public LiveData<List<Kanji>> getDataForLesson(int lessonId) {
        MutableLiveData<List<Kanji>> liveData = new MutableLiveData<>();
        new DataLoadTask(controller, liveData).execute(lessonId);
        return liveData;
    }

    @Override
    protected void createGames(List<GameTemplate<Kanji>> gameTemplates) {
        gameTemplates.add(new KanjiViewGameTemplate());
        gameTemplates.add(new KanjiSelectJapaneseByTranslationGameTemplate());
        gameTemplates.add(new KanjiSelectOnReadingByKanjiGameTemplate());
        gameTemplates.add(new KanjiSelectKunReadingByKanjiGameTemplate());
        gameTemplates.add(new KanjiSelectTranslationByKanjiGameTemplate());
        gameTemplates.add(new SelectKanjiByOnReadingGameTemplate());
        gameTemplates.add(new SelectKanjiByKunReadingGameTemplate());
    }

    static class DataLoadTask extends AsyncTask<Integer, Void, List<Kanji>> {
        private AppController controller;
        private MutableLiveData<List<Kanji>> liveData;

        DataLoadTask(AppController controller, MutableLiveData<List<Kanji>> liveData) {
            this.controller = controller;
            this.liveData = liveData;
        }

        @Override
        protected List<Kanji> doInBackground(Integer... lessonId) {
            List<Kanji> kanji = controller.getKanjiForSelectedLesson(lessonId[0]);
            //shuffle
            Collections.shuffle(kanji);
            return kanji;
        }

        @Override
        protected void onPostExecute(List<Kanji> kanji) {
            super.onPostExecute(kanji);
            liveData.setValue(kanji);
        }
    }
}