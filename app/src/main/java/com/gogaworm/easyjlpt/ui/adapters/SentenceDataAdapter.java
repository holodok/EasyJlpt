package com.gogaworm.easyjlpt.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Sentence;
import com.gogaworm.easyjlpt.ui.RecyclerViewFragment;
import com.gogaworm.easyjlpt.ui.widget.UnitedKanjiKanaSpannableString;

public class SentenceDataAdapter extends DynamicDataAdapter<Sentence> {
    public SentenceDataAdapter(Context context, RecyclerViewFragment.OnItemSelectedListener<Sentence> itemSelectedListener) {
        super(context, itemSelectedListener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflateView(viewGroup, R.layout.sentence_list_item);
        return new ViewHolder(view) {

            private TextView transationView;
            private TextView kanjiView;

            @Override
            protected void initViewHolder(View parent) {
                kanjiView = parent.findViewById(R.id.kanjiView);
                transationView = parent.findViewById(R.id.translationView);
            }

            @Override
            protected void bindViewHolder(Context context, int position, final Sentence sentence) {
                kanjiView.setText(new UnitedKanjiKanaSpannableString(sentence.japanese));
                transationView.setText(sentence.translation);
            }
        };
    }
}
