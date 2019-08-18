package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.gogaworm.easyjlpt.EasyJlptApplication;

import java.util.List;

public abstract class ListViewModel<D> extends AndroidViewModel {
    final AppController controller;

    ListViewModel(@NonNull Application application) {
        super(application);
        controller = ((EasyJlptApplication) application).getAppController();
    }

    public abstract LiveData<List<D>> getData();

    public abstract void setSelectedItem(D item);
}
