package com.gogaworm.easyjlpt.loader;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gogaworm.easyjlpt.db.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.UPPER_CAMEL_CASE;

public class DataSyncLoader implements Runnable {
    private Context context;
    private AppDatabase appDatabase;
    private MutableLiveData<Boolean> dbSyncResult;
    private final ExamJsonFileLoader examJsonFileLoader;
    private final WordJsonFileLoader wordJsonFileLoader;
    private final KanjiJsonFileLoader kanjiJsonFileLoader;

    public DataSyncLoader(Context context, AppDatabase appDatabase, MutableLiveData<Boolean> dbSyncResult) {
        this.context = context;
        this.appDatabase = appDatabase;
        this.dbSyncResult = dbSyncResult;

        wordJsonFileLoader = new WordJsonFileLoader();
        kanjiJsonFileLoader = new KanjiJsonFileLoader();
        examJsonFileLoader = new ExamJsonFileLoader();
    }

    @Override
    public void run() {
        appDatabase.clearAllTables(); //TODO: later delete this

        //load data from json, fill tables
        //get all folders from assets
        try {
            updateData("data");
            dbSyncResult.postValue(true);
        } catch (Exception ex) {
            ex.printStackTrace(); //todo: log nicely
            dbSyncResult.postValue(false);
        }
    }

    private void updateData(String rootFolder) throws Exception {
        String[] dataFolders = context.getAssets().list(rootFolder);
        if (dataFolders == null) {
            return;
        }
        for (String level : dataFolders) {
            String fullLevelFolderName = rootFolder.concat("/").concat(level);
            loadLevels(level, fullLevelFolderName);
        }
    }

    private void loadLevels(String level, String rootFolder) throws Exception {
        String[] sectionFolders = context.getAssets().list(rootFolder);
        if (sectionFolders == null) {
            return;
        }

        for (String section : sectionFolders) {
            String fullSectionFolderName = rootFolder.concat("/").concat(section);
            loadCourses(level, section, fullSectionFolderName);
        }
    }

    private void loadCourses(String level, String section, String rootFolder) throws Exception {
        String[] courseFolders = context.getAssets().list(rootFolder);
        if (courseFolders == null) {
            return;
        }

        for (String courseName : courseFolders) {
            String courseFolderName = rootFolder.concat("/").concat(courseName);
            Course course = saveCourseIfNew(courseName, level, section);
            loadLessons(course, courseFolderName);
        }
    }

    private void loadLessons(Course course, String rootFolder) throws Exception {
        String[] wordFiles = context.getAssets().list(rootFolder);
        if (wordFiles == null) {
            return;
        }

        for (String wordFile : wordFiles) { //todo: read only from vocabulary section
            //load words
            String lessonFileName = rootFolder.concat("/").concat(wordFile);
            String lessonCode;
            if (wordFile.startsWith("lesson_")) { //get exam data as well
                updateLesson(course, lessonFileName);
            } else if (wordFile.startsWith("exam_")) {
                lessonCode = wordFile.substring(5, wordFile.indexOf("."));
                updateExam(course, lessonCode, lessonFileName);
            }
        }
    }

    private void updateLesson(Course course, String lessonFileName) throws Exception {
        if (course.section.equalsIgnoreCase("vocabulary")) {
            WordLesson wordLesson = OBJECT_MAPPER.readValue(loadJsonFromAsset(context, lessonFileName), WordLesson.class);
            //wordJsonFileLoader.loadItems(context, appDatabase, course.id, lessonCode, lessonFileName);
        } else if (course.section.equalsIgnoreCase("kanji")) {
            KanjiLesson kanjiLesson = OBJECT_MAPPER.readValue(loadJsonFromAsset(context, lessonFileName), KanjiLesson.class);
            syncKanjiLesson(kanjiLesson, course.id);
            //kanjiJsonFileLoader.loadItems(context, appDatabase, course.id, lessonCode, lessonFileName);
        }
    }

    private void updateExam(Course course, String lessonCode, String lessonFileName) {
        examJsonFileLoader.loadItems(context, appDatabase, course.id, lessonCode, lessonFileName);
    }

    private void syncKanjiLesson(KanjiLesson kanjiLesson, int courseId) {
        //get lesson from db
        int lessonId = updateDbLesson(kanjiLesson, courseId);

        List<String> dbKanjiCodes = appDatabase.getKanjiDao().getKanjiCodesForLesson(lessonId);

        //update kanji and words
        for (JsonKanji jsonKanji : kanjiLesson.kanji) {
            int kanjiId = updateDbKanji(lessonId, jsonKanji);
            dbKanjiCodes.remove(jsonKanji.id);

            List<String> dbWordCodes = appDatabase.getKanjiDao().getExpressionCodesForKanji(kanjiId);
            for (JsonWord jsonWord : jsonKanji.words) {
                updateDbWord(kanjiId, jsonWord);
                dbWordCodes.remove(jsonWord.id);
            }
            appDatabase.getKanjiDao().removeExpressionByCode(dbWordCodes);
        }
        appDatabase.getKanjiDao().removeKanjiByCode(dbKanjiCodes);
    }

