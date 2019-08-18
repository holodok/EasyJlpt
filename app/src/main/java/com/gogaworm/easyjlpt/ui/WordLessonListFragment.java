package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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

import static com.gogaworm.easyjlpt.db.Lesson.LESSON_TYPE.*;

public class WordLessonListFragment extends RecyclerViewFragment<Lesson> {
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitle(((LessonListViewModel) viewModel).getSelectedCourse().title);
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.wordLessonListRecyclerView;
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
                Fragment fragment = new WordLessonViewFragment();
                ((MainActivity) getActivity()).goToFragment(fragment, true);
                break;
            case LEARN_WORDS:
                //goto learn activity
                ((MainActivity) getActivity()).goToActivity(LessonLearnWordsActivity.class);
                break;
            case REVIEW_WORDS:
                ((MainActivity) getActivity()).goToActivity(LessonReviewWordsActivity.class);
                break;
            case WORD_EXAM:
                ((MainActivity) getActivity()).goToActivity(ExamWordsActivity.class);
                break;
        }
    }

    @Override
    protected DynamicDataAdapter createAdapter(Context context, OnItemSelectedListener<Lesson> itemSelectedListener) {

        return new DynamicDataAdapter<Lesson>(context, itemSelectedListener) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view = inflateView(parent, R.layout.word_lesson_list_item);

                return new ViewHolder(view) {
                    private Button learnButton;
                    private Button viewButton;
                    private Button reviewButton;
                    private ImageButton menuButton;
                    private TextView textView;
                    private TextView timestampView;

                    @Override
                    protected void initViewHolder(View parent) {
                        textView = parent.findViewById(R.id.lessonTitleView);
                        timestampView = parent.findViewById(R.id.timestampView);
                        viewButton = parent.findViewById(R.id.viewButton);
                        learnButton = parent.findViewById(R.id.learnButton);
                        reviewButton = parent.findViewById(R.id.reviewButton);
                        menuButton = parent.findViewById(R.id.openMenuButton);
                    }

                    @Override
                    protected void bindViewHolder(Context context, int position, final Lesson lesson) {
                        textView.setText(lesson.title);
                        String timeStamp = lesson.lastStudiedTime == 0 ? getString(R.string.timestamp_never) : new Date(lesson.lastStudiedTime).toString();
                        timestampView.setText(timeStamp);
                        viewButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lesson.lessonType = VIEW;
                                itemSelectedListener.onItemSelected(lesson);
                            }
                        });
                        learnButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                lesson.lessonType = LEARN_WORDS;
                                itemSelectedListener.onItemSelected(lesson);
                            }
                        });
                        reviewButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lesson.lessonType = REVIEW_WORDS;
                                itemSelectedListener.onItemSelected(lesson);
                            }
                        });
                        menuButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showPopupMenu(menuButton, lesson);
                            }
                        });
                    }
                };

            }

            private void showPopupMenu(View view, final Lesson lesson) {
                // inflate menu
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu_word_lesson, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.examMenuItem:
                                lesson.lessonType = WORD_EXAM;
                                itemSelectedListener.onItemSelected(lesson);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        };
    }
}
