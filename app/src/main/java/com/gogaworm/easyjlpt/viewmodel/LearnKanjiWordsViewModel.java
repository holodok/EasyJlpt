package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.games.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LearnKanjiWordsViewModel extends GenericGamesLearnWordsViewModel<Word> {

    final AppController controller;

    public LearnKanjiWordsViewModel(@NonNull Application application) {
        super(application);
        controller = ((EasyJlptApplication) application).getAppController();
    }

    @Override
    public LiveData<List<Word>> getDataForLesson(int lessonId) {
        MutableLiveData<List<Word>> liveData = new MutableLiveData<>();
        new DataLoadTask(controller, liveData).execute(lessonId);
        return liveData;
    }

    @Override
    protected void createGames(List<GameTemplate<Word>> gameTemplates) {
        gameTemplates.add(new WordViewGameTemplate());

        gameTemplates.add(new WordSelectTranslationByJapaneseTemplate());
        gameTemplates.add(new WordSelectReadingByJapaneseGameTemplate());
        gameTemplates.add(new WordSelectJapaneseByTranslationGameTemplate());

        gameTemplates.add(new WordTypeReadingByJapaneseGameTemplate());
        gameTemplates.add(new WordTypeReadingByTranslationGameTemplate());
    }

    static class DataLoadTask extends AsyncTask<Integer, Void, List<Word>> {
        private AppController controller;
        private MutableLiveData<List<Word>> liveData;

        DataLoadTask(AppController controller, MutableLiveData<List<Word>> liveData) {
            this.controller = controller;
            this.liveData = liveData;
        }

        @Override
        protected List<Word> doInBackground(Integer... lessonId) {
            List<Kanji> kanjis = controller.getKanjiForSelectedLesson(lessonId[0]);
            //shuffle
            Collections.shuffle(kanjis);

            List<Word> results = new ArrayList<>();
            //for each word load expressions
            for (Kanji kanji : kanjis) {
                results.addAll(controller.getExpressionsForKanji(kanji.id));
            }
            return results;
        }

        @Override
        protected void onPostExecute(List<Word> words) {
            super.onPostExecute(words);
            liveData.setValue(words);
        }
    }

}
