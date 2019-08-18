package com.gogaworm.easyjlpt.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.gogaworm.easyjlpt.db.Expression;
import com.gogaworm.easyjlpt.db.Kanji;

import java.util.List;

public class KanjiViewViewModel extends ListViewModel<Expression> {
    public KanjiViewViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<List<Expression>> getData() {
        return controller.getExpressionsForKanji();
    }

    @Override
    public void setSelectedItem(Expression item) {
    }

    public LiveData<Kanji> getSelectedKanji() {
        return controller.getSelectedKanji();
    }
}
