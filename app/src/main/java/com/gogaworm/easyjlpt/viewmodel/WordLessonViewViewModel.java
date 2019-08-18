package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.gogaworm.easyjlpt.db.Lesson;
import com.gogaworm.easyjlpt.db.Word;

import java.util.List;

public class WordLessonViewViewModel extends ListViewModel<Word> {
    public WordLessonViewViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<Word>> getData() {
        return controller.getWordsForSelectedLesson();
    }

    @Override
    public void setSelectedItem(Word word) {

    }

    public Lesson getSelectedLesson() {
        return controller.getSelectedLesson().getValue();
    }
}
