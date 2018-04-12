package com.jiresoft.smartcallblocker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by victorariasgonzalez on 2/15/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    // Define the SQLite database name
    private static final String DATABASE_NAME = "smart_call_blocker.db";

    // Define the SQLite database version
    private static final int DATABASE_VERSION = 1;

    // Define the SQLite Table name to create
    public static final String TABLE_PHONENUMBER = "phone_number";
    public static final String TABLE_BLOCKEDPHONENUMBER = "blocked_phone_number";

    // Table creation SQL statement
    private static final String TABLE_PHONENUMBER_CREATE = "create table "  + TABLE_PHONENUMBER + "( id "
            + " integer primary key autoincrement, number  text not null, type integer not null);";
    private static final String TABLE_BLOCKEDPHONENUMBER_CREATE = "create table " + TABLE_BLOCKEDPHONENUMBER + "( id " +
            "integer primary key autoincrement, number text not null, blocked_time integer not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This method will execute once in the application entire life cycle
    // All table creation code should put here
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_PHONENUMBER_CREATE);
        db.execSQL(TABLE_BLOCKEDPHONENUMBER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

}
