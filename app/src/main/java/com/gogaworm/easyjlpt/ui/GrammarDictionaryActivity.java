package com.gogaworm.easyjlpt.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.Loader;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.*;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;
import com.gogaworm.easyjlpt.utils.Constants;

import java.util.List;

/**
 * Created on 16.10.2017.
 *
 * @author ikarpova
 */
public class GrammarDictionaryActivity extends UserSessionLoaderActivity<Grammar> {
    private ProgressDialog progressDialog;
    private Grammar grammarItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = UserSessionFragment.setArguments(new GrammarDictionaryFragment(), new UserSession(JlptSection.GRAMMAR, JlptLevel.ALL));
        fragmentManager.beginTransaction().add(R.id.content, fragment).commit();

        getSupportActionBar().setTitle(R.string.menu_grammar_dictionary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //prepare progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (grammarItem != null) {
                    Fragment fragment = GrammarItemFragment.getInstance(userSession, grammarItem);
                    getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
                }
            }
        });

        getSupportLoaderManager().initLoader(Constants.LOADER_ID_GRAMMAR_LIST, null, this);
        progressDialog.show();

    }

    void showGrammarItem(GrammarSearchResult searchResult) {
        Bundle args = new Bundle();
        args.putString("section", searchResult.section.name());
        args.putString("level", searchResult.level.name());
        args.putInt("lessonId", searchResult.lessonId);
        getSupportLoaderManager().restartLoader(Constants.LOADER_ID_GRAMMAR_LIST, args, this);
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
    public void onLoadFinished(Loader<List<Grammar>> loader, List<Grammar> data) {
        grammarItem = data != null && data.size() > 0 ? data.get(0) : null;
    }

    @Override
    protected Loader<List<Grammar>> createLoader(Bundle args) {
        return LoaderFactory.getViewListLoader(this, args);
    }
}
