package com.jiresoft.smartcallblocker.data.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.jiresoft.smartcallblocker.data.DatabaseHelper;
import com.jiresoft.smartcallblocker.data.model.PhoneNumber;
import com.jiresoft.smartcallblocker.data.model.Type;

/**
 * Created by victorariasgonzalez on 2/15/18.
 */

public class PhoneNumberDAO extends BaseDAO{

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_TYPE = "type";


    public PhoneNumberDAO(Context context) {
        super(context);
    }

    public PhoneNumber create(final PhoneNumber phoneNumber) {

        final ContentValues values = new ContentValues();

        values.put(COLUMN_NUMBER, phoneNumber.getNumber());
        values.put(COLUMN_TYPE, phoneNumber.getType());

        final long id = getDatabase().insert(DatabaseHelper.TABLE_PHONENUMBER , null, values);

        phoneNumber.setId(id);
        return phoneNumber;
    }

    public void delete(final PhoneNumber phoneNumber) {

        getDatabase().delete(DatabaseHelper.TABLE_PHONENUMBER, "number = ?", new String[]{phoneNumber.getNumber()});
    }

    public List<PhoneNumber> getPhoneNumberByType(Type listType) {

        final List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
        final String WHERE_CLAUSE = String.format("%s = ?", COLUMN_TYPE);

        final Cursor cursor = getDatabase().query(DatabaseHelper.TABLE_PHONENUMBER, new String[]{COLUMN_ID, COLUMN_NUMBER, COLUMN_TYPE}, WHERE_CLAUSE, new String[] {String.valueOf(listType.getValue())},null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final PhoneNumber number = new PhoneNumber();
            number.setId(cursor.getLong(0));
            number.setNumber(cursor.getString(1));
            number.setType(cursor.getInt(2));

            phoneNumberList.add(number);

            cursor.moveToNext();
        }
        return phoneNumberList;
    }
}
