package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Section;
import com.gogaworm.easyjlpt.loaders.SectionLoader;
import com.gogaworm.easyjlpt.ui.widgets.ArcProgress;
import com.gogaworm.easyjlpt.ui.widgets.KanjiKanaView;

import java.util.ArrayList;
import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_SECTION;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class SectionFragment extends JlptListFragment<Section, Section> {

    @Override
    protected int getLoaderId() {
        return LOADER_ID_SECTION;
    }

    @Override
    DynamicDataAdapter createAdapter(Context context) {
        return new SectionAdapter(context);
    }

    @Override
    protected Loader<List<Section>> createLoader(String folder) {
        return new SectionLoader(getActivity(), folder);
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
        protected void setData(List<Section> dataset, List<Section> values) {
            dataset.addAll(values);
        }

        @Override
        protected int getItemViewType(Section value) {
            return 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflateView(parent, R.layout.list_item_section);
            return new SectionViewHolder(view);
        }

        class SectionViewHolder extends ViewHolder {
            private final ArcProgress progressView;
            private final KanjiKanaView kanjiView;
            private final TextView translationView;
            private final View openButton;
            // each data item is just a string in this case


            SectionViewHolder(View view) {
                super(view);
                progressView = (ArcProgress) view.findViewById(R.id.progress);
                kanjiView = (KanjiKanaView) view.findViewById(R.id.kanjiView);
                translationView = (TextView) view.findViewById(R.id.translation);
                openButton = view.findViewById(R.id.openButton);
            }

            @Override
            protected void bindViewHolder(final Context context, final Section value) {
                progressView.setProgress(0);
                kanjiView.setText(value.title.japanese, value.title.reading);
                translationView.setText(value.title.translation);
                openButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LessonListActivity.class);
                        intent.putExtra("userSession", userSession);
                        intent.putExtra("section", value);
                        startActivityForResult(intent, 100); //todo: update loader to see the progress
                    }
                });
            }
        }
    }
}
