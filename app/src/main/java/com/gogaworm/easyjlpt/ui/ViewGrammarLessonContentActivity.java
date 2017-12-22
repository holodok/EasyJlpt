package com.gogaworm.easyjlpt.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Grammar;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;
import com.gogaworm.easyjlpt.utils.UnitedKanjiKanaSpannableString;

import java.util.ArrayList;
import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_GRAMMAR_LIST;

/**
 * Created on 17.07.2017.
 *
 * @author ikarpova
 */
public class ViewGrammarLessonContentActivity extends UserSessionLoaderActivity<Grammar> {
    private Lesson lesson;
    private CollectionPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        lesson = getIntent().getParcelableExtra("lesson");
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(UnitedKanjiKanaSpannableString.getKanjiFromReading(lesson.title.japanese));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new CollectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_GRAMMAR_LIST;
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_grammar_lesson_content;
    }

    @Override
    protected Loader<List<Grammar>> createLoader(Bundle args) {
        args.putInt("lessonId", lesson.trainId);
        return LoaderFactory.getViewListLoader(this, args);
    }

    @Override
    protected void initData(List<Grammar> data) {
        adapter.updateData(data);
    }

    @Override
    protected void showFragment() {

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
            return GrammarItemFragment.getInstance(userSession, values.get(position));
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
