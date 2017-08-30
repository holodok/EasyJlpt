package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.Lesson;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.loaders.LoaderFactory;
import com.gogaworm.easyjlpt.ui.widgets.ArcProgress;
import com.gogaworm.easyjlpt.utils.UnitedKanjiKanaSpannableString;

import java.util.ArrayList;
import java.util.List;

import static com.gogaworm.easyjlpt.utils.Constants.LOADER_ID_LESSON;

/**
 * Created on 27.03.2017.
 *
 * @author ikarpova
 */
public class LessonFragment extends RecyclerViewFragment<Lesson, Lesson> {
    private int sectionId;

    static LessonFragment getInstance(UserSession userSession, int sectionId) {
        LessonFragment lessonFragment = new LessonFragment();
        UserSessionFragment.setArguments(lessonFragment, userSession);
        lessonFragment.getArguments().putInt("sectionId", sectionId);
        return lessonFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sectionId = getArguments().getInt("sectionId");
    }

    @Override
    protected int getLoaderId() {
        return LOADER_ID_LESSON;
    }

    @Override
    DynamicDataAdapter createAdapter(Context context) {
        return new LessonAdapter(context);
    }

    @Override
    protected Loader<List<Lesson>> createLoader(JlptSection section, JlptLevel level) {
        return LoaderFactory.getLessonListLoader(getContext(), section, level, sectionId);
    }

    class LessonAdapter extends DynamicDataAdapter {

        LessonAdapter(Context context) {
            super(context);
        }

        @Override
        protected List<Lesson> createDataset() {
            return new ArrayList<>();
        }

        @Override
        protected void setData(List<Lesson> dataset, List<Lesson> values) {
            dataset.addAll(values);
        }

        @Override
        protected int getItemViewType(Lesson value) {
            return 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflateView(parent, R.layout.list_item_lesson);
            return new LessonViewHolder(view);
        }

        class LessonViewHolder extends ViewHolder {
            private final ArcProgress progressView;
            private final TextView kanjiView;
            private final TextView translationView;
            private final ImageButton viewButton;
            private final View flashCardsButton;
            private final View learnButton;
            private final View examButton;
            // each data item is just a string in this case


            LessonViewHolder(View view) {
                super(view);
                progressView = (ArcProgress) view.findViewById(R.id.progress);
                kanjiView = (TextView) view.findViewById(R.id.kanjiView);
                translationView = (TextView) view.findViewById(R.id.translation);
                viewButton = (ImageButton) view.findViewById(R.id.viewButton);
                flashCardsButton = view.findViewById(R.id.flashCardButton);
                learnButton = view.findViewById(R.id.learnButton);
                examButton = view.findViewById(R.id.examButton);
            }

            @Override
            protected void bindViewHolder(Context context, int position, final Lesson lesson) {
                progressView.setProgress(100);
                kanjiView.setText(new UnitedKanjiKanaSpannableString(lesson.title.japanese));
                translationView.setText(lesson.title.translation);
                viewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startNextActivity(userSession.section == JlptSection.GRAMMAR ?
                                ViewGrammarLessonContentActivity.class :
                                ViewLessonContentActivity.class, lesson);
                    }
                });
                flashCardsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startNextActivity(FlashCardsActivity.class, lesson);
                    }
                });
                learnButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startNextActivity(LearnLessonActivity.class, lesson);
                    }
                });
                examButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startNextActivity(ExamActivity.class, lesson);
                    }
                });
            }

            private void startNextActivity(Class activityClass, Lesson lesson) {
                Intent intent = new Intent(getContext(), activityClass);
                intent.putExtra("userSession", userSession);
                intent.putExtra("lesson", lesson);
                startActivity(intent);
            }
        }
    }
}
