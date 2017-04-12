package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public abstract class UserSessionLoaderActivity<V> extends UserSessionActivity implements LoaderManager.LoaderCallbacks<List<V>>  {
    @Override
    public Loader<List<V>> onCreateLoader(int id, Bundle args) {
        return createLoader(userSession.getFolder());
    }

    @Override
    public void onLoaderReset(Loader<List<V>> loader) {

    }

    protected abstract Loader<List<V>> createLoader(String folder);
}
