package com.yadav.divya.simpletodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dyadav1 on 1/24/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SIMPLETODO_DB";
    public static final String TABLE_NAME = "TASK_LIST";
    public static final String COLUMN_TASK = "task";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DATE = "date";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TODO_TABLE  = "create table "
                + TABLE_NAME + "('_id' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TASK + " TEXT NOT NULL, "
                + COLUMN_PRIORITY + " TEXT NOT NULL, "
                + COLUMN_STATUS + " TEXT NOT NULL, "
                + COLUMN_DATE + " INTEGER NOT NULL);";
        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
