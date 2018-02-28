package com.gogaworm.easyjlpt.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Section;
import com.gogaworm.easyjlpt.model.ListViewModel;
import com.gogaworm.easyjlpt.model.SectionListViewModel;
import com.gogaworm.easyjlpt.ui.widgets.ArcProgress;
import com.gogaworm.easyjlpt.utils.UnitedKanjiKanaSpannableString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class SectionFragment extends com.gogaworm.easyjlpt.view.RecyclerViewFragment<Section> {

    @Override
    protected ListViewModel<Section> initViewModel() {
        return ViewModelProviders.of(this).get(SectionListViewModel.class);
    }

    @Override
    protected DynamicDataAdapter createAdapter(Context context) {
        return new SectionAdapter(context);
    }

    class SectionAdapter extends DynamicDataAdapter {

        SectionAdapter(Context context) {
            super(context);
        }

        @Override
        protected List<Section> createDataset() {
            return new ArrayList<>();
        }

        @Override
        protected int getItemViewType(Section value) {
            return 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SectionViewHolder(inflateView(parent, R.layout.list_item_section));
        }

        class SectionViewHolder extends ViewHolder {
            private final ArcProgress progressView;
            private final TextView kanjiView;
            private final TextView translationView;
            private final View openButton;
            // each data item is just a string in this case

            SectionViewHolder(View view) {
                super(view);
                progressView = view.findViewById(R.id.progress);
                kanjiView = view.findViewById(R.id.kanjiView);
                translationView = view.findViewById(R.id.translation);
                openButton = view.findViewById(R.id.viewButton);
            }

            @Override
            protected void bindViewHolder(final Context context, int position, final Section section) {
                progressView.setProgress(0);
                kanjiView.setText(new UnitedKanjiKanaSpannableString(section.title.japanese));
                translationView.setText(section.title.translation);
                openButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getContext()).showLessonList(section);
                    }
                });
            }
        }
    }
}
