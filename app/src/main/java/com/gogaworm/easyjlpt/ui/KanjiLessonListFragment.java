package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.MainActivity;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Lesson;
import com.gogaworm.easyjlpt.ui.adapters.DynamicDataAdapter;
import com.gogaworm.easyjlpt.viewmodel.LessonListViewModel;
import com.gogaworm.easyjlpt.viewmodel.ListViewModel;

import java.util.Date;

public class KanjiLessonListFragment extends RecyclerViewFragment<Lesson> {

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitle(((LessonListViewModel) viewModel).getSelectedCourse().title);
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.kanjiLessonListRecyclerView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kanji_lesson_list;
    }

    @Override
    protected ListViewModel<Lesson> initViewModel() {
        return ViewModelProviders.of(this).get(LessonListViewModel.class);
    }

    @Override
    protected void onItemSelected(Lesson lesson) {
        super.onItemSelected(lesson);
        switch (lesson.lessonType) {
            case VIEW:
                Fragment fragment = new KanjiLessonViewFragment();
                ((MainActivity) getActivity()).goToFragment(fragment, true);
                break;
            case LEARN_KANJI:
                ((MainActivity) getActivity()).goToActivity(LessonLearnKanjiActivity.class);
                break;
            case LEARN_KANJI_WORDS:
                ((MainActivity) getActivity()).goToActivity(LessonLearnKanjiWordsActivity.class);
                break;
        }
    }

    @Override
    protected DynamicDataAdapter createAdapter(Context context, OnItemSelectedListener<Lesson> itemSelectedListener) {

        return new DynamicDataAdapter<Lesson>(context, itemSelectedListener) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = inflateView(parent, R.layout.kanji_lesson_list_item);

                return new ViewHolder(view) {
                    private Button learnKanjiButton;
                    private Button learnWordsButton;
                    private Button viewButton;
                    private TextView textView;
                    private TextView timestampView;

                    @Override
                    protected void initViewHolder(View parent) {
                        textView = parent.findViewById(R.id.lessonTitleView);
                        timestampView = parent.findViewById(R.id.timestampView);
                        viewButton = parent.findViewById(R.id.viewButton);
                        learnKanjiButton = parent.findViewById(R.id.learnKanjiButton);
                        learnWordsButton = parent.findViewById(R.id.learnWordsButton);
                    }

                    @Override
                    protected void bindViewHolder(Context context, int position, final Lesson lesson) {
                        textView.setText(lesson.title);
                        String timeStamp = lesson.lastStudiedTime == 0 ? getString(R.string.timestamp_never) : new Date(lesson.lastStudiedTime).toString();
                        timestampView.setText(timeStamp);
                        viewButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lesson.lessonType = Lesson.LESSON_TYPE.VIEW;
                                itemSelectedListener.onItemSelected(lesson);
                            }
                        });
                        learnKanjiButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lesson.lessonType = Lesson.LESSON_TYPE.LEARN_KANJI;
                                itemSelectedListener.onItemSelected(lesson);
                            }
                        });
                        learnWordsButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lesson.lessonType = Lesson.LESSON_TYPE.LEARN_KANJI_WORDS;
                                itemSelectedListener.onItemSelected(lesson);
                            }
                        });
                    }
                };
            }
        };
    }
}
