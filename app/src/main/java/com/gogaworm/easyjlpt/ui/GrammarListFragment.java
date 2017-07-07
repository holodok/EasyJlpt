package com.gogaworm.easyjlpt.ui;

import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.data.Grammar;

import java.util.List;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class GrammarListFragment extends UserSessionFragment<Grammar>  {
    @Override
    protected Loader<List<Grammar>> createLoader(String folder) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Grammar>> loader, List<Grammar> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Grammar>> loader) {

    }
}
