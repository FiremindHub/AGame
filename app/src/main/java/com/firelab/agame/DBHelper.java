package com.firelab.agame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
                "Id integer primary key autoincrement, " +
                "Number int, " +
                "Name text, " +
                "ClassName text, " +
                "State int, " +
                "LockState int, " +
                "Result int, " +
                "Time int);";
        db.execSQL(createTablesCommand);
        initializeTablesContent(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initializeTablesContent(SQLiteDatabase db){
        insertLevelTableContent(db);
    }

    private void insertLevelTableContent(SQLiteDatabase db){
        for (ContentValues cv :createLevelTableContent()) {
            db.insert("Level", "", cv);
        }
    }

    private Collection<ContentValues> createLevelTableContent(){
        Collection<ContentValues> result = new ArrayList<ContentValues>();

        for(int i = 1; i <= 8; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Number", i);
            contentValues.put("Name", String.format("Level %02d", i));
            contentValues.put("ClassName", String.format("Level %d", i));
            contentValues.put("State", "3");
            contentValues.put("LockState", "0");
            contentValues.put("Result", "2");
            contentValues.put("Time", "0");
            result.add(contentValues);
        }

        return result;
    }

    public boolean getIsLevelUnlocked(int levelNumber){
        if (levelNumber == 1){
            return true;
        }
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("Level", new String[] {"LockState"}, "Number = ?", new String[] {String.valueOf(levelNumber)}, null, null, null);

        int lockStateColumnIndex = c.getColumnIndex("LockState");
        c.moveToFirst();
        int result = c.getInt(lockStateColumnIndex);

        return result > 0;
    }
}
