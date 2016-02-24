package com.firelab.agame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private final String LOG_TAG = "DBHELPER";
    static final String DATABASE_NAME = "SquaresDB";
    static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Creating database...");
        String createTablesCommand = "CREATE TABLE Level (" +
                "Id integer primary key autoincrement" +
                "Number int" +
                "Name text" +
                "ClassName text" +
                "State int" +
                "LockState int" +
                "Result int" +
                "Time int";
        db.execSQL(createTablesCommand);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
