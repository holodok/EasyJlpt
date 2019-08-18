package com.gogaworm.easyjlpt.ui.gamefragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.gogaworm.easyjlpt.R;
import com.gogaworm.easyjlpt.db.Exam;
import com.gogaworm.easyjlpt.games.SelectVariantGameTemplate;
import com.gogaworm.easyjlpt.viewmodel.ExamViewModel;
import com.gogaworm.easyjlpt.viewmodel.GenericGamesLearnWordsViewModel;

public class ExamGameFragment extends GameFragment<Exam> {
    private TextView japaneseView;
    private TextView readingView;
    private View separatorView;
    private TextView translationView;
    private Button[] answerButtons;
    private View submitButton;

    private SelectVariantGameTemplate.Game<Exam> game;
    private ColorStateList defaultBackgroundTintList;
    private ColorStateList defaultTextColors;

    @Override
    protected Class<? extends GenericGamesLearnWordsViewModel<Exam>> getViewModelClass() {
        return ExamViewModel.class;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(getLayoutId(), container, false);
        japaneseView = parentView.findViewById(R.id.japanese);
        readingView = parentView.findViewById(R.id.reading);
        separatorView = parentView.findViewById(R.id.divider);
        translationView = parentView.findViewById(R.id.translation);

        answerButtons = new Button[] {
                parentView.findViewById(R.id.firstAnswer),
                parentView.findViewById(R.id.secondAnswer),
                parentView.findViewById(R.id.thirdAnswer),
                parentView.findViewById(R.id.forthAnswer)
        };

        defaultBackgroundTintList = ViewCompat.getBackgroundTintList(answerButtons[0]);
        defaultTextColors = answerButtons[0].getTextColors();

        submitButton = parentView.findViewById(R.id.submitButton);

        return parentView;
    }

    protected int getLayoutId() {
        return R.layout.fragment_variant_select_game;
    }

}
