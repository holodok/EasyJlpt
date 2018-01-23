package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.UserSession;

/**
 * Created on 29.03.2017.
 *
 * @author ikarpova
 */
public class UserSessionActivity extends AppCompatActivity {
    protected UserSession userSession;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());

        userSession = getIntent().getParcelableExtra("userSession");

        findViewById(R.id.progress).setVisibility(View.GONE);
    }

    protected int getContentViewResId() {
        return R.layout.activity_user_session;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
