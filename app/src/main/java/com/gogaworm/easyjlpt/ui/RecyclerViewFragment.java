package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.ui.adapters.DynamicDataAdapter;
import com.gogaworm.easyjlpt.viewmodel.ListViewModel;

import java.util.List;

public abstract class RecyclerViewFragment<D> extends Fragment {
    protected RecyclerView recyclerView;
    private DynamicDataAdapter<D> adapter;
    private Parcelable listState;

    protected ListViewModel<D> viewModel;
    private int currentVisiblePosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            recyclerView = rootView.findViewById(getRecyclerViewId());
        } else {
            recyclerView.setId(getRecyclerViewId());
        }

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = createAdapter(getActivity(), new OnItemSelectedListener<D>() {
            @Override
            public void onItemSelected(D item) {
                RecyclerViewFragment.this.onItemSelected(item);
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel = initViewModel();
        viewModel.getData().observe(this, new Observer<List<D>>() {
            @Override
            public void onChanged(@Nullable List<D> data) {
                adapter.setData(data);
/*
                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
*/
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        // this variable should be static in class
        currentVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.getLayoutManager().scrollToPosition(currentVisiblePosition);
            }
        }, 300);
    }

    /*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(getClass().getName() + ".ListState");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getClass().getName() + ".ListState", recyclerView.getLayoutManager().onSaveInstanceState());
    }
*/

    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    protected abstract int getRecyclerViewId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*
        adapter = createAdapter(getActivity(), new OnItemSelectedListener<D>() {
            @Override
            public void onItemSelected(D item) {
                RecyclerViewFragment.this.onItemSelected(item);
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel = initViewModel();
        viewModel.getData().observe(this, new Observer<List<D>>() {
            @Override
            public void onChanged(@Nullable List<D> data) {
                adapter.setData(data);
                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
            }
        });
*/

    }

    protected abstract ListViewModel<D> initViewModel();

/*
    protected void setDividerVisible(boolean visible) {
        if (visible) {
            DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.list_divider));
            recyclerView.addItemDecoration(divider);
        } else {
            recyclerView.addItemDecoration(null);
        }
    }
*/

    protected abstract DynamicDataAdapter createAdapter(Context context, OnItemSelectedListener<D> itemSelectedListener);

    protected void onItemSelected(D item) {
        viewModel.setSelectedItem(item);
    }

    public interface OnItemSelectedListener<D> {
        void onItemSelected(D item);
    }
}
