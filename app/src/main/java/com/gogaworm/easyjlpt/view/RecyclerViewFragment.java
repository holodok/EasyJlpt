package com.gogaworm.easyjlpt.view;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.model.ListViewModel;

import java.util.List;

public abstract class RecyclerViewFragment<D> extends Fragment {
    private RecyclerView recyclerView;
    private DynamicDataAdapter adapter;

    private ListViewModel<D> viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = rootView.findViewById(R.id.list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = createAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = initViewModel();
        viewModel.init(this);
        viewModel.getData().observe(this, new Observer<List<D>>() {
            @Override
            public void onChanged(@Nullable List<D> sections) {
                adapter.setData(sections);
            }
        });
    }

    protected abstract ListViewModel<D> initViewModel();

    protected void setDividerVisible(boolean visible) {
        if (visible) {
            DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.list_divider));
            recyclerView.addItemDecoration(divider);
        } else {
            recyclerView.addItemDecoration(null);
        }
    }

    protected abstract DynamicDataAdapter createAdapter(Context context);

    protected abstract class DynamicDataAdapter extends RecyclerView.Adapter<DynamicDataAdapter.ViewHolder> {
        private List<D> dataset;
        private Context context;

        protected DynamicDataAdapter(Context context) {
            this.context = context;
            dataset = createDataset();
        }

        protected abstract List<D> createDataset();

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(DynamicDataAdapter.ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.bindViewHolder(context, position, dataset.get(position));
        }

        public void setData(List<D> values) {
            dataset.clear();
            if (values != null) {
                dataset.addAll(values);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return dataset != null ? dataset.size() : 0;
        }

        protected View inflateView(ViewGroup parent, int layoutId) {
            return LayoutInflater.from(context).inflate(layoutId, parent, false);
        }

        @Override
        public int getItemViewType(int position) {
            D value = dataset.get(position);
            return getItemViewType(value);
        }

        protected abstract int getItemViewType(D value);

        protected abstract class ViewHolder extends RecyclerView.ViewHolder {
            protected ViewHolder(View v) {
                super(v);
            }

            protected abstract void bindViewHolder(Context context, int position, D value);
        }
    }
}
