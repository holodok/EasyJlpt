package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;

import java.util.List;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public abstract class UserSessionLoaderActivity<V> extends UserSessionActivity implements LoaderManager.LoaderCallbacks<List<V>>  {
    @Override
    public Loader<List<V>> onCreateLoader(int id, Bundle args) {
        if (args == null) args = new Bundle();
        args.putString("section", userSession.section.name());
        args.putString("level", userSession.level.name());
        return createLoader(args);
    }

    @Override
    public void onLoaderReset(Loader<List<V>> loader) {

    }

    protected abstract Loader<List<V>> createLoader(Bundle args);
}
