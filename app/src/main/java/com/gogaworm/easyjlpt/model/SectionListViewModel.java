package com.gogaworm.easyjlpt.model;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gogaworm.easyjlpt.DataRepository;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.data.Section;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.loaders.SectionLoader;

import java.util.List;

public class SectionListViewModel extends ListViewModel<Section> {
    private MutableLiveData<List<Section>> sections;
    private final DataRepository repository;

    public SectionListViewModel(@NonNull Application application) {
        super(application);

        sections = new MutableLiveData<>();
        repository = ((EasyJlptApplication) application).getRepository();
    }

    @Override
    public void init(LifecycleOwner lifecycleOwner) {
        repository.getUserSession().observe(lifecycleOwner, new Observer<UserSession>() {
            @Override
            public void onChanged(@Nullable UserSession userSession) {
                if (userSession == null) {
                    sections.setValue(null);
                    return;
                }
                final SectionLoader loader = new SectionLoader(getApplication(), userSession.section, userSession.level);
                new AsyncTask<Void, Void, List<Section>>() {
                    @Override
                    protected List<Section> doInBackground(Void... voids) {
                        return loader.loadInBackground();
                    }

                    @Override
                    protected void onPostExecute(List<Section> results) {
                        super.onPostExecute(results);
                        sections.setValue(results);
                    }
                }.executeOnExecutor(((EasyJlptApplication)getApplication()).getExecutors().diskIO());
            }
        });
    }

    public LiveData<List<Section>> getData() {
        return sections;
    }
}
