package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.gogaworm.easyjlpt.db.Course;

import java.util.List;

public class CourseListViewModel extends ListViewModel<Course> {
    public CourseListViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<Course>> getData() {
        return controller.getCourses();
    }

    @Override
    public void setSelectedItem(Course course) {
        controller.setSelectedCourse(course);
    }
}
