package com.gogaworm.easyjlpt.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.gogaworm.easyjlpt.EasyJlptApplication;
import com.gogaworm.easyjlpt.db.*;

import java.util.Collection;
import java.util.List;

public class AppController {
    private EasyJlptApplication application;
    private AppDatabase database;

    private final LiveData<Boolean> result;

    private MutableLiveData<Course> selectedCourseLiveData;
    private MutableLiveData<Lesson> selectedLessonLiveData;
    private MutableLiveData<Kanji> selectedKanji;

    public AppController(EasyJlptApplication application, AppDatabase database, LiveData<Boolean> result) {
        this.application = application;
        this.database = database;
        this.result = result;

        selectedCourseLiveData = new MutableLiveData<>();
        selectedLessonLiveData = new MutableLiveData<>();
        selectedKanji = new MutableLiveData<>();
    }

    public LiveData<List<Course>> getCourses() {
        return database.getCourseDao().getCourses();
    }

    public LiveData<Boolean> applicationInitializedResult() {
        return result;
    }

    public LiveData<Course> getSelectedCourse() {
        return selectedCourseLiveData;
    }

    public void setSelectedCourse(Course course) {
        selectedCourseLiveData.setValue(course);
    }

    public void setSelectedLesson(Lesson lesson) {
        selectedLessonLiveData.setValue(lesson);
    }

    public LiveData<Lesson> getSelectedLesson() {
        return selectedLessonLiveData;
    }

    public LiveData<List<Lesson>> getLessonsForSelectedCourse() {//todo rewrite
        Course selectedCourse = this.selectedCourseLiveData.getValue();
        return database.getLessonDao().getLessonsForCourse(selectedCourse == null ? 0 : selectedCourse.id);
    }

    public LiveData<List<Word>> getWordsForSelectedLesson() {//todo rewrite
        Lesson selectedLesson = selectedLessonLiveData.getValue();
        return database.getWordDao().getWordsForLesson(selectedLesson == null ? 0 : selectedLesson.id);
    }

    public List<Word> getWordsForSelectedLesson(int lessonId) {
        return database.getWordDao().getWordsForLessonRaw(lessonId);
    }

    public LiveData<List<WordDescription>> getWordDescriptionsForSelectedLesson() {//todo rewrite
        Lesson selectedLesson = selectedLessonLiveData.getValue();
        return database.getWordDao().getWordDescriptionsForLesson(selectedLesson == null ? 0 : selectedLesson.id);
    }

    public LiveData<List<Kanji>> getKanjiForSelectedLesson() {
        Lesson selectedLesson = selectedLessonLiveData.getValue();
        return database.getKanjiDao().getKanjiForLesson(selectedLesson == null ? 0 : selectedLesson.id);
    }

    public List<Kanji> getKanjiForSelectedLesson(int lessonId) {
        return database.getKanjiDao().getKanjiForLessonRaw(lessonId);
    }

    public LiveData<List<Expression>> getAllExpressions() {
        return database.getExpressionDao().getAllExpressions();
    }

    public void setSelectedKanji(Kanji kanji) {
        selectedKanji.setValue(kanji);
    }

    public LiveData<Kanji> getSelectedKanji() {
        return selectedKanji;
    }

    public LiveData<List<Expression>> getExpressionsForKanji() {
        Kanji kanji = selectedKanji.getValue();
        return database.getExpressionDao().getExpressionForKanji(kanji.id);
    }

    public List<Expression> getExpressionsForKanji(int kanjiId) {
        return database.getExpressionDao().getExpressionForKanjiRaw(kanjiId);
    }

    public Collection<Expression> getExpressionsForWord(int wordId) {
        return database.getExpressionDao().getExpressionForWord(wordId);
    }

    public Collection<Sentence> getSentencesForWord(int wordId) {
        return database.getSentenceDao().getSentencesForWord(wordId);
    }

    public List<Exam> getExamForSelectedLesson(int lessonId) {
        return database.getExamDao().getExamForLesson(lessonId);
    }
}
