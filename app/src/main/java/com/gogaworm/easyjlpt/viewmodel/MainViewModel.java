package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.db.Course;
import com.gogaworm.easyjlpt.db.Lesson;

public class MainViewModel extends AndroidViewModel {
    final AppController controller;

    public MainViewModel(@NonNull Application application) {
        super(application);
        controller = ((EasyJlptApplication) application).getAppController();
    }

    public LiveData<Boolean> applicationInitialized() {
        return controller.applicationInitializedResult();
    }

    public LiveData<Course> getSelectedCourse() {
        return controller.getSelectedCourse();
    }

    public LiveData<Lesson> getSelectedLesson() {
        return controller.getSelectedLesson();
    }
}
