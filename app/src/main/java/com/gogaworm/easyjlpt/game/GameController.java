package com.gogaworm.easyjlpt.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 17.04.2017.
 *
 * @author ikarpova
 */
public class GameController {
    enum GameType {
        FLASH_CARD,
        SELECT_TRANSLATION_BY_READING,
        SELECT_TRANSLATION_BY_KANJI,
        SELECT_READING_BY_KANJI,
        SELECT_KANJI_BY_READING,
        SELECT_KANJI_BY_TRANSLATION,
        WRITE_READING,
        MULTYSELECT_KANJI_READING,
        WRITE_KANJI_IN_KANJI
    }

    private List<Game> games = new ArrayList<>();

    private List<Task> tasks = new LinkedList<>();
    private int currentIndex;

    private OnGameStateChangedListener listener;

    public GameController() {
        games.add(new FlashCardGame());
        games.add(new SelectTranslationByReadingGame());
        games.add(new SelectTranslationByKanjiGame());
        games.add(new SelectReadingByKanjiGame());
        games.add(new SelectKanjiByReading());
        games.add(new SelectKanjiByTranslation());
        games.add(new WriteKanjiReadingGame());
        games.add(new MultiSelectKanjiOnKunReading());
        games.add(new WriteKanjiByReading());
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
    }

    public void startGame() {
        currentIndex = 0;
        showNextTask();
    }

    public void onUserAnswer(boolean correct) {
        // adjust learnStep
        Task task = tasks.get(currentIndex);
        if (correct) {
            task.leftGames--;
            if (!task.isComplete()) {
                shiftCurrentTaskForward();
            } else {
                currentIndex++;
            }
        } else {
            task.setupGame(GameType.FLASH_CARD, games.get(0));
            task.leftGames++;
        }
        showNextTask();
    }

    public void setOnGameStateChangedListener(OnGameStateChangedListener listener) {
        this.listener = listener;
    }

    private void showNextTask() {
        for (; currentIndex < tasks.size(); currentIndex++) {
            Task task = tasks.get(currentIndex);
            prepareTask(task);
            if (!task.isComplete()) {
                notifyNextTask(task);
                return;
            }
        }

        if (currentIndex == tasks.size()) {
            notifyGameOver();
        }
    }

    private void prepareTask(Task task) {
        if (task.gameType == null) {
            task.setupGame(GameType.FLASH_CARD, games.get(0));
        } else {
            //find next proper gameType
            GameType[] gameTypes = GameType.values();
            for (int i = task.gameType.ordinal() + 1; i < gameTypes.length; i++) {
                Game game = games.get(i);
                if (game.isValid(task)) {
                    task.setupGame(gameTypes[i], game);
                    return;
                }
                if (i == gameTypes.length - 1) i = 0;
                if (i == task.gameType.ordinal()) return;
            }
        }
    }

    private void shiftCurrentTaskForward() {
        if (currentIndex < tasks.size()) {
            Task task = tasks.remove(currentIndex);
            tasks.add(currentIndex + Math.min(2, tasks.size() - currentIndex), task);
        }
    }

    private void notifyNextTask(Task task) {
        if (listener != null) {
            listener.onNextTask(task);
        }
    }

    private void notifyGameOver() {
        if (listener != null) {
            listener.onGameOver();
        }
    }

    public interface OnGameStateChangedListener {
        void onNextTask(Task task);
        void onGameOver();
    }
}
