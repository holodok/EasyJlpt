package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.games.GameTemplate;
import com.gogaworm.easyjlpt.games.WordViewGameTemplate;

import java.util.List;

public class ReviewWordsViewModel extends LearnWordsViewModel {
    public ReviewWordsViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void createGames(List<GameTemplate<Word>> gameTemplates) {
        gameTemplates.add(new WordViewGameTemplate());
    }
}
