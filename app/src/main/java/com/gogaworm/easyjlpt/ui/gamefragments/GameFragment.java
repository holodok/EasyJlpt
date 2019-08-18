package com.gogaworm.easyjlpt.ui.gamefragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;

public abstract class GameFragment<D> extends Fragment {
    protected GenericGamesLearnWordsViewModel<D> viewModel;
    protected D datum;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(getViewModelClass());
    }

    protected abstract Class<? extends GenericGamesLearnWordsViewModel<D>> getViewModelClass();
}
