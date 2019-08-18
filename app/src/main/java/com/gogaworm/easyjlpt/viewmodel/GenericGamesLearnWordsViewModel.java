package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.db.Lesson;
import com.gogaworm.easyjlpt.games.GameController;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.games.StudyResults;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericGamesLearnWordsViewModel<D> extends AndroidViewModel {
    private final AppController appController;
    protected GameController<D> gameController;

    private GameTemplate.Game<D> currentGameInstance;
    private MutableLiveData<GameTemplate.Game<D>> currentGameData;
    private MutableLiveData<Integer> gameProgress;

    private long startStudyTime; //todo:move to fragment of view mmodel to react onPause
    private long endStudyTime;
    private int wordCount;

    public GenericGamesLearnWordsViewModel(@NonNull Application application) {
        super(application);
        appController = ((EasyJlptApplication) application).getAppController();
        gameController = new GameController<>();

        currentGameData = new MutableLiveData<>();
        gameProgress = new MutableLiveData<>();
    }

    public LiveData<Lesson> getSelectedLesson() {
        return appController.getSelectedLesson();
    }

    public abstract LiveData<List<D>> getDataForLesson(int lessonId);

    public void startGame(List<D> dataList) {
        wordCount = dataList.size();
        ArrayList<GameTemplate<D>> games = new ArrayList<>();
        createGames(games);

        gameController.initialize(games, dataList);

        //update live data variable
        currentGameInstance = gameController.getNextGameInstance();
        currentGameData.postValue(currentGameInstance);
        startStudyTime = System.currentTimeMillis();
    }

    protected abstract void createGames(List<GameTemplate<D>> games);

    public LiveData<GameTemplate.Game<D>> getCurrentGame() {
        return currentGameData;
    }

    public void gameFinished(boolean result) {
        gameController.gameFinished(result);
        currentGameInstance = gameController.getNextGameInstance();
        currentGameData.setValue(currentGameInstance);
        gameProgress.setValue(100 * (gameController.getTotalTasks() - gameController.getLeftTasks()) / gameController.getTotalTasks());
        endStudyTime = System.currentTimeMillis();
    }

    public LiveData<Integer> getGameProgress() {
        return gameProgress;
    }

    public StudyResults getStudyResults() {
        int progress = Math.round(gameController.getCorrect() * 100f / (gameController.getCorrect() + gameController.getWrong()));
        long studyTime = (endStudyTime - startStudyTime) / 1000;
        return new StudyResults(progress, wordCount, studyTime);
    }
}
