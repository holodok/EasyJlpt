package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.loaders.GrammarLoader;
import com.gogaworm.easyjlpt.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 17.07.2017.
 *
 * @author ikarpova
 */
public class ViewGrammarLessonContentActivity extends UserSessionLoaderActivity<Grammar> {
    private int lessonId;
    private CollectionPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lessonId = getIntent().getIntExtra("lessonId", 0);

/*
        getSupportActionBar().setTitle(lesson.title.japanese);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new CollectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        getSupportLoaderManager().initLoader(Constants.LOADER_ID_GRAMMAR_LIST, null, this).forceLoad();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_grammar_lesson_content;
    }

    @Override
    protected Loader<List<Grammar>> createLoader(String folder) {
        return new GrammarLoader(this, userSession.getFolder(), lessonId);
    }

    @Override
    public void onLoadFinished(Loader<List<Grammar>> loader, List<Grammar> data) {
        adapter.updateData(data);
    }

    // Since this is an object collection, use a FragmentStatePagerAdapter,
    // and NOT a FragmentPagerAdapter.
    class CollectionPagerAdapter extends FragmentStatePagerAdapter {
        List<Grammar> values = new ArrayList<>();

        CollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void updateData(List<Grammar> values) {
            this.values.clear();
            this.values.addAll(values);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return GrammarListFragment.getInstance(userSession, values.get(position));
        }

        @Override
        public int getCount() {
            return values.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    }
}
