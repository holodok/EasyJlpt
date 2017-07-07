package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.loaders.WordListLoader;
import com.gogaworm.easyjlpt.ui.widgets.ArcProgress;
import com.gogaworm.easyjlpt.ui.widgets.KanjiKanaView;

import java.util.ArrayList;
import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_WORD_LIST;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class WordListFragment extends RecyclerViewFragment<Word, Word> {

    private Lesson lesson;

    public static WordListFragment getInstance(UserSession userSession, Lesson lesson) {
        WordListFragment fragment = new WordListFragment();
        UserSessionFragment.setArguments(fragment, userSession);
        fragment.getArguments().putParcelable("lesson", lesson);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lesson = getArguments().getParcelable("lesson");
    }

    @Override
    protected Loader<List<Word>> createLoader(String folder) {
        return new WordListLoader(getContext(), userSession.getFolder(), lesson.trainId);
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_WORD_LIST;
    }

    @Override
    DynamicDataAdapter createAdapter(Context context) {
        return new WordListAdapter(context);
    }

    class WordListAdapter extends DynamicDataAdapter {

        WordListAdapter(Context context) {
            super(context);
        }

        @Override
        protected List<Word> createDataset() {
            return new ArrayList<>();
        }

        @Override
        protected void setData(List<Word> dataset, List<Word> values) {
            dataset.addAll(values);
        }

        @Override
        protected int getItemViewType(Word value) {
            return 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WordListAdapter.WordViewHolder(inflateView(parent, R.layout.list_item_section));
        }

        class WordViewHolder extends ViewHolder {
            private final ArcProgress progressView;
            private final KanjiKanaView kanjiView;
            private final TextView translationView;
            private final View openButton;
            // each data item is just a string in this case


            WordViewHolder(View view) {
                super(view);
                progressView = (ArcProgress) view.findViewById(R.id.progress);
                kanjiView = (KanjiKanaView) view.findViewById(R.id.kanjiView);
                translationView = (TextView) view.findViewById(R.id.translation);
                openButton = view.findViewById(R.id.viewButton);
            }

            @Override
            protected void bindViewHolder(final Context context, final Word value) {
                progressView.setProgress(0);
                kanjiView.setText(value.japanese, value.reading);
                translationView.setText(value.translation);
                openButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
    }
}
