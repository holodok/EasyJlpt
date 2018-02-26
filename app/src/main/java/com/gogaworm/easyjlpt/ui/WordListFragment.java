package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_WORD_LIST;
import static com.gogaworm.easyjlpt.utils.KanjiUtils.getReading;

/**
 * Created on 05.07.2017.
 *
 * @author ikarpova
 */
public class WordListFragment extends RecyclerViewFragment<Word, Word> {
    private Lesson lesson;

    static WordListFragment getInstance(UserSession userSession, Lesson lesson) {
        WordListFragment fragment = new WordListFragment();
        UserSessionFragment.setArguments(fragment, userSession);
        fragment.getArguments().putParcelable("lesson", lesson);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setDividerVisible(true);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lesson = getArguments().getParcelable("lesson");
    }

    @Override
    protected Loader<List<Word>> createLoader(Bundle args) {
        args.putInt("lessonId", lesson.trainId);
        return LoaderFactory.getViewListLoader(getContext(), args);
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
            return new WordListAdapter.WordViewHolder(inflateView(parent, R.layout.list_item_word_view));
        }

        class WordViewHolder extends ViewHolder {
            private final TextView kanjiView;
            private final TextView readingView;
            private final TextView translationView;
            private final TextView positionView;

            WordViewHolder(View view) {
                super(view);
                kanjiView = view.findViewById(R.id.kanjiView);
                readingView = view.findViewById(R.id.readingView);
                translationView = view.findViewById(R.id.translation);
                positionView = view.findViewById(R.id.position);
            }

            @Override
            protected void bindViewHolder(final Context context, int position, final Word value) {
                kanjiView.setText(value.japanese);
                readingView.setText(getReading(context, value), TextView.BufferType.SPANNABLE);
                readingView.setVisibility(TextUtils.isEmpty(value.reading) ? GONE : VISIBLE);
                translationView.setText(value.translation);
                positionView.setText(String.valueOf(position + 1));
            }
        }
    }
}
