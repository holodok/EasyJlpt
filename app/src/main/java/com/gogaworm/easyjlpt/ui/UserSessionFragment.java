package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.data.UserSession;

import java.util.List;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public abstract class UserSessionFragment<V> extends Fragment implements LoaderManager.LoaderCallbacks<List<V>>  {
    protected UserSession userSession;

    public static UserSessionFragment setArguments(UserSessionFragment fragment, UserSession userSession) {
        Bundle arguments = new Bundle();
        arguments.putParcelable("userSession", userSession);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle params = savedInstanceState == null ? getArguments() : savedInstanceState;
        userSession = params.getParcelable("userSession");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("userSession", userSession);
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<List<V>> onCreateLoader(int id, Bundle args) {
        return createLoader(userSession.getFolder());
    }

    protected abstract Loader<List<V>> createLoader(String folder);

}
