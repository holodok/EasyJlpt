package com.gogaworm.easyjlpt.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.gogaworm.easyjlpt.ui.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

public abstract class DynamicDataAdapter<D> extends RecyclerView.Adapter<DynamicDataAdapter.ViewHolder> {
    private List<D> dataset;
    protected Context context;
    protected RecyclerViewFragment.OnItemSelectedListener<D> itemSelectedListener;

    protected DynamicDataAdapter(Context context, RecyclerViewFragment.OnItemSelectedListener<D> itemSelectedListener) {
        this.context = context;
        this.itemSelectedListener = itemSelectedListener;
        dataset = new ArrayList<>();
    }

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
        return 1;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View parent) {
            super(parent);
            initViewHolder(parent);
        }

        protected abstract void initViewHolder(View parent);

        protected abstract void bindViewHolder(Context context, int position, D value);
    }
}