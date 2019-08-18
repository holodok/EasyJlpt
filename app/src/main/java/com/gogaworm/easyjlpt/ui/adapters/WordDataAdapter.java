package com.gogaworm.easyjlpt.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Word;
import com.gogaworm.easyjlpt.ui.RecyclerViewFragment;

public class WordDataAdapter<D extends Word> extends DynamicDataAdapter<D> {
    public WordDataAdapter(Context context, RecyclerViewFragment.OnItemSelectedListener<D> itemSelectedListener) {
        super(context, itemSelectedListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflateView(viewGroup, R.layout.word_list_item);
        return new ViewHolder(view) {

            private TextView transationView;
            private TextView readingView;
            private TextView kanjiView;

            @Override
            protected void initViewHolder(View parent) {
                kanjiView = parent.findViewById(R.id.kanjiView);
                readingView = parent.findViewById(R.id.readingView);
                transationView = parent.findViewById(R.id.translationView);
            }

            @Override
            protected void bindViewHolder(Context context, int position, final D word) {
                kanjiView.setText(word.japanese);
                readingView.setText(word.reading);
                transationView.setText(word.translation);
            }
        };
    }
}
