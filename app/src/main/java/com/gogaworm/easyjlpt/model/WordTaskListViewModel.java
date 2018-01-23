package com.gogaworm.easyjlpt.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.controller.*;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.game.GameVariant;

public class WordTaskListViewModel extends UserSessionViewModel {
    private GameController gameController;

    public WordTaskListViewModel(@NonNull Application application) {
        super(application);

        gameController = new GameController((EasyJlptApplication) getApplication());
        gameController.addGame(new FlashCardGame());
        gameController.addGame(new SelectTranslationByReadingGame());
        gameController.addGame(new SelectTranslationByKanjiGame());
        gameController.addGame(new SelectReadingByKanjiGame());
        gameController.addGame(new SelectKanjiByReadingGame());
        gameController.addGame(new SelectKanjiByTranslationGame());
    }

    public void startGame(UserSession userSession) {
        gameController.createGame(getApplication(), userSession);
    }

    @Override
    protected void onCleared() {
        gameController.clear();
    }

    @Override
    protected void onUserSessionChanged(UserSession userSession) {
    }

    public LiveData<Integer> getProgress() {
        return gameController.getProgress();
    }

    public LiveData<Game> getCurrentGame() {
        return gameController.getCurrentGame();
    }

    public LiveData<StudyResults> getStudyResults() {
        return gameController.getStudyResults();
    }

    public void userAnswered(GameVariant variant) { //todo: what should be the answer?...
        gameController.userAnswered(variant);
    }

    public void nextTask() {
        gameController.nextTask();
    }
}
