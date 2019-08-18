package com.gogaworm.easyjlpt;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import com.gogaworm.easyjlpt.db.AppDatabase;
import com.gogaworm.easyjlpt.loader.DataSyncLoader;
import com.gogaworm.easyjlpt.viewmodel.AppController;

import java.util.concurrent.Executors;

public class EasyJlptApplication extends Application {

    private AppDatabase appDatabase;
    private AppController appController;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase = Room
                .databaseBuilder(getApplicationContext(), AppDatabase.class, "easy-jlpt")
                .fallbackToDestructiveMigration()
                .build();

        MutableLiveData<Boolean> result = new MutableLiveData<>();
        DataSyncLoader dataSyncLoader = new DataSyncLoader(this, appDatabase, result);
        Executors.newSingleThreadExecutor().execute(dataSyncLoader);

        appController = new AppController(this, appDatabase, result);
    }

    public AppController getAppController() {
        return appController;
    }
}
