package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.data.UserSession;

import java.util.List;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public abstract class JlptListFragment<V, D> extends RecyclerViewFragment<V, D> {
    protected UserSession userSession;

    public static JlptListFragment setArguments(JlptListFragment fragment, UserSession userSession) {
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
