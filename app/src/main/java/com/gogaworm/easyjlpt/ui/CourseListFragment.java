package com.gogaworm.easyjlpt.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.ViewModelProviders;
import com.gogaworm.easyjlpt.MainActivity;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Course;
import com.gogaworm.easyjlpt.ui.adapters.DynamicDataAdapter;
import com.gogaworm.easyjlpt.viewmodel.CourseListViewModel;
import com.gogaworm.easyjlpt.viewmodel.ListViewModel;

public class CourseListFragment extends RecyclerViewFragment<Course> {

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setTitle(getString(R.string.course_list_title));
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.courseListRecyclerView;
    }

    @Override
    protected ListViewModel<Course> initViewModel() {
        return ViewModelProviders.of(this).get(CourseListViewModel.class);
    }

    @Override
    protected void onItemSelected(Course course) {
        super.onItemSelected(course);
        if (course.section.equals("vocabulary")) {
            ((MainActivity) getActivity()).goToFragment(new WordLessonListFragment(), true);
        } else if (course.section.equals("kanji")) {
            ((MainActivity) getActivity()).goToFragment(new KanjiLessonListFragment(), true);
        }
    }

    @Override
    protected DynamicDataAdapter createAdapter(Context context, OnItemSelectedListener<Course> itemSelectedListener) {
        return new DynamicDataAdapter<Course>(getContext(), itemSelectedListener) {

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = inflateView(parent, R.layout.course_list_item);
                return new ViewHolder(view) {
                    private TextView sectionView;
                    private TextView levelView;
                    private TextView textView;

                    @Override
                    protected void initViewHolder(View parent) {
                        textView = parent.findViewById(R.id.courseTitleView);
                        levelView = parent.findViewById(R.id.levelView);
                        sectionView = parent.findViewById(R.id.sectionView);
                    }

                    @Override
                    protected void bindViewHolder(Context context, int position, final Course course) {
                        textView.setText(course.title);
                        levelView.setText(course.level);
                        sectionView.setText(course.section);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (itemSelectedListener != null) {
                                    itemSelectedListener.onItemSelected(course);
                                }
                            }
                        });
                    }
                };
            }
        };
    }
}
