package com.gogaworm.easyjlpt.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gogaworm.easyjlpt.game.Task;

/**
 * Created on 23.04.2017.
 *
 * @author ikarpova
 */
public class ProgressDatasource {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ProgressDatasource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long loadTask(Task task) {
        Cursor cursor = database.query("TASKS",
                new String[] {"PROGRESS", "LAST_PRACTICE"}, "VALUE_ID=?",
                new String[] {String.valueOf(task.id)}, null, null, null);
        long id = 0;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            id = cursor.getLong(0);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return id;
    }

}
