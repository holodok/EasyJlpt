package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * Created on 17.07.2017.
 *
 * @author ikarpova
 */
public abstract class UserSessionLoaderFragment<V> extends UserSessionFragment implements LoaderManager.LoaderCallbacks<List<V>> {
    @Override
    public Loader<List<V>> onCreateLoader(int id, Bundle args) {
        if (args == null) args = new Bundle();
        args.putString("section", userSession.section.name());
        args.putString("level", userSession.level.name());
        return createLoader(args);
    }

    abstract Loader<List<V>> createLoader(Bundle args);
}
