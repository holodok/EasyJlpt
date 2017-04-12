package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Section;

/**
 * Created on 28.03.2017.
 *
 * @author ikarpova
 */
public class LessonListActivity extends UserSessionActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Section section = getIntent().getParcelableExtra("section");

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = LessonFragment.getInstance(userSession, section.id);
        fragmentManager.beginTransaction().add(R.id.content, fragment).commit();

        getSupportActionBar().setTitle(section.title.japanese);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