    private int updateDbLesson(KanjiLesson kanjiLesson, int courseId) {
        Lesson dbLesson = appDatabase.getLessonDao().getLessonByCode(kanjiLesson.id);
        if (dbLesson == null) {
            dbLesson = new Lesson(courseId, kanjiLesson.title, kanjiLesson.id);
            appDatabase.getLessonDao().insert(dbLesson);
        } else if (!dbLesson.title.equals(kanjiLesson.title)) {
            appDatabase.getLessonDao().update(dbLesson);
        }
        return dbLesson.id;
    }

    private int updateDbKanji(int lessonId, JsonKanji jsonKanji) {
        Kanji kanji = appDatabase.getKanjiDao().getKanjiByCode(jsonKanji.id);
        if (kanji == null) {
            kanji = new Kanji(lessonId, jsonKanji.id, jsonKanji.japanese, jsonKanji.on, jsonKanji.kun, jsonKanji.translation);
            appDatabase.getKanjiDao().insert(kanji);
        } else {
            kanji.kanji = jsonKanji.japanese;
            kanji.onReading = jsonKanji.on;
            kanji.kunReading = jsonKanji.kun;
            kanji.translation = jsonKanji.translation;
            appDatabase.getKanjiDao().update(kanji);
        }
        return kanji.id;
    }

    private void updateDbWord(int kanjiId, JsonWord jsonWord) {
        Expression expression = appDatabase.getExpressionDao().getExpressionByCode(jsonWord.id);
        if (expression == null) {
            expression = new Expression();//todo
            appDatabase.getExpressionDao().insert(expression);
        } else {
            expression.japanese = jsonWord.japanese;
            expression.reading = jsonWord.reading;
            expression.translation = jsonWord.translation;
            appDatabase.getExpressionDao().update(expression);
        }
    }

/*
    private void syncDataWithJsonAssets() throws IOException {
        String dataFolderName = "data";
        WordJsonFileLoader wordJsonFileLoader = new WordJsonFileLoader();
        KanjiJsonFileLoader kanjiJsonFileLoader = new KanjiJsonFileLoader();
        ExamJsonFileLoader examJsonFileLoader = new ExamJsonFileLoader();

        String[] dataFolders = context.getAssets().list(dataFolderName);
        for (String levelFolder : dataFolders) {
            String levelFolderName = dataFolderName.concat("/").concat(levelFolder);
            String[] sectionFolders = context.getAssets().list(levelFolderName);
            for (String sectionFolder : sectionFolders) {
                String sectionFolderName = levelFolderName.concat("/").concat(sectionFolder);
                String[] courseFolders = context.getAssets().list(sectionFolderName);
                for (String courseFolder : courseFolders) {
                    String folderName = sectionFolderName.concat("/").concat(courseFolder);
                    Course course = saveCourseIfNew(courseFolder, levelFolder, sectionFolder);

                    String[] wordFiles = context.getAssets().list(folderName);
                    for (String wordFile : wordFiles) { //todo: read only from vocabulary section
                        //load words
                        String lessonFileName = folderName.concat("/").concat(wordFile);
                        if (wordFile.startsWith("lesson_")) { //get exam data as well
                            String lessonCode = wordFile.substring(7, wordFile.indexOf("."));
                            if (sectionFolder.equals("vocabulary")) {
                                wordJsonFileLoader.loadItems(context, appDatabase, course.id, lessonCode, lessonFileName);
                            } else if (sectionFolder.equals("kanji")) {
                                kanjiJsonFileLoader.loadItems(context, appDatabase, course.id, lessonCode, lessonFileName);
                            }
                        } else if (wordFile.startsWith("exam_")) {
                            String lessonCode = wordFile.substring(5, wordFile.indexOf("."));
                            examJsonFileLoader.loadItems(context, appDatabase, course.id, lessonCode, lessonFileName);
                        }
                    }
                }
            }
        }
    }
*/

    private Course saveCourseIfNew(String title, String level, String section) {
        Course course = new Course(title, level, section); //todo: get course by section/level/title
        course.id = (int) appDatabase.getCourseDao().insert(course);
        return course;
    }

    private String loadJsonFromAsset(Context context, String fileName) {
        StringBuilder text = new StringBuilder();

        try (InputStream is = context.getAssets().open(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return text.toString();
    }

    private static final ObjectMapper OBJECT_MAPPER = createBaseObjectMapper();

    private static ObjectMapper createBaseObjectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(UPPER_CAMEL_CASE)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

}
