package com.mycompany.clockplus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew on 4/29/2017.
 */

public class AlarmReaderDbHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Alarmdb.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AlarmContract.AlarmEntry.TABLE_NAME + " (" +
                    AlarmContract.AlarmEntry._ID + " INTEGER PRIMARY KEY," +
                    AlarmContract.AlarmEntry.COLUMN_NAME_NAME + " TEXT," +
                    AlarmContract.AlarmEntry.COLUMN_NAME_MINUTE + " INTEGER," +
                    AlarmContract.AlarmEntry.COLUMN_NAME_HOUR + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AlarmContract.AlarmEntry.TABLE_NAME;

    public AlarmReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
