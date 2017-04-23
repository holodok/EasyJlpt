package com.gogaworm.easyjlpt.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 23.04.2017.
 *
 * @author ikarpova
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jlpt.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TASKS (ID INTEGER PRIMARY KEY, VALUE_ID INTEGER, PROGRESS INTEGER, LAST_PRACTICE DATETIME);");
        db.execSQL("CREATE TABLE EXAMS (ID INTEGER PRIMARY KEY, EXAM_ID INTEGER, PROGRESS INTEGER, LAST_PRACTICE DATETIME);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TASKS;");
        db.execSQL("DROP TABLE IF EXISTS EXAMS;");
    }
}
