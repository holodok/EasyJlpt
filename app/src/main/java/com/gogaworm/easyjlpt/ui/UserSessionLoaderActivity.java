package com.gogaworm.easyjlpt.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import com.gogaworm.easyjlpt.R;

import java.util.List;

import static android.text.TextUtils.isEmpty;

/**
 * Created on 03.04.2017.
 *
 * @author ikarpova
 */
public abstract class UserSessionLoaderActivity<V> extends UserSessionActivity implements LoaderManager.LoaderCallbacks<List<V>>  {

    private ActionBar actionBar;
    private ProgressDialog progressDialog;
    private CharSequence onBackPressedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();

        //prepare progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.messageLoading));
        progressDialog.setIndeterminate(true);
        progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                showFragment();
            }
        });

        getSupportLoaderManager().initLoader(getLoaderId(), null, this).forceLoad();
        progressDialog.show();
    }

    protected abstract int getLoaderId();

    @Override
    public Loader<List<V>> onCreateLoader(int id, Bundle args) {
        if (args == null) args = new Bundle();
        args.putString("section", userSession.section.name());
        args.putString("level", userSession.level.name());
        return createLoader(args);
    }

    @Override
    public void onLoaderReset(Loader<List<V>> loader) {}

    public void onLoadFinished(Loader<List<V>> loader, List<V> data) {
        initData(data);
        progressDialog.dismiss();
    }

    protected abstract Loader<List<V>> createLoader(Bundle args);

    protected abstract void initData(List<V> data);

    protected abstract void showFragment();

    protected void updateLeftCount(int count) {
        if (count > 0) {
            actionBar.setTitle(getString(R.string.title_flash_cards, count));
        }
    }

    protected void setOnBackPressedText(CharSequence onBackPressedText) {
        this.onBackPressedText = onBackPressedText;
    }

    @Override
    public void onBackPressed() {
        if (isEmpty(onBackPressedText)) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.messageLeaveFlashCards)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
    }
}
