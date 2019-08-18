package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.MainActivity;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.ui.adapters.DynamicDataAdapter;
import com.gogaworm.easyjlpt.ui.adapters.WordDataAdapter;
import com.gogaworm.easyjlpt.viewmodel.ListViewModel;
import com.gogaworm.easyjlpt.viewmodel.WordLessonViewViewModel;

public class WordLessonViewFragment extends RecyclerViewFragment<Word> {
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitle(((WordLessonViewViewModel)viewModel).getSelectedLesson().title);
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.wordLessonViewRecyclerView;
    }

    @Override
    protected ListViewModel<Word> initViewModel() {
        return ViewModelProviders.of(this).get(WordLessonViewViewModel.class);
    }

    @Override
    protected DynamicDataAdapter createAdapter(Context context, OnItemSelectedListener<Word> itemSelectedListener) {
        return new WordDataAdapter<>(context, itemSelectedListener);
    }
}
