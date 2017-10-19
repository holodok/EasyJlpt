package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.GrammarSearchResult;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;

import java.util.LinkedList;
import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_GRAMMAR_LIST;

/**
 * Created on 15.10.2017.
 *
 * @author ikarpova
 */
public class GrammarDictionaryFragment extends RecyclerViewFragment<GrammarSearchResult, GrammarSearchResult> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setDividerVisible(true);
        return view;
    }

    @Override
    Loader<List<GrammarSearchResult>> createLoader(Bundle args) {
        return LoaderFactory.getSearchLoader(getContext(), args);
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_GRAMMAR_LIST + 1;
    }

    @Override
    DynamicDataAdapter createAdapter(Context context) {
        return new GrammarDictionaryAdapter(context);
    }

    class GrammarDictionaryAdapter extends DynamicDataAdapter {

        GrammarDictionaryAdapter(Context context) {
            super(context);
        }

        @Override
        protected List<GrammarSearchResult> createDataset() {
            return new LinkedList<>();
        }

        @Override
        protected void setData(List<GrammarSearchResult> dataset, List<GrammarSearchResult> values) {
            dataset.addAll(values);
        }

        @Override
        protected int getItemViewType(GrammarSearchResult value) {
            return 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflateView(parent, R.layout.list_item_grammar_search_result);
            return new GrammarDictionaryViewHolder(view);
        }

        class GrammarDictionaryViewHolder extends ViewHolder {
            private final TextView itemView;
            private final TextView meaningView;

            GrammarDictionaryViewHolder(View view) {
                super(view);
                itemView = (TextView) view.findViewById(R.id.grammarItem);
                meaningView = (TextView) view.findViewById(R.id.grammarMeaning);
            }

            @Override
            protected void bindViewHolder(Context context, int position, final GrammarSearchResult value) {
                itemView.setText(value.item);
                meaningView.setText(value.meaning.meaning.translation);
                getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((GrammarDictionaryActivity) getContext()).showGrammarItem(value);
                    }
                });
            }
        }
    }
}
