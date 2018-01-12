package com.gogaworm.easyjlpt.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import com.gogaworm.easyjlpt.DataRepository;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.UserSession;

public abstract class UserSessionViewModel extends AndroidViewModel {
    private MediatorLiveData<UserSession> userSessionData;
    protected DataRepository repository;

    public UserSessionViewModel(@NonNull Application application) {
        super(application);

        userSessionData = new MediatorLiveData<>();
/*
        repository = ((EasyJlptApplication) application).getRepository();
        userSessionData.addSource(repository.getUserSession(), new Observer<UserSession>() {
            @Override
            public void onChanged(@Nullable UserSession userSession) {
                userSessionData.setValue(userSession);
                onUserSessionChanged(userSession);
            }
        });
*/
    }

    protected abstract void onUserSessionChanged(UserSession userSession);

    @Override
    protected void onCleared() {
/*
        userSessionData.removeSource(((EasyJlptApplication) getApplication()).getRepository().getUserSession());
        userSessionData.setValue(null);
        userSessionData = null;
*/
        repository = null;
    }

    protected void setJlptSection(JlptSection section) {
        repository.setJlptSection(section);
    }

    protected void setJlptLevel(JlptLevel level) {
        repository.setJlptLevel(level);
    }

    protected void setLessonId(int lessonId) {
        repository.setLessonId(lessonId);
    }

    protected void setExamId(int examId) {
        repository.setExamId(examId);
    }
}
