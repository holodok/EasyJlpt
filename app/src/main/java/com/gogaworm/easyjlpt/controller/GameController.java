package com.gogaworm.easyjlpt.controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import com.gogaworm.easyjlpt.DataRepository;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.game.Task;
import com.gogaworm.easyjlpt.game.TaskCreator;
import com.gogaworm.easyjlpt.game.WordTask;
import com.gogaworm.easyjlpt.loaders.WordListLoader;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private MutableLiveData<List<WordTask>> wordTasksData;
    private MutableLiveData<Game> currentGame;
    private MutableLiveData<Integer> progress;
    private MutableLiveData<StudyResults> studyResults;

    private final List<Game> games = new ArrayList<>();

    private EasyJlptApplication application;
    private DataRepository repository;

    private int currentIndex;
    private int totalTaskCount;

    //statistics
    private int wordCount;
    private int correctCount;
    private int incorrectCount;
    private long startStudyTime;

    public GameController(final EasyJlptApplication application) {
        this.application = application;
        repository = application.getRepository();

        wordTasksData = new MutableLiveData<>();
        currentGame = new MutableLiveData<>();
        progress = new MutableLiveData<>();
        progress.setValue(0);
        studyResults = new MutableLiveData<>();
    }

    //background
    public void createGame(Context context) { //used for reading from the assets, possibly should be changed
        final UserSession userSession = repository.getUserSession();
        final WordListLoader loader = new WordListLoader(context, userSession.section, userSession.level, userSession.getLessonId());

        new AsyncTask<Void, Void, List<WordTask>>() {

            @Override
            protected List<WordTask> doInBackground(Void... voids) {
                List<Word> words = loader.loadInBackground();
                TaskCreator taskCreator = new TaskCreator();
                taskCreator.addWords(words);
                return taskCreator.generateLearnSession();
            }

            @Override
            protected void onPostExecute(List<WordTask> wordTasks) {
                super.onPostExecute(wordTasks);
                wordTasksData.setValue(wordTasks);

/*
                List<WordTask> tasks = new ArrayList<>();
                tasks.add(wordTasks.get(0));
                wordTasksData.setValue(tasks);
*/

                int maxGamesCount = 1;
                for (Task task : wordTasks) {
                    task.leftGames = maxGamesCount;
                }
                totalTaskCount = wordTasks.size() * maxGamesCount;

                wordCount = wordTasks.size();
                correctCount = 0;
                incorrectCount = 0;
                startStudyTime = System.currentTimeMillis();

                nextTask();
            }
        }.executeOnExecutor(application.getExecutors().diskIO());
    }

    public void addGame(Game game) {
        //add possible game
        games.add(game);
    }

    public void nextTask() {
        List<WordTask> value = wordTasksData.getValue();
        if (value == null) return;

        for (; currentIndex < value.size(); currentIndex++) {
            WordTask task = value.get(currentIndex);
            if (canStudy(task)) {
                return;
            }
        }

        gameOver();
    }

    public void userAnswered(GameVariant variant) {
        Game game = currentGame.getValue();
        if (game.isAnswered()) return;

        game.userAnswered(variant);
        currentGame.setValue(game);
        currentIndex++;
        progress.setValue(currentIndex * 100 / totalTaskCount);
        if (game.isAnswerCorrect()) {
            correctCount++;
        } else {
            incorrectCount++;
        }
    }

    private boolean canStudy(WordTask task) {
        if (task.isComplete()) {
            return false;
        }
        if (task.currentGame == games.size()) {
            task.currentGame = 0;
        }
        for (int i = task.currentGame; i < games.size(); i++) {
            Game game = games.get(i);
            if (game.canInitialize(wordTasksData.getValue(), task)) {
                task.currentGame = i;
                currentGame.setValue(game);
                return true;
            }
        }
        return false;
    }

    private void gameOver() {
        int progress = Math.round(correctCount * 100f / (correctCount + incorrectCount));
        long studyTime = (System.currentTimeMillis() - startStudyTime) / 1000;
        studyResults.setValue(new StudyResults(progress, wordCount, studyTime)); //todo: show some results
    }

    public void clear() {
        repository = null;
    }

    public LiveData<Integer> getProgress() {
        return progress;
    }

    public LiveData<Game> getCurrentGame() {
        return currentGame;
    }

    public LiveData<StudyResults> getStudyResults() {
        return studyResults;
    }
}
