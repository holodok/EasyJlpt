package com.gogaworm.easyjlpt.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

public abstract class ListViewModel<D> extends AndroidViewModel {
    ListViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract void init(LifecycleOwner lifecycleOwner);

    public abstract LiveData<List<D>> getData();
}
