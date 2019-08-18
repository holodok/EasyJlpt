package com.gogaworm.easyjlpt.games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameController<D> {
    private int maxGamesPerItem = 3;
    private int maxGamesCountOnCurrentLevel = 5;
    private int maxLevel = 3;

    private final List<GameLevel> gameSequence = new ArrayList<>();
    private int currentLevel;
    private int currentLevelLeftGamesCount;
    private int minimunLevel;
    private Random random;

    private int correct;
    private int wrong;
    private int totalTasks;
    private int leftTasks;

    public void initialize(List<GameTemplate<D>> gameTemplates, List<D> learnedData) {
        createLearnedWords(gameTemplates, learnedData);

        random = new Random(System.currentTimeMillis());
    }

    private void createLearnedWords(List<GameTemplate<D>> gameTemplates, List<D> learnedData) {
        gameSequence.clear();
        leftTasks = 0;
        correct = 0;
        wrong = 0;

        for (int i = 0; i < gameTemplates.size(); i++) {
            gameSequence.add(new GameLevel());
        }
        for (D learnedDatum : learnedData) {
            LearnedItem learnedItem =  new LearnedItem();
            for (int i = 0; i < gameTemplates.size(); i++) {
                GameTemplate<D> gameTemplate = gameTemplates.get(i);
                if (gameTemplate.canUseGame(learnedDatum)) {
                    GameTemplate.Game<D>[] games = gameTemplate.createGame(learnedDatum, learnedData);
                    for (int j = 0; j < maxGamesPerItem && j < games.length; j++) {
                        learnedItem.addGames(games[j]);
                        leftTasks ++;
                    }
/*
                    learnedItem.addGames(games);
                    leftTasks += games.length;
*/
                }
            }
            gameSequence.get(0).addLearnedItem(learnedItem);
        }

        totalTasks = leftTasks;
        currentLevel = 0;
        currentLevelLeftGamesCount = Math.min(Math.max(maxGamesCountOnCurrentLevel - currentLevel, 1), gameSequence.get(currentLevel).size());
    }

    public GameTemplate.Game<D> getNextGameInstance() {
        boolean levelReset = false;
        while (currentLevelLeftGamesCount == 0) {
            levelReset = true;
            if (gameSequence.get(currentLevel).size() == 0) {
                gameSequence.remove(currentLevel);
            } else {
                currentLevel++;
            }
            if (currentLevel == gameSequence.size() || currentLevel == maxLevel) { //last level, go to first again
                if (leftTasks == 0) {
                    return null; //game over
                }
                currentLevel = 0;
            }
            currentLevelLeftGamesCount = Math.min(Math.max(maxGamesCountOnCurrentLevel - currentLevel, 1), gameSequence.get(currentLevel).size());
        }
        currentLevelLeftGamesCount--;

        return gameSequence.get(currentLevel).getNextGame(levelReset);
    }

    public void gameFinished(boolean result) {
        GameLevel gameLevel = gameSequence.get(currentLevel);
        if (result) {
            LearnedItem learnedItem = gameLevel.removeSuccessGame();
            if (learnedItem != null) {
                if (currentLevel + 1 >= gameSequence.size()) {
                    gameSequence.add(new GameLevel());
                }
                GameLevel nextGameLevel = gameSequence.get(currentLevel + 1);
                nextGameLevel.addLearnedItem(learnedItem);
            }
            correct++;
            leftTasks--;
        } else {
            wrong++;
            boolean shouldDecreaseGameCount = gameLevel.shouldDecreaseGameCountAfterAddFailedGame();
            if (shouldDecreaseGameCount) {
                currentLevelLeftGamesCount = Math.min(gameLevel.size() - 1, currentLevelLeftGamesCount);
            }
        }
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getLeftTasks() {
        return leftTasks;
    }

    public int getCorrect() {
        return correct;
    }

    public int getWrong() {
        return wrong;
    }

    private class LearnedItem {
        private final List<GameTemplate.Game<D>> games = new ArrayList<>();
        private int index;

        LearnedItem() {
        }

        void addGames(GameTemplate.Game<D>... games) {
            this.games.addAll(Arrays.asList(games));
        }

        private GameTemplate.Game<D> getNextGame() {
            if (index == games.size()) {
                return null;
            }
            return games.get(index);
        }

        private boolean noGamesLeft() {
            return ++index >= games.size();
        }
    }

    private class GameLevel {
        List<LearnedItem> learnedItems = new ArrayList<>();
        int index;

        void addLearnedItem(LearnedItem item) {
            learnedItems.add(item);
        }

        int size() {
            return learnedItems.size();
        }

        GameTemplate.Game<D> getNextGame(boolean levelReset) {
            if (levelReset) {
                index = 0;
            }
            LearnedItem learnedItem = learnedItems.get(index);
            return learnedItem.getNextGame();
        }

        LearnedItem removeSuccessGame() {
            LearnedItem learnedItem = learnedItems.remove(index);
            if (index >= learnedItems.size()) {
                index = 0;
            }
            return learnedItem.noGamesLeft() ? null : learnedItem;
        }

        boolean shouldDecreaseGameCountAfterAddFailedGame() {
            if (++index >= learnedItems.size()) {
                index = 0;
                return true;
            }
            return false;
        }
    }
}
