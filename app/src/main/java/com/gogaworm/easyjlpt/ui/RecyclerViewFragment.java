package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gogaworm.easyjlpt.R;

import java.util.List;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public abstract class RecyclerViewFragment<V, D> extends UserSessionLoaderFragment<V> {
    private RecyclerView recyclerView;
    private DynamicDataAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = createAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    protected void setDividerVisible(boolean visible) {
        if (visible) {
            DividerItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            divider.setDrawable(getResources().getDrawable(R.drawable.list_divider));
            recyclerView.addItemDecoration(divider);
        } else {
            recyclerView.addItemDecoration(null);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(getLoaderId(), null, this).forceLoad();
    }

    @Override
    public void onLoadFinished(Loader<List<V>> loader, List<V> data) {
        adapter.setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<V>> loader) {}

    protected abstract int getLoaderId();

    abstract DynamicDataAdapter createAdapter(Context context);

    abstract class DynamicDataAdapter extends RecyclerView.Adapter<DynamicDataAdapter.ViewHolder> {
        private List<D> dataset;
        private Context context;

        DynamicDataAdapter(Context context) {
            this.context = context;
            dataset = createDataset();
        }

        protected abstract List<D> createDataset();

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.bindViewHolder(context, position, dataset.get(position));
        }

        public void setData(List<V> values) {
            dataset.clear();
            if (values != null) {
                setData(dataset, values);
            }
            notifyDataSetChanged();
        }

        protected abstract void setData(List<D> dataset, List<V> values);

        @Override
        public int getItemCount() {
            return dataset != null ? dataset.size() : 0;
        }

        View inflateView(ViewGroup parent, int layoutId) {
            return LayoutInflater.from(context).inflate(layoutId, parent, false);
        }

        @Override
        public int getItemViewType(int position) {
            D value = dataset.get(position);
            return getItemViewType(value);
        }

        protected abstract int getItemViewType(D value);

        abstract class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View v) {
                super(v);
            }

            protected abstract void bindViewHolder(Context context, int position, D value);
        }
    }
}
