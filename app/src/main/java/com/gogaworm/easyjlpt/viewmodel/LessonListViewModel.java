package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.gogaworm.easyjlpt.db.Course;
import com.gogaworm.easyjlpt.db.Lesson;

import java.util.List;

public class LessonListViewModel extends ListViewModel<Lesson> {
    public LessonListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<Lesson>> getData() {
        return controller.getLessonsForSelectedCourse();
    }

    @Override
    public void setSelectedItem(Lesson lesson) {
        controller.setSelectedLesson(lesson);
    }

    public Course getSelectedCourse() {
        return controller.getSelectedCourse().getValue();
    }
}
