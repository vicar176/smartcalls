package com.jiresoft.smartcallblocker.data.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jiresoft.smartcallblocker.data.DatabaseHelper;

/**
 * Created by victorariasgonzalez on 2/20/18.
 */

public class BaseDAO {

    // SQLiteDatabase and DatabaseHelper objects  to access SQLite database
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    // Constructor initiates the DatabaseHelper to make sure, database creation is done
    public BaseDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        open();
    }

    protected SQLiteDatabase getDatabase() {
        return this.database;
    }

    private void open() throws SQLException {

        // Opens the database connection to provide the access
        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        // Close it, once done
        dbHelper.close();
    }

}
