package com.gogaworm.easyjlpt;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.ui.CourseListFragment;
import com.gogaworm.easyjlpt.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ContentLoadingProgressBar progressBar;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.show();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.applicationInitialized().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean result) {
                progressBar.hide();

                goToFragment(new CourseListFragment(), false);
            }
        });
    }

    public void goToFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment != null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = supportFragmentManager.beginTransaction().replace(R.id.content, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
        }
    }

    public void goToActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
