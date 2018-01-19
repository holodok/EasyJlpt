package com.gogaworm.easyjlpt.controller;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.SparseArray;
import com.gogaworm.easyjlpt.DataRepository;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.game.GameVariant;
import com.gogaworm.easyjlpt.game.Task;
import com.gogaworm.easyjlpt.game.TaskCreator;
import com.gogaworm.easyjlpt.game.WordTask;
import com.gogaworm.easyjlpt.loaders.WordListLoader;

import java.util.*;

public class GameController {
    private MutableLiveData<Game> currentGame;
    private MutableLiveData<Integer> progress;
    private MutableLiveData<StudyResults> studyResults;

    private EasyJlptApplication application;
    private DataRepository repository;

    private final List<WordTask> wordTasks = new ArrayList<>();
    private final List<Game> games = new ArrayList<>();

    private final SparseArray<List<WordTask>> gameCourse = new SparseArray<>();

    private int maxGamesCount = 4;
    private int levelIndex;
    private int levelGameCount;
    private int totalTaskCount;
    private int answeredTasks;
    private boolean repeatTask;

    //statistics
    private int wordCount;
    private int correctCount;
    private int incorrectCount;
    private long startStudyTime;
    private WordTask currentTask;

    public GameController(final EasyJlptApplication application) {
        this.application = application;
        repository = application.getRepository();

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
            protected void onPostExecute(List<WordTask> results) {
                super.onPostExecute(results);
                //wordTasksData.setValue(wordTasks);

/*
                List<WordTask> tasks = new ArrayList<>();
                tasks.add(wordTasks.get(0));
*/

                initialize(results);
            }
        }.executeOnExecutor(application.getExecutors().diskIO());
    }

    public void addGame(Game game) {
        //add possible game
        games.add(game);
    }

    private void initialize(List<WordTask> newTasks) {
        wordTasks.clear();
        wordTasks.addAll(newTasks);

        for (Task task : wordTasks) {
            task.leftGames = maxGamesCount;
        }

        gameCourse.clear();
        for (int i = 0; i < maxGamesCount; i++) {
            gameCourse.put(i, new ArrayList<WordTask>());
        }
        gameCourse.get(0).addAll(wordTasks);
        levelIndex = 0;
        levelGameCount = maxGamesCount;

        totalTaskCount = wordTasks.size() * maxGamesCount;
        wordCount = wordTasks.size();
        correctCount = 0;
        incorrectCount = 0;
        startStudyTime = System.currentTimeMillis();

        nextTask();
    }

    public void nextTask() {
        if (wordTasks == null) return;

        // check game over
        boolean isGameOver = true;
        for (int i = 0; i < gameCourse.size(); i++) {
            isGameOver = gameCourse.get(i).isEmpty();
            if (!isGameOver) break;
        }
        if (isGameOver) {
            gameOver();
            return;
        }

        if (levelIndex == gameCourse.size()) {
            levelIndex = 0;
            levelGameCount = Math.max(maxGamesCount - levelIndex, 1);
        }
        if (repeatTask) {
            repeatTask = false;
            Game repeatGame = games.get(currentTask.currentGame);
            if (repeatGame.canInitialize(Collections.unmodifiableList(wordTasks), currentTask)) {
                currentGame.setValue(repeatGame);
            }
            return;
        }

        if (levelGameCount > 0) {
            currentTask = getTaskFromLevel(levelIndex);
            if (currentTask == null) {
                levelIndex++;
                nextTask();
                return;
            }
            prepareTask(currentTask);
            levelGameCount--;
        } else {
            levelIndex++;
            levelGameCount = Math.max(maxGamesCount - levelIndex, 1);
            nextTask();
        }
    }

    private WordTask getTaskFromLevel(int levelIndex) {
        List<WordTask> wordTasks = gameCourse.get(levelIndex);
        return wordTasks.isEmpty() ? null : wordTasks.remove(0);
    }

    public void userAnswered(GameVariant variant) {
        Game game = currentGame.getValue();
        if (game.isAnswered()) return;

        //update game
        game.userAnswered(variant);
        currentGame.setValue(game);

        if (game.isAnswerCorrect()) {
            answeredTasks++;
            currentTask.leftGames--;
            currentTask.currentGame++;
            correctCount++;

            //move task to next level or remove
            if (!currentTask.isComplete() && levelIndex + 1 < gameCourse.size()) {
                gameCourse.get(levelIndex + 1).add(currentTask);
            }
            progress.setValue(answeredTasks * 100 / totalTaskCount);
        } else {
            // show this task again
            currentTask.currentGame = 0;
            currentTask.leftGames++;
            incorrectCount++;
            repeatTask = true;
        }
    }

    private void prepareTask(WordTask task) {
        if (task.currentGame == games.size()) {
            task.currentGame = 1;
        }
        for (int i = task.currentGame; i < games.size(); i++) {
            Game game = games.get(i);
            if (game.canInitialize(Collections.unmodifiableList(wordTasks), task)) {
                task.currentGame = i;
                currentGame.setValue(game);
                return;
            }
        }
        for (int i = 1; i < task.currentGame; i++) {
            Game game = games.get(i);
            if (game.canInitialize(Collections.unmodifiableList(wordTasks), task)) {
                task.currentGame = i;
                currentGame.setValue(game);
                return;
            }
        }
        task.leftGames = 0;
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
