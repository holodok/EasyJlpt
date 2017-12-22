package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.data.GrammarSearchResult;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;

import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_GRAMMAR_LIST;

/**
 * Created on 16.10.2017.
 *
 * @author ikarpova
 */
public class GrammarDictionaryActivity extends UserSessionLoaderActivity<Grammar> {
    private Grammar grammarItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.menu_grammar_dictionary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_GRAMMAR_LIST;
    }

    void showGrammarItem(GrammarSearchResult searchResult) {
        Bundle args = new Bundle();
        args.putString("section", searchResult.section.name());
        args.putString("level", searchResult.level.name());
        args.putInt("lessonId", searchResult.lessonId);
        getSupportLoaderManager().restartLoader(LOADER_ID_GRAMMAR_LIST, args, this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected Loader<List<Grammar>> createLoader(Bundle args) {
        return LoaderFactory.getViewListLoader(this, args);
    }

    @Override
    protected void initData(List<Grammar> data) {
        grammarItem = data != null && data.size() > 0 ? data.get(0) : null;
    }

    @Override
    protected void showFragment() {
        if (grammarItem != null) {
            Fragment fragment = GrammarItemFragment.getInstance(userSession, grammarItem);
            getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
        }
    }
}
