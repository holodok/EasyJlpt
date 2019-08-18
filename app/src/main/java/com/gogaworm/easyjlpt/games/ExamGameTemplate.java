package com.gogaworm.easyjlpt.games;

import com.gogaworm.easyjlpt.db.Exam;

import java.util.List;

public class ExamGameTemplate implements GameTemplate<Exam> {
    @Override
    public boolean canUseGame(Exam datum) {
        return true;
    }

    @Override
    public Game<Exam>[] createGame(Exam datum, List<Exam> allData) {
        return new Game[] {
                new Game<Exam>() {
                    @Override
                    public GameTemplate getGame() {
                        return ExamGameTemplate.this;
                    }

                    @Override
                    public Exam getItem() {
                        return datum;
                    }

                    @Override
                    public AnswerVariant[] getVariants() {
                        return null;
                    }

                    @Override
                    public String getTaskText() {
                        return datum.japanese;
                    }
                }
        };
    }
}
