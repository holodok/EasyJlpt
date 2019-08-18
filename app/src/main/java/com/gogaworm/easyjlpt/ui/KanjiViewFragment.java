package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Expression;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.ui.adapters.DynamicDataAdapter;
import com.gogaworm.easyjlpt.ui.adapters.WordDataAdapter;
import com.gogaworm.easyjlpt.utils.KanjiUtils;
import com.gogaworm.easyjlpt.viewmodel.KanjiViewViewModel;
import com.gogaworm.easyjlpt.viewmodel.ListViewModel;

public class KanjiViewFragment extends RecyclerViewFragment<Expression> {
    private TextView kanjiView;
    private TextView readingView;
    private TextView transationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        kanjiView = rootView.findViewById(R.id.japanese);
        readingView = rootView.findViewById(R.id.reading);
        transationView = rootView.findViewById(R.id.translation);

        return rootView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kanji_view;
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.kanjiViewRecyclerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((KanjiViewViewModel) viewModel).getSelectedKanji().observe(this, new Observer<Kanji>() {
            @Override
            public void onChanged(@Nullable Kanji kanji) {
                if (kanji == null) return;

                kanjiView.setText(kanji.kanji);
                readingView.setText(KanjiUtils.getKanjiReading(getActivity(), kanji));
                transationView.setText(kanji.translation);
            }
        });
    }

    @Override
    protected ListViewModel<Expression> initViewModel() {
        return ViewModelProviders.of(this).get(KanjiViewViewModel.class);
    }

    @Override
    protected DynamicDataAdapter createAdapter(Context context, OnItemSelectedListener<Expression> itemSelectedListener) {
        return new WordDataAdapter<>(context, itemSelectedListener);
    }
}
