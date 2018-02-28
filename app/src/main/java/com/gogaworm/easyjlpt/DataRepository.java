package com.gogaworm.easyjlpt;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.gogaworm.easyjlpt.data.JlptLevel;
import com.gogaworm.easyjlpt.data.JlptSection;
import com.gogaworm.easyjlpt.data.UserSession;
import com.gogaworm.easyjlpt.data.Word;
import com.gogaworm.easyjlpt.database.AppDatabase;
import com.gogaworm.easyjlpt.game.WordTask;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase database;

    private MutableLiveData<UserSession> userSessionLiveData;
    public UserSession userSession;

    private DataRepository(final AppDatabase database) {
        this.database = database;

        userSession = new UserSession(JlptSection.VOCABULARY, JlptLevel.N2);
        userSession.setLessonId(212); //todo: this should be set by user //todo: load from previous session
        userSessionLiveData = new MutableLiveData<>();
        userSessionLiveData.setValue(userSession);
/*
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(productEntities);
                    }
                });
*/
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    //todo: move this to game engine========================

    //todo: load from json and cache
    public LiveData<List<WordTask>> getWordTasks() {
        //todo: get section. level and lesson from user session
        List<WordTask> wordTasks = new ArrayList<>();
        wordTasks.add(new WordTask(new Word("japanese", "reading", "translation")));
        MutableLiveData<List<WordTask>> wordTasksLiveData = new MutableLiveData<>();
        wordTasksLiveData.setValue(wordTasks);
        return wordTasksLiveData;
    }
    //=====================================

    public LiveData<UserSession> getUserSession() {
        return userSessionLiveData;
    }

    public void setJlptSection(JlptSection section) {
        userSession.section = section;
        userSessionLiveData.setValue(userSession);
    }

    public void setJlptLevel(JlptLevel level) {
        userSession.level = level;
    }

    public void setLessonId(int lessonId) {
        userSession.setLessonId(lessonId);
    }

    public void setExamId(int examId) {
        userSession.setExamId(examId);
    }

    /*
        private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<ProductEntity>> mObservableProducts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

        mObservableProducts.addSource(mDatabase.productDao().loadAllProducts(),
                productEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(productEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     *//*
    public LiveData<List<ProductEntity>> getProducts() {
        return mObservableProducts;
    }

    public LiveData<ProductEntity> loadProduct(final int productId) {
        return mDatabase.productDao().loadProduct(productId);
    }

    public LiveData<List<CommentEntity>> loadComments(final int productId) {
        return mDatabase.commentDao().loadComments(productId);
    }
    * */
}
