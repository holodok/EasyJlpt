package com.gogaworm.easyjlpt.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.gogaworm.easyjlpt.DataRepository;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.Section;
import com.gogaworm.easyjlpt.data.UserSession;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        changeFragment(R.id.nav_vocabulary_n2);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_grammar_dictionary) {
            Intent intent = new Intent(this, GrammarDictionaryActivity.class);
            intent.putExtra("userSession", new UserSession(JlptSection.VOCABULARY, JlptLevel.ALL));
            startActivity(intent);
        } else {
            changeFragment(item.getItemId());
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(int resId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        DataRepository repository = ((EasyJlptApplication) getApplication()).getRepository();
        switch (resId) {
            case R.id.nav_vocabulary_n2:
                //todo: change user session globally
                repository.setJlptSection(JlptSection.VOCABULARY);
                fragment = new SectionFragment(); //Todo: cache fragments
                break;
            case R.id.nav_kanji_n2:
                repository.setJlptSection(JlptSection.KANJI);
                fragment = new SectionFragment(); //Todo: cache fragments
                break;
            case R.id.nav_grammar_n2:
                repository.setJlptSection(JlptSection.GRAMMAR);
                fragment = new SectionFragment(); //Todo: cache fragments
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
    }

    public void showLessonList(Section section) {
        DataRepository repository = ((EasyJlptApplication) getApplication()).getRepository();
        UserSession userSession = repository.userSession;
        Intent intent = new Intent(this, LessonListActivity.class);
        intent.putExtra("userSession", userSession);
        intent.putExtra("section", section);
        startActivityForResult(intent, 100); //todo: update loader to see the progress
    }
}
