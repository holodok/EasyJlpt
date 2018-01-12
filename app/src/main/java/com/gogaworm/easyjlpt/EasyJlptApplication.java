package com.gogaworm.easyjlpt;

import android.app.Application;
import com.gogaworm.easyjlpt.database.AppDatabase;

public class EasyJlptApplication extends Application {
    private AppExecutors executors;

    @Override
    public void onCreate() {
        super.onCreate();

        executors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, executors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }

    public AppExecutors getExecutors() {
        return executors;
    }
}
