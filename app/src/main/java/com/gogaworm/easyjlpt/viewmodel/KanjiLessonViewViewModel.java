package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.db.Lesson;

import java.util.List;

public class KanjiLessonViewViewModel extends ListViewModel<Kanji> {

    public KanjiLessonViewViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<Kanji>> getData() {
        return controller.getKanjiForSelectedLesson();
    }

    @Override
    public void setSelectedItem(Kanji kanji) {
        controller.setSelectedKanji(kanji);
    }

    public Lesson getSelectedLesson() {
        return controller.getSelectedLesson().getValue();
    }
}
