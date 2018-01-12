package com.gogaworm.easyjlpt.game;

public abstract class GameVariant {
    private boolean answered;
    protected String japanese;
    protected String reading;
    protected String translation;

    public void set(String japanese, String reading, String translation) {
        this.japanese = japanese;
        this.reading = reading;
        this.translation = translation;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getJapanese() {
        return null;
    }

    public String getReading() {
        return null;
    }

    public String getTranslation() {
        return null;
    }

    public String getAnswer() {
        return null;
    }
}
