package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.*;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;
import com.gogaworm.easyjlpt.ui.widgets.rcbs.RoundedCornersBackgroundSpan;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_WORD_LIST;

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
    protected Loader<List<Word>> createLoader(JlptSection section, JlptLevel level) {
        return LoaderFactory.getViewListLoader(getContext(), section, level, lesson.trainId);
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
                kanjiView = (TextView) view.findViewById(R.id.kanjiView);
                readingView = (TextView) view.findViewById(R.id.readingView);
                translationView = (TextView) view.findViewById(R.id.translation);
                positionView = (TextView) view.findViewById(R.id.position);
            }

            @Override
            protected void bindViewHolder(final Context context, int position, final Word value) {
                kanjiView.setText(value.japanese);
                if (value instanceof Kanji) {
                    Kanji kanji = (Kanji) value;
                    SpannableString reading = new SpannableString(kanji.reading);
                    if (kanji.hasOnReading()) {
                        setReadingSpans(reading, kanji.onReading, 0, getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.primaryInvertedText));
                    }
                    if (kanji.hasKunReading()) {
                        int startPosition = kanji.hasOnReading() ? (kanji.onReading.length() + 1) : 0;
                        setReadingSpans(reading, kanji.kunReading, startPosition, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.primaryInvertedText));
                    }
                    readingView.setText(reading, TextView.BufferType.SPANNABLE);
                } else {
                    readingView.setText(value.reading);
                }
                readingView.setVisibility(TextUtils.isEmpty(value.reading) ? GONE : VISIBLE);
                translationView.setText(value.translation);
                positionView.setText(String.valueOf(position + 1));
            }

            private void setReadingSpans(SpannableString spannable, String reading, int startPosition, int backgroundColor, int textColor) {
                String[] readings = reading.split(" ");
                int position = startPosition;
                for (String readingPart : readings) {
                    spannable.setSpan(new RoundedCornersBackgroundSpan(backgroundColor, textColor),
                            position, position + readingPart.length(), 0);
                    position += readingPart.length() + 1;
                }
            }
        }
    }
}
