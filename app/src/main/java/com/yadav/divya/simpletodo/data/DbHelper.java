package com.yadav.divya.simpletodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dyadav1 on 1/24/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "SIMPLETODO_DB";
    static final String TABLE_NAME = "TASK_LIST";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TODO_TABLE  = "create table "
                + TABLE_NAME + "('_id' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "task TEXT NOT NULL,"
                + "status TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
