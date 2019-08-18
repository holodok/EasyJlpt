package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.MainActivity;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Kanji;
import com.gogaworm.easyjlpt.ui.adapters.DynamicDataAdapter;
import com.gogaworm.easyjlpt.utils.KanjiUtils;
import com.gogaworm.easyjlpt.viewmodel.KanjiLessonViewViewModel;
import com.gogaworm.easyjlpt.viewmodel.ListViewModel;

public class KanjiLessonViewFragment  extends RecyclerViewFragment<Kanji> {
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitle(((KanjiLessonViewViewModel)viewModel).getSelectedLesson().title);
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.kanjiLessonRecyclerView;
    }

    @Override
    protected ListViewModel<Kanji> initViewModel() {
        return ViewModelProviders.of(this).get(KanjiLessonViewViewModel.class);
    }

    @Override
    protected void onItemSelected(Kanji item) {
        super.onItemSelected(item);
        MainActivity activity = (MainActivity) getActivity();
        activity.goToFragment(new KanjiViewFragment(), true);
    }

    @Override
    protected DynamicDataAdapter createAdapter(Context context, OnItemSelectedListener<Kanji> itemSelectedListener) {
        return new DynamicDataAdapter<Kanji>(context, itemSelectedListener) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = inflateView(viewGroup, R.layout.kanji_list_item);
                return new ViewHolder(view) {

                    private TextView transationView;
                    private View parent;
                    private TextView readingView;
                    private TextView kanjiView;

                    @Override
                    protected void initViewHolder(View parent) {
                        kanjiView = parent.findViewById(R.id.kanjiView);
                        readingView = parent.findViewById(R.id.readingView);
                        transationView = parent.findViewById(R.id.translationView);
                        this.parent = parent;
                    }

                    @Override
                    protected void bindViewHolder(Context context, int position, final Kanji kanji) {
                        parent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                itemSelectedListener.onItemSelected(kanji);
                            }
                        });
                        kanjiView.setText(kanji.kanji);
                        readingView.setText(KanjiUtils.getKanjiReading(getContext(), kanji), TextView.BufferType.SPANNABLE);
                        transationView.setText(kanji.translation);
                    }
                };
            }
        };
    }
}
