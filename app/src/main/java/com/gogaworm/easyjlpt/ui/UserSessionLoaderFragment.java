package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;

import java.util.List;

/**
 * Created on 17.07.2017.
 *
 * @author ikarpova
 */
public abstract class UserSessionLoaderFragment<V> extends UserSessionFragment implements LoaderManager.LoaderCallbacks<List<V>> {
    @Override
    public Loader<List<V>> onCreateLoader(int id, Bundle args) {
        return createLoader(userSession.section, userSession.level);
    }

    abstract Loader<List<V>> createLoader(JlptSection section, JlptLevel level);
}
